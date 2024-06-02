package joluphosoin.tennisfunserver.match.data.dto;

import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MatchRequestResDto {
    @Schema(example = "662cda25e5c4314006800000")
    private String id;
    @Schema(example = "662cda25e5c4314e868188d3")
    private String userId;

    @Schema(example = "2024-05-01T10:00:00")
    private Date startTime;

    @Schema(example = "2024-05-01T12:00:00")
    private Date endTime;

    @Schema(example = "RALLY")
    private MatchRequest.MatchObjective objective;

    @Schema(example = "true")
    private Boolean isSingles;

    @Schema(example = "126.887847771379")
    private Double x;

    @Schema(example = "37.5204279064529")
    private Double y;

    @Schema(example = "2.8")
    private Double maxDistance;

    @Schema(example = "[\"court1\", \"court2\"]")
    private List<String> dislikedCourts;

    @Schema(example ="30")
    private Integer minTime;

    @Schema(example ="120")
    private Integer maxTime;

    @Schema(example = "false")
    @NotNull
    private Boolean isReserved;

    @Schema(example = "courtId")
    private String reservationCourtId;

    @Schema(example = "2024-05-01")
    private Date reservationDate;

    @Schema(example = "테니스 랠리 연습을 위한 매치 요청")
    private String description;

    public static MatchRequestResDto toDto(MatchRequest matchRequest) {

        MatchRequestResDto.MatchRequestResDtoBuilder builder = MatchRequestResDto.builder()
                .id(matchRequest.getId())
                .userId(matchRequest.getUserId())
                .startTime(matchRequest.getStartTime())
                .endTime(matchRequest.getEndTime())
                .objective(matchRequest.getObjective())
                .isSingles(matchRequest.getIsSingles())
                .x(matchRequest.getLocation().getX())
                .y(matchRequest.getLocation().getY())
                .dislikedCourts(matchRequest.getDislikedCourts())
                .maxDistance(matchRequest.getMaxDistance())
                .minTime(matchRequest.getMinTime())
                .maxTime(matchRequest.getMaxTime())
                .description(matchRequest.getDescription())
                .isReserved(matchRequest.getIsReserved());

        if (Boolean.TRUE.equals(matchRequest.getIsReserved())) {
            builder.reservationDate(matchRequest.getReservationDate())
                    .reservationCourtId(matchRequest.getReservationCourtId());
        }
        return builder.build();
    }
}
