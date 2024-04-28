package JolUpHoSoIn.TennisFun_Server.match.service;

import JolUpHoSoIn.TennisFun_Server.match.data.dto.MatchRequestDto;
import JolUpHoSoIn.TennisFun_Server.match.data.dto.MatchResponseDto;

public interface MatchService {
    MatchResponseDto registermatchRequest(MatchRequestDto matchRequestDto);

    void deleteMatchRequest(String requestId);

    MatchResponseDto updateMatchRequest(MatchRequestDto matchRequestDto, String requestId);

    MatchResponseDto getMatchRequest(String requestId);
}
