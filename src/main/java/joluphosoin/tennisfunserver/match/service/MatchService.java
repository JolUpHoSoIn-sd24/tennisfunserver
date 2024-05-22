package joluphosoin.tennisfunserver.match.service;

import joluphosoin.tennisfunserver.match.data.dto.FeedbackReqDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResponseDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResultResDto;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface MatchService {
    MatchResponseDto registerMatchRequest(MatchRequestDto matchRequestDto, String userId);

    void deleteMatchRequest(String requestId);

    MatchResponseDto updateMatchRequest(MatchRequestDto matchRequestDto, String userId);

    MatchResponseDto getMatchRequest(String userId);

    List<MatchResultResDto> getMatchResult(String userId);

    void registerFeedback(String matchRequestId, FeedbackReqDto feedbackReqDto,String userId) throws IOException, ParseException;

}
