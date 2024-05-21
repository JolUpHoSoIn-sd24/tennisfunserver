package joluphosoin.tennisfunserver.user.service;

import joluphosoin.tennisfunserver.business.data.dto.CourtResDto;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.repository.CourtRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtUserService {

    private final CourtRepository courtRepository;

    public List<CourtResDto> searchCourts(String courtName) {
        List<Court> courts = courtRepository
                .findAllByCourtNameContaining(courtName).orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));

        List<CourtResDto> courtResDtos=new ArrayList<>();

        for (Court court : courts) {
            courtResDtos.add(CourtResDto.toDTO(court));
        }

        return courtResDtos;
    }
}
