package joluphosoin.tennisfunserver.match.service;

import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResponseDto;

public interface MatchService {
    MatchResponseDto registermatchRequest(MatchRequestDto matchRequestDto);

    void deleteMatchRequest(String requestId);

    MatchResponseDto updateMatchRequest(MatchRequestDto matchRequestDto, String requestId);

    MatchResponseDto getMatchRequest(String requestId);
}
