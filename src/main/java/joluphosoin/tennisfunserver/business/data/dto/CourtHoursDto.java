package joluphosoin.tennisfunserver.business.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@Builder
@AllArgsConstructor
public class CourtHoursDto {
    private DayOfWeek dayOfWeek;
    @Schema(example = "10:00")
    private String openTime;
    @Schema(example = "22:00")
    private String closeTime;
}
