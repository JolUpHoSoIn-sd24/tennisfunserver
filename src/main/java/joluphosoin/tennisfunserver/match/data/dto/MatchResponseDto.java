package joluphosoin.tennisfunserver.match.data.dto;

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
public class MatchResponseDto {
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

    @Schema(example = "[\"court1\", \"court2\"]")
    private List<String> dislikedCourts;

    @Schema(example = "2.8")
    private Double maxDistance;

    @Schema(example ="30")
    private Integer minTime;

    @Schema(example ="120")
    private Integer maxTime;

    @Schema(example = "courtId")
    private String reservationCourtId;

    @Schema(example = "2024-05-01")
    private Date reservationDate;

    @Schema(example = "20000")
    private Integer rentalCost;

    @Schema(example = "테니스 랠리 연습을 위한 매치 요청")
    private String description;

    public static MatchResponseDto toDto(MatchRequest matchRequest) {

        MatchResponseDto.MatchResponseDtoBuilder builder = MatchResponseDto.builder()
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
                .description(matchRequest.getDescription());

        if (matchRequest.getReservationDate() != null && matchRequest.getRentalCost() != null) {
            builder.reservationDate(matchRequest.getReservationDate())
                    .rentalCost(matchRequest.getRentalCost())
                    .reservationCourtId(matchRequest.getReservationCourtId());
        }
        return builder.build();
    }
}
