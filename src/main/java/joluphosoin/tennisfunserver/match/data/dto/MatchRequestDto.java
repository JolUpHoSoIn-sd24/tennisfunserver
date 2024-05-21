package joluphosoin.tennisfunserver.match.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequestDto {

    @Schema(example ="2024-05-01T10:00:00")
    @NotNull
    private Date startTime;

    @Schema(example ="2024-05-01T12:00:00")
    @NotNull
    private Date endTime;

    @Schema(example ="RALLY")
    @NotNull
    private MatchRequest.MatchObjective objective;

    @Schema(example ="true")
    @NotNull
    private Boolean isSingles;

    @Schema(example ="[\"court1\", \"court2\"]")
    private List<String> dislikedCourts;

    @Schema(example ="30")
    @NotNull
    private Integer minTime;

    @Schema(example ="120")
    @NotNull
    private Integer maxTime;

    @Schema(example = "courtId")
    private String reservationCourtId;

    @Schema(example ="2024-05-01")
    private Date reservationDate;

    @Schema(example ="20000")
    private Integer rentalCost;

    @Schema(example ="테니스 랠리 연습을 위한 매치 요청")
    private String description;

    public MatchRequest toEntity(User user){

        MatchRequest.MatchRequestBuilder builder = MatchRequest.builder()
                .userId(user.getId())
                .startTime(startTime)
                .endTime(endTime)
                .isSingles(isSingles)
                .objective(objective)
                .location(user.getLocation())
                .maxDistance(user.getMaxDistance())
                .dislikedCourts(dislikedCourts)
                .minTime(minTime)
                .maxTime(maxTime)
                .description(description);

        if (reservationDate != null && rentalCost != null) {
            builder.reservationDate(reservationDate)
                    .rentalCost(rentalCost)
                    .reservationCourtId(reservationCourtId);
        }

        return builder.build();
    }
}
