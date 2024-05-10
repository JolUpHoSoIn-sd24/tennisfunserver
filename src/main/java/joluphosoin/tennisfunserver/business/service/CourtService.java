package joluphosoin.tennisfunserver.business.service;

import joluphosoin.tennisfunserver.business.data.dto.CourtReqDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtResDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtTimeSlotReqDto;
import joluphosoin.tennisfunserver.business.data.dto.TimeSlotDto;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.data.entity.TimeSlot;
import joluphosoin.tennisfunserver.business.repository.BusinessInfoRepository;
import joluphosoin.tennisfunserver.business.repository.CourtRepository;
import joluphosoin.tennisfunserver.business.repository.TimeSlotRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final BusinessInfoRepository businessInfoRepository;
    private final CourtRepository courtRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Transactional
    public CourtResDto registerCourt(CourtReqDto courtReqDto) {

        businessInfoRepository.findById(courtReqDto.getOwnerId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.BUSINESS_NOT_FOUND));

        if(courtRepository.findByOwnerIdAndCourtName(courtReqDto.getOwnerId(), courtReqDto.getCourtName()).isPresent()){
            throw new GeneralException(ErrorStatus.COURT_NOT_UNIQUE);
        }

        Court court = courtReqDto.toEntity();

        courtRepository.save(court);

        return CourtResDto.toDTO(court);
    }

    @Transactional
    public CourtResDto registerCourtTimeSlot(CourtTimeSlotReqDto courtTimeSlotReqDto) {

        Court court = courtRepository.findById(courtTimeSlotReqDto.getCourtId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));

        List<TimeSlotDto> timeSlotDtos = courtTimeSlotReqDto.getTimeSlots();
        List<TimeSlot> timeSlots = new ArrayList<>();
        timeSlotDtos.forEach(timeSlotDto -> {
            TimeSlot timeSlot = TimeSlotDto.toEntity(timeSlotDto,courtTimeSlotReqDto);
            timeSlots.add(timeSlot);
            timeSlotRepository.save(timeSlot);
        });

        return CourtResDto.toDTO(court,timeSlots);

    }
}
