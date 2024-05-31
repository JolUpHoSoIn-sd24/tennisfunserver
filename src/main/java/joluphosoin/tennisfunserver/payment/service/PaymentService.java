package joluphosoin.tennisfunserver.payment.service;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.payment.data.dto.PaymentVerificationRequestDto;
import joluphosoin.tennisfunserver.payment.exception.PaymentServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class PaymentService {

    private final WebClient webClient;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Autowired
    public PaymentService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Map<String, Object> getPaymentInfo(Game game) {
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
        requestBody.add("approval_url", "https://tennisfun-rrrlqvarua-du.a.run.app/success");
        requestBody.add("fail_url", "https://tennisfun-rrrlqvarua-du.a.run.app/fail");
        requestBody.add("cancel_url", "https://tennisfun-rrrlqvarua-du.a.run.app/cancel");

        try {
            return webClient.post()
                    .uri("https://kapi.kakao.com/v1/payment/ready")
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            throw new PaymentServiceException("Failed to get payment info: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> verifyPayment(PaymentVerificationRequestDto requestDto) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("cid", "TC0ONETIME");
        requestBody.add("tid", requestDto.getTid());
        requestBody.add("partner_order_id", "partner_order_id");
        requestBody.add("partner_user_id", "partner_user_id");
        requestBody.add("pg_token", requestDto.getPgToken());

        try {
            return webClient.post()
                    .uri("https://kapi.kakao.com/v1/payment/approve")
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            throw new PaymentServiceException("Failed to verify payment: " + e.getResponseBodyAsString(), e);
        }
    }
}
