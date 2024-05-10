package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class TimeSlotDto {

    private Date startTime;

    private Double rentalCostPerHalfHour;

    private Boolean isAvailable;

    public static TimeSlot toEntity(TimeSlotDto timeSlotDto,CourtTimeSlotReqDto courtTimeSlotReqDto){
        return TimeSlot.builder()
                .ownerId(courtTimeSlotReqDto.getOwnerId())
                .courtId(courtTimeSlotReqDto.getCourtId())
                .startTime(timeSlotDto.getStartTime())
                .rentalCostPerHalfHour(timeSlotDto.getRentalCostPerHalfHour())
                .isAvailable(timeSlotDto.getIsAvailable())
                .build();
    }
}
