package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    // String 형식의 startTime을 Date 객체로 변환하는 메소드 필요 시
    public static Date convertStringToDate(String startTime) throws ParseException {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return isoFormat.parse(startTime);
    }
}
