package joluphosoin.tennisfunserver.match.service;

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

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRequestRepository matchRequestRepository;
    private final UserRepository userRepository;
    private final MatchResultRepository matchResultRepository;

    @Override
    public MatchResponseDto getMatchRequest(String requestId) {

        MatchRequest matchRequest = matchRequestRepository.findById(requestId).orElseThrow(() -> new GeneralException(ErrorStatus.MATCHREQ_NOT_FOUND));

        return MatchResponseDto.toDto(matchRequest);
    }

    @Override
    @Transactional
    public MatchResponseDto registermatchRequest(MatchRequestDto matchRequestDto) {

        MatchRequest matchRequest = matchRequestDto.toEntity();

        User user = userRepository.findById(matchRequestDto.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        user.setAvoidCourtLocation(matchRequest.getDislikedCourts());

        matchRequestRepository.save(matchRequest);

        userRepository.save(user);

        return MatchResponseDto.toDto(matchRequest);
    }

    @Override
    public void deleteMatchRequest(String requestId) {

        matchRequestRepository.findById(requestId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHREQ_NOT_FOUND));

        matchRequestRepository.deleteById(requestId);

    }

    @Override
    @Transactional
    public MatchResponseDto updateMatchRequest(MatchRequestDto matchRequestDto, String requestId) {
        MatchRequest matchRequest = matchRequestRepository.findById(requestId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHREQ_NOT_FOUND));

        MatchRequest updateMatchRequest = matchRequest.setEntity(matchRequestDto);

        matchRequestRepository.save(updateMatchRequest);

        return MatchResponseDto.toDto(matchRequest);
    }

    @Override
    @Transactional
    public MatchResultResDto getMatchResult(String matchRequestId, String userId) {
        MatchResult matchResult = matchResultRepository.findByMatchRequestId(matchRequestId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHRESULT_NOT_FOUND));

        Map<String, MatchResult.FeedbackStatus> feedback = matchResult.getFeedback();

        if(!feedback.containsKey(userId)){
            feedback.put(userId, MatchResult.FeedbackStatus.NOTSELECTED);
        }

        matchResult.setFeedback(feedback);

        matchResultRepository.save(matchResult);

        return MatchResultResDto.toDto(matchResult,userId);

    }

    @Override
    @Transactional
    public void registerFeedback(String matchRequestId, FeedbackReqDto feedbackReqDto) {

        MatchResult matchResult = matchResultRepository.findByMatchRequestId(matchRequestId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCHRESULT_NOT_FOUND));

        Map<String, MatchResult.FeedbackStatus> feedback = matchResult.getFeedback();

        feedback.put(feedbackReqDto.getUserId(), feedbackReqDto.getFeedback());

        matchResult.setFeedback(feedback);

        matchResultRepository.save(matchResult);

    }
}
