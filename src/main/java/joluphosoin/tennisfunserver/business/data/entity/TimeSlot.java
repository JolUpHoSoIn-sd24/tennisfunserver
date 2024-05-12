package joluphosoin.tennisfunserver.business.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "timeslot")
@Getter
@AllArgsConstructor
@Builder
public class TimeSlot {

    @MongoId
    private String id;

    private String ownerId;

    private String courtId;

    private Date startTime;

    private Double rentalCostPerHalfHour;

    private Boolean isAvailable;
}
