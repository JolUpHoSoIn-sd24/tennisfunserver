package joluphosoin.tennisfunserver.match.service;

import joluphosoin.tennisfunserver.match.data.dto.FeedbackReqDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResponseDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResultResDto;

public interface MatchService {
    MatchResponseDto registermatchRequest(MatchRequestDto matchRequestDto);

    void deleteMatchRequest(String requestId);

    MatchResponseDto updateMatchRequest(MatchRequestDto matchRequestDto, String requestId);

    MatchResponseDto getMatchRequest(String requestId);

    MatchResultResDto getMatchResult(String matchRequestId, String userId);

    void registerFeedback(String matchRequestId, FeedbackReqDto feedbackReqDto);

}
