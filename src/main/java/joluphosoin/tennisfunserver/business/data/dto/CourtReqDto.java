package joluphosoin.tennisfunserver.business.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class CourtReqDto {

    @Min(-90)
    @Max(90)
    @NotNull
    @Schema(example = "37.5204279064529")
    private Double latitude;

    @Min(-180)
    @Max(180)
    @NotNull
    @Schema(example = "126.887847771379")
    private Double longitude;

    private String description;

    private String ownerId;

    private Court.CourtType courtType;

    private String courtName;

    private LocalTime openTime;

    private LocalTime closeTime;


    public Court toEntity(){
        Point location = new Point(longitude, latitude);

        return Court.builder()
                .location(location)
                .description(description)
                .ownerId(ownerId)
                .courtType(courtType)
                .courtName(courtName)
                .openTime(openTime)
                .closeTime(closeTime)
                .build();
    }
}
