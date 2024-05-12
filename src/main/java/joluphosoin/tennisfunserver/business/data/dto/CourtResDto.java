package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.data.entity.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CourtResDto {

    private String courtId;

    private Point location;

    private String description;

    private String ownerId;

    private Court.CourtType courtType;

    private String courtName;

    private LocalTime openTime;

    private LocalTime closeTime;

    private List<TimeSlot> timeSlots;

    public static CourtResDto toDTO(Court court){
        CourtResDtoBuilder builder = CourtResDto.builder()
                .courtId(court.getId())
                .location(court.getLocation())
                .ownerId(court.getOwnerId())
                .courtType(court.getCourtType())
                .courtName(court.getCourtName())
                .openTime(court.getOpenTime())
                .closeTime(court.getCloseTime());

        if(court.getDescription()!=null){
            builder.description(court.getDescription());
        }
        return builder.build();
    }
    public static CourtResDto toDTO(Court court, List<TimeSlot> timeSlot){
        CourtResDtoBuilder builder = CourtResDto.builder()
                .courtId(court.getId())
                .location(court.getLocation())
                .ownerId(court.getOwnerId())
                .courtType(court.getCourtType())
                .courtName(court.getCourtName())
                .openTime(court.getOpenTime())
                .closeTime(court.getCloseTime())
                .timeSlots(timeSlot);

        if(court.getDescription()!=null){
            builder.description(court.getDescription());
        }
        return builder.build();
    }
}
