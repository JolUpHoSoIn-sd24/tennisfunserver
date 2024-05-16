package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.Court;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;

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

    private List<CourtHoursDto> businessHours;


    public static CourtResDto toDTO(Court court){
        CourtResDtoBuilder builder = CourtResDto.builder()
                .courtId(court.getId())
                .location(court.getLocation())
                .ownerId(court.getOwnerId())
                .courtType(court.getCourtType())
                .courtName(court.getCourtName())
                .businessHours(court.getBusinessHours());

        if(court.getDescription()!=null){
            builder.description(court.getDescription());
        }
        return builder.build();
    }
}
