package joluphosoin.tennisfunserver.business.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CourtReqDto {

    @Min(-90)
    @Max(90)
    @NotNull
    @Schema(example = "37.2840931")
    private Double latitude;

    @Min(-180)
    @Max(180)
    @NotNull
    @Schema(example = "127.0453753")
    private Double longitude;

    private String description;

    @NotBlank
    @Schema(example = "665b1cc00b8df7460a248bf0")
    private String ownerId;

    @NotNull
    private Court.CourtType courtType;

    @NotBlank
    @Schema(example = "수원시 아주대학교 1코트")
    private String courtName;

    private List<CourtHoursDto> businessHours;

    @Schema(example = "7000")
    private Double rentalCostPerHalfHour;

    public Court toEntity(){
        Point location = new Point(longitude, latitude);

        return Court.builder()
                .location(location)
                .description(description)
                .ownerId(ownerId)
                .courtType(courtType)
                .courtName(courtName)
                .businessHours(businessHours)
                .rentalCostPerHalfHour(rentalCostPerHalfHour)
                .build();
    }
}
