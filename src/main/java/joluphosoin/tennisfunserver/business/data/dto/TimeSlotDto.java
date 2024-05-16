package joluphosoin.tennisfunserver.business.data.dto;

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

    private String status;

//    public static TimeSlot toEntity(TimeSlotDto timeSlotDto, Court court){
//
//        LocalDate localDate = timeSlotDto.getStartTime()
//                .toInstant()
//                .atZone(ZoneId.of("Asia/Seoul"))
//                .toLocalDate();
//
//        return TimeSlot.builder()
//                .ownerId(court.getOwnerId())
//                .courtId(court.getId())
//                .date(localDate)
//                .startTime(timeSlotDto.getStartTime())
//                .rentalCostPerHalfHour(timeSlotDto.getRentalCostPerHalfHour())
//                .status(TimeSlot.ReservationStatus.valueOf(timeSlotDto.getStatus()))
//                .build();
//    }
}
