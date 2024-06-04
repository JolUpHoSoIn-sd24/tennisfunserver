package joluphosoin.tennisfunserver.payment.service;

import joluphosoin.tennisfunserver.business.data.dto.TimeSlotDto;
import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import joluphosoin.tennisfunserver.business.repository.DayTimeSlotRepository;
import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.game.service.GameService;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.payment.data.dto.PaymentVerificationRequestDto;
import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import joluphosoin.tennisfunserver.payment.data.entity.TempPayment;
import joluphosoin.tennisfunserver.payment.exception.PaymentServiceException;
import joluphosoin.tennisfunserver.payment.repository.PaymentInfoRepository;
import joluphosoin.tennisfunserver.payment.repository.TempPaymentRepository;
import joluphosoin.tennisfunserver.websocket.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WebClient webClient;
    private final GameRepository gameRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    private final NotificationService notificationService;
    private final GameService gameService;

    private final DayTimeSlotRepository dayTimeSlotRepository;

    private final TempPaymentRepository tempPaymentRepository;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${app.base-url}")
    private String baseUrl;

    public Map<String, Object> getPaymentInfo(Game game, String userId) {
        int amount = (int) Math.round(game.getRentalCost());
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("cid", "TC0ONETIME");
        requestBody.add("partner_order_id", "partner_order_id");
        requestBody.add("partner_user_id", "partner_user_id");
        requestBody.add("item_name", "코트 대여 비용");
        requestBody.add("quantity", "1");
        requestBody.add("total_amount", Integer.toString(amount));
        requestBody.add("vat_amount", "0");
        requestBody.add("tax_free_amount", "0");
        requestBody.add("approval_url", baseUrl+"/api/payment/verify/" + userId + "/success");
        requestBody.add("fail_url", baseUrl+ "/api/payment/verify/" + userId + "/fail");
        requestBody.add("cancel_url", baseUrl+"/api/payment/verify/" + userId + "/cancel");

        try {
            Map<String, Object> res = webClient.post()
                    .uri("https://kapi.kakao.com/v1/payment/ready")
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();
            Optional<TempPayment> existingPayment = tempPaymentRepository.findByUserId(userId);
            TempPayment tempPayment = existingPayment.orElseGet(() -> TempPayment.builder()
                    .game(game)
                    .userId(userId)
                    .build());

            tempPayment.setTransactionId((String) res.get("tid"));
            tempPayment.setStatus(TempPayment.PaymentStatus.PENDING);
            tempPaymentRepository.save(tempPayment);

            return res;
        } catch (WebClientResponseException e) {
            throw new PaymentServiceException("Failed to get payment info: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> verifyPayment(String userId, String pg_token) {
        TempPayment tempPayment = tempPaymentRepository.findByUserId(userId)
                .orElseThrow(() -> new PaymentServiceException("No payment found for user: " + userId));
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("cid", "TC0ONETIME");
        requestBody.add("tid", tempPayment.getTransactionId());
        requestBody.add("partner_order_id", "partner_order_id");
        requestBody.add("partner_user_id", "partner_user_id");
        requestBody.add("pg_token", pg_token);

        try {
            Map<String, Object> response = webClient.post()
                    .uri("https://kapi.kakao.com/v1/payment/approve")
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            Collection<Game> games = gameRepository.findByPlayerIdsContaining(userId);
            if (games.isEmpty()) {
                throw new PaymentServiceException("No game found for user: " + userId);
            }

            Game game = games.iterator().next();
            game.getPaymentStatus().put(userId, true);

            boolean allPaid = game.getPaymentStatus().values().stream().allMatch(status -> status);

            if (allPaid) {
                game.setGameStatus(Game.GameStatus.INPLAY);

                setTimeSlotToConfirmed(game);

                notificationToClient(userId, game);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime createdAt = LocalDateTime.parse((String) response.get("created_at"), formatter);
            LocalDateTime approvedAt = LocalDateTime.parse((String) response.get("approved_at"), formatter);

            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .game(game)
                    .userId(userId)
                    .transactionId((String) response.get("tid"))
                    .paymentMethodType((String) response.get("payment_method_type"))
                    .itemName((String) response.get("item_name"))
                    .quantity((Integer) response.get("quantity"))
                    .amount((Map<String, Integer>) response.get("amount"))
                    .createdAt(Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant()))
                    .approvedAt(Date.from(approvedAt.atZone(ZoneId.systemDefault()).toInstant()))
                    .status(PaymentInfo.PaymentStatus.APPROVED)
                    .build();
            paymentInfoRepository.save(paymentInfo);

            gameRepository.save(game);




            Map<String, Object> res = new HashMap<>();
            res.put("updatedGame", game);
            return res;
        } catch (WebClientResponseException e) {
            throw new PaymentServiceException("Failed to verify payment: " + e.getResponseBodyAsString(), e);
        }
    }

    private void notificationToClient(String userId, Game game) {
        GameDetailsDto gameDetailsDto = gameService.transformGameToDto(game);
        List<String> playerIds = game.getPlayerIds();
        String opponentId = playerIds.stream()
                .filter(id -> !id.equals(userId)) // userId와 다른 ID만 필터링
                .findAny().orElseThrow(()->new GeneralException(ErrorStatus.USER_NOT_FOUND));

        notificationService.sendGameNotification(userId,gameDetailsDto);
        notificationService.sendGameNotification(opponentId,gameDetailsDto);
    }

    private void setTimeSlotToConfirmed(Game game) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        DayTimeSlot dayTimeSlot = dayTimeSlotRepository.findByCourtIdAndDate(game.getCourtId(),
                        dateFormat.format(game.getStartTime()))
                .orElseThrow(() -> new GeneralException(ErrorStatus.TIMESLOT_NOT_FOUND));

        List<TimeSlotDto> timeSlotDtos = dayTimeSlot.getTimeSlots();

        String startTime = sdf.format(game.getStartTime());
        String endTime = sdf.format(game.getEndTime());

        boolean changeStatus = false;

        for (int i = 0; i < timeSlotDtos.size(); i++) {
            TimeSlotDto currentSlot = timeSlotDtos.get(i);
            if (currentSlot.getStartTime().equals(endTime)) {
                break;
            }
            if (currentSlot.getStartTime().equals(startTime)) {
                changeStatus = true;
            }

            if (changeStatus) {
                currentSlot.setStatus(DayTimeSlot.ReservationStatus.CONFIRMED);
                timeSlotDtos.set(i, currentSlot);
            }

        }
        dayTimeSlot.setTimeSlots(timeSlotDtos);
        dayTimeSlotRepository.save(dayTimeSlot);
    }
}
