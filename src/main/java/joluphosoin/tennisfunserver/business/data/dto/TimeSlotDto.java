package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class TimeSlotDto {

    private Date startTime;

    @Setter
    private DayTimeSlot.ReservationStatus status;

    public static TimeSlotDto toDto(Date startTime){
        return TimeSlotDto.builder()
                .startTime(startTime)
                .status(DayTimeSlot.ReservationStatus.NOT_OPEN)
                .build();
    }
}
