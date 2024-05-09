package joluphosoin.tennisfunserver.business.service;

import joluphosoin.tennisfunserver.business.data.dto.CourtReqDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtTimeSlotReqDto;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.repository.BusinessInfoRepository;
import joluphosoin.tennisfunserver.business.repository.CourtRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final BusinessInfoRepository businessInfoRepository;
    private final CourtRepository courtRepository;

    @Transactional
    public void registerCourt(CourtReqDto courtReqDto) {

        businessInfoRepository.findById(courtReqDto.getOwnerId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.BUSINESS_NOT_FOUND));

        if(courtRepository.findByOwnerIdAndCourtName(courtReqDto.getOwnerId(), courtReqDto.getCourtName()).isPresent()){
            throw new GeneralException(ErrorStatus.COURT_NOT_UNIQUE);
        }

        Court court = courtReqDto.toEntity();

        courtRepository.save(court);
    }

    @Transactional
    public void registerCourtTimeSlot(CourtTimeSlotReqDto courtTimeSlotReqDto) {

        Court court = courtRepository.findById(courtTimeSlotReqDto.getCourtId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));

        court.setTimeSlots(courtTimeSlotReqDto.getTimeSlots());

        courtRepository.save(court);
    }
}
