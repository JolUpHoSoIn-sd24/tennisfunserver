package joluphosoin.tennisfunserver.match.service;

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
