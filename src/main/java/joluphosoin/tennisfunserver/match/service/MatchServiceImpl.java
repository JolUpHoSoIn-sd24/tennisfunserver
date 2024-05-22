package joluphosoin.tennisfunserver.match.service;

import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.repository.CourtRepository;
import joluphosoin.tennisfunserver.match.data.dto.FeedbackReqDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResultResDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import joluphosoin.tennisfunserver.match.repository.MatchResultRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResponseDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import joluphosoin.tennisfunserver.match.repository.MatchRequestRepository;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRequestRepository matchRequestRepository;
    private final UserRepository userRepository;
    private final MatchResultRepository matchResultRepository;
    private final CourtRepository courtRepository;
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
    public void registerFeedback(String matchResultId, FeedbackReqDto feedbackReqDto) {

        MatchResult matchResult = matchResultRepository.findById(matchResultId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHRESULT_NOT_FOUND));

        Map<String, MatchResult.FeedbackStatus> feedback = matchResult.getFeedback();

        feedback.put(feedbackReqDto.getUserId(), feedbackReqDto.getFeedback());

        matchResult.setFeedback(feedback);

        matchResultRepository.save(matchResult);

    }
}
