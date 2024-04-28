package JolUpHoSoIn.TennisFun_Server.match.service;

import JolUpHoSoIn.TennisFun_Server.apiPayload.code.status.ErrorStatus;
import JolUpHoSoIn.TennisFun_Server.apiPayload.exception.GeneralException;
import JolUpHoSoIn.TennisFun_Server.match.data.dto.MatchRequestDto;
import JolUpHoSoIn.TennisFun_Server.match.data.dto.MatchResponseDto;
import JolUpHoSoIn.TennisFun_Server.match.data.entity.MatchRequest;
import JolUpHoSoIn.TennisFun_Server.match.repository.MatchRequestRepository;
import JolUpHoSoIn.TennisFun_Server.user.data.entity.User;
import JolUpHoSoIn.TennisFun_Server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRequestRepository matchRequestRepository;
    private final UserRepository userRepository;
    @Override
    public MatchResponseDto getMatchRequest(String requestId) {

        MatchRequest matchRequest = matchRequestRepository.findById(requestId).orElseThrow(() -> new GeneralException(ErrorStatus.MATCH_NOT_FOUND));

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
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCH_NOT_FOUND));

        matchRequestRepository.deleteById(requestId);

    }

    @Override
    @Transactional
    public MatchResponseDto updateMatchRequest(MatchRequestDto matchRequestDto, String requestId) {
        MatchRequest matchRequest = matchRequestRepository.findById(requestId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MATCH_NOT_FOUND));

        MatchRequest updateMatchRequest = matchRequest.setEntity(matchRequestDto);

        matchRequestRepository.save(updateMatchRequest);

        return MatchResponseDto.toDto(matchRequest);
    }
}
