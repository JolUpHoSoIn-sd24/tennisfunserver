package joluphosoin.tennisfunserver.match.service;

import joluphosoin.tennisfunserver.business.data.dto.TimeSlotDto;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import joluphosoin.tennisfunserver.business.repository.CourtRepository;
import joluphosoin.tennisfunserver.business.repository.DayTimeSlotRepository;
import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import joluphosoin.tennisfunserver.game.service.GameService;
import joluphosoin.tennisfunserver.match.data.dto.FeedbackReqDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResponseDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResultResDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import joluphosoin.tennisfunserver.match.repository.MatchRequestRepository;
import joluphosoin.tennisfunserver.match.repository.MatchResultRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import joluphosoin.tennisfunserver.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static joluphosoin.tennisfunserver.business.data.dto.TimeSlotDto.convertStringToDate;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRequestRepository matchRequestRepository;
    private final UserRepository userRepository;
    private final MatchResultRepository matchResultRepository;
    private final CourtRepository courtRepository;
    private final DayTimeSlotRepository dayTimeSlotRepository;

    private final GameService gameService;
    private final WebSocketHandler webSocketHandler;
    @Override
    public MatchResponseDto getMatchRequest(String userId) {

        MatchRequest matchRequest = matchRequestRepository.findByUserId(userId).orElseThrow(() -> new GeneralException(ErrorStatus.MATCHREQ_NOT_FOUND));

        return MatchResponseDto.toDto(matchRequest,userId);
    }

    @Override
    @Transactional
    public MatchResponseDto registerMatchRequest(MatchRequestDto matchRequestDto, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Optional<MatchRequest> optionalMatchRequest = matchRequestRepository.findByUserId(userId);

        MatchRequest matchRequest;

        if(optionalMatchRequest.isPresent()){
            matchRequest = optionalMatchRequest.get();
            matchRequest.setEntity(matchRequestDto, user);
        }
        else{
            matchRequest = matchRequestDto.toEntity(user);
        }

        saveMatchRequestAndUser(matchRequest, user);

        return MatchResponseDto.toDto(matchRequest,userId);
    }

    private void saveMatchRequestAndUser(MatchRequest matchRequest, User user) {
        matchRequestRepository.save(matchRequest);

        user.setDislikedCourts(matchRequest.getDislikedCourts());

        userRepository.save(user);
    }

    @Override
    public void deleteMatchRequest(String userId) {
        MatchRequest matchRequest = matchRequestRepository.findByUserId(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHREQ_NOT_FOUND));

        matchRequestRepository.delete(matchRequest);
    }

    @Override
    @Transactional
    public MatchResponseDto updateMatchRequest(MatchRequestDto matchRequestDto, String userId) {
        MatchRequest matchRequest = matchRequestRepository.findByUserId(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHREQ_NOT_FOUND));

        User user = userRepository.findById(matchRequest.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        MatchRequest updateMatchRequest = matchRequest.setEntity(matchRequestDto,user);

        saveMatchRequestAndUser(updateMatchRequest, user);

        return MatchResponseDto.toDto(matchRequest,userId);
    }

    @Override
    @Transactional
    public List<MatchResultResDto> getMatchResult(String userId) {

        List<MatchResult> matchResults = matchResultRepository
                .findAllByUserAndMatchRequests(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHRESULT_NOT_FOUND));
        List<MatchResultResDto> matchResultResDtos = new ArrayList<>();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        getMatchResults(user, matchResults, matchResultResDtos);
        matchResultResDtos.sort(Comparator.comparingDouble(matchResultResDto ->
                Math.abs(user.getNtrp() - matchResultResDto.getOpponent().getNtrp())
        ));
        return matchResultResDtos;
    }

    private void getMatchResults(User user, List<MatchResult> matchResults, List<MatchResultResDto> matchResultResDtos) {

        for (MatchResult matchResult : matchResults) {
            setFeedback(user.getId(), matchResult);

            User opponent = getOpponent(user.getId(), matchResult);

            Court court = courtRepository.findById(matchResult.getMatchDetails().getCourtId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));


            matchResultResDtos.add(MatchResultResDto.toDto(matchResult,user,opponent,court));
        }
    }

    private User getOpponent(String userId, MatchResult matchResult) {
        Map<String, String> userAndMatchRequests = matchResult.getUserAndMatchRequests();

        String opponentId = userAndMatchRequests.keySet().stream()
                .filter(id -> !id.equals(userId))
                .findAny()
                .orElse(null);

        assert opponentId != null;

        return userRepository.findById(opponentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    private void setFeedback(String userId, MatchResult matchResult) {

        Map<String, MatchResult.FeedbackStatus> feedback = matchResult.getFeedback() == null ?
                new HashMap<>() : matchResult.getFeedback();

        feedback.computeIfAbsent(userId, k -> MatchResult.FeedbackStatus.NOTSELECTED);

        matchResult.setFeedback(feedback);

        matchResultRepository.save(matchResult);
    }

    @Override
    @Transactional
    public void registerFeedback(String matchResultId, FeedbackReqDto feedbackReqDto,String userId) throws ParseException {

        MatchResult matchResult = matchResultRepository.findById(matchResultId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHRESULT_NOT_FOUND));

        Map<String, MatchResult.FeedbackStatus> feedback = matchResult.getFeedback();

        feedback.put(userId, feedbackReqDto.getFeedback());

        matchResult.setFeedback(feedback);

        matchResultRepository.save(matchResult);

        isCreateGame(userId, matchResult, feedback);
    }

    private void isCreateGame(String userId, MatchResult matchResult, Map<String, MatchResult.FeedbackStatus> feedback) throws ParseException {
        if(matchResult.getFeedback().size()==2){
            boolean allLike = feedback.values().stream().allMatch(status -> status == MatchResult.FeedbackStatus.LIKE);

            if (allLike) {
                MatchResult.MatchDetails matchDetails = matchResult.getMatchDetails();

                // 게임 생성
                Court court = courtRepository.findById(matchResult.getMatchDetails().getCourtId())
                        .orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));
                gameService.createGame(GameCreationDto.toDto(matchResult,court));

                // 해당 타임 슬롯 가예약으로 변경..
                setTimeSlotToPending(matchResult, court, matchDetails);

                // matchRequest 각자 삭제

                Map<String, String> userAndMatchRequests = matchResult.getUserAndMatchRequests();
                Collection<String> matchRequestIds = userAndMatchRequests.values();

                for (String matchRequestId : matchRequestIds) {
                    matchRequestRepository.deleteById(matchRequestId);
                }

                // matchResult의 isConfirmed 값을 true로 변경
                matchResult.setIsConfirmed(true);
                matchResultRepository.save(matchResult);

                // 소켓 알림 전송
                User user = userRepository.findById(userId)
                        .orElseThrow(()->new GeneralException(ErrorStatus.USER_NOT_FOUND));
                String notification = "Both users liked the match. New game created.";
                webSocketHandler.sendNotification(user.getWebSocketId(), notification); // sessionId에는 해당 세션의 ID를 넣어야 합니다.
            }

        }
    }

    private void setTimeSlotToPending(MatchResult matchResult, Court court, MatchResult.MatchDetails matchDetails) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        DayTimeSlot dayTimeSlot = dayTimeSlotRepository
                .findByCourtIdAndDate(court.getId(),
                        sdf.format(matchResult.getMatchDetails().getStartTime()))
                .orElseThrow(() -> new GeneralException(ErrorStatus.TIMESLOT_NOT_FOUND));

        List<TimeSlotDto> timeSlotDtos = dayTimeSlot.getTimeSlots();

        Date startTime = matchDetails.getStartTime();
        Date endTime = matchDetails.getEndTime();
        boolean flag = false;

        for (int i = 0; i < timeSlotDtos.size(); i++) {
            TimeSlotDto currentSlot = timeSlotDtos.get(i);
            Date currentSlotStartTime = convertStringToDate(currentSlot.getStartTime());

            if(currentSlotStartTime==startTime){
                flag = true;
            }

            if(flag){
                currentSlot.setStatus(DayTimeSlot.ReservationStatus.PENDING);
                if (currentSlotStartTime == endTime) {
                    break;
                }
            }
        }

        dayTimeSlot.setTimeSlots(timeSlotDtos);
        dayTimeSlotRepository.save(dayTimeSlot);
    }
}
