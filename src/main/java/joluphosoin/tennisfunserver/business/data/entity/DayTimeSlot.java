package joluphosoin.tennisfunserver.business.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Document(collection = "timeslot")
@Getter
@AllArgsConstructor
@Builder
public class DayTimeSlot {

    @MongoId
    private String id;

    private String ownerId;

    private String courtId;

    private String date;

    @Setter
    private Map<String,ReservationStatus> timeSlots;

    private Double rentalCostPerHalfHour;

    public enum ReservationStatus {
        NOT_OPEN, // Open 하기 전
        BEFORE, // 예약 전
        PENDING, // 가 예약
        CONFIRMED, // 예약 완료
        USED // 사용 완료
    }

    public static DayTimeSlot toEntity(Court court, Date date, Map<String,ReservationStatus> timeSlots){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        return DayTimeSlot.builder()
                .ownerId(court.getOwnerId())
                .courtId(court.getId())
                .date(dateString)
                .timeSlots(timeSlots)
                .rentalCostPerHalfHour(court.getRentalCostPerHalfHour())
                .build();

    }

}
