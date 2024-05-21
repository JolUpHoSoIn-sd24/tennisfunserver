package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Getter
@Builder
@AllArgsConstructor
public class TimeSlotDto {

    private String startTime;

    @Setter
    private DayTimeSlot.ReservationStatus status;

    public static TimeSlotDto toDto(Date startTime){
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return TimeSlotDto.builder()
                .startTime(isoFormat.format(startTime))
                .status(DayTimeSlot.ReservationStatus.NOT_OPEN)
                .build();
    }
}
