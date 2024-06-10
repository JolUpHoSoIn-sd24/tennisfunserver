package joluphosoin.tennisfunserver.business.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CourtReqDto {

    @NotBlank
    @Schema(example = "수원시 아주대학교 1코트")
    private String courtName;

    private String description;

    private List<CourtHoursDto> businessHours;

    @Schema(example = "7000")
    private Double rentalCostPerHalfHour;

    @NotNull
    private Court.CourtType courtType;

    private boolean isAutoMatch;

}
