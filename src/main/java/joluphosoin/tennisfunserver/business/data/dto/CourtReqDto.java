package joluphosoin.tennisfunserver.business.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

    private CourtDetails courtDetails;

    public static class CourtDetails {
        private String courtType; // 코트 타입
        private LocalTime startTime; // 시작 시간
        private LocalTime endTime; // 종료 시간
        private Integer rentalCost;
        private String courtName;

    }
}
