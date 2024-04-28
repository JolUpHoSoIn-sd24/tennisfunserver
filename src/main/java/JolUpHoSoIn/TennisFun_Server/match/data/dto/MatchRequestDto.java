package JolUpHoSoIn.TennisFun_Server.match.data.dto;

import JolUpHoSoIn.TennisFun_Server.match.data.entity.MatchRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.Date;
import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequestDto {
    @Schema(example ="662cda25e5c4314e868188d3")
    private String userId;

    @Schema(example ="2024-05-01T10:00:00")
    private String startTime;

    @Schema(example ="2024-05-01T12:00:00")
    private String endTime;

    @Schema(example ="RALLY")
    private MatchRequest.MatchObjective objective;

    @Schema(example ="true")
    private Boolean isSingles;

    @Min(-90)
    @Max(90)
    @NotNull
    @Schema(example ="37.5204279064529")
    private Double latitude;

    @Min(-180)
    @Max(180)
    @NotNull
    @Schema(example ="126.887847771379")
    private Double longitude;

    @Schema(example ="[\"court1\", \"court2\"]")
    private List<String> dislikedCourts;

    @Schema(example ="2.8")
    private Double maxDistance;

    @Schema(example ="2024-05-01")
    private Date reservationDate;

    @Schema(example ="20000")
    private Integer rentalCost;

    @Schema(example ="테니스 랠리 연습을 위한 매치 요청")
    private String description;

    public MatchRequest toEntity(){

        Point location = new Point(longitude, latitude);

        MatchRequest.MatchRequestBuilder builder = MatchRequest.builder()
                .userId(userId)
                .startTime(startTime)
                .endTime(endTime)
                .isSingles(isSingles)
                .objective(objective)
                .location(location)
                .maxDistance(maxDistance)
                .dislikedCourts(dislikedCourts)
                .description(description);

        if (reservationDate != null && rentalCost != null) {
            builder.reservationDate(reservationDate)
                    .rentalCost(rentalCost);
        }

        return builder.build();
    }
}
