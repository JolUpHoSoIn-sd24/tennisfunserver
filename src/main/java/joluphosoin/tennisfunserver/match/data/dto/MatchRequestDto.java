package joluphosoin.tennisfunserver.match.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;
@Getter
@AllArgsConstructor
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

    @Schema(example = "126.887847771379")
    private Double x;

    @Schema(example = "37.5204279064529")
    private Double y;

    @Schema(example = "4.5")
    private Double maxDistance; // 최대 이동 가능 거리

    @Schema(example ="[\"court1\", \"court2\"]")
    private List<String> dislikedCourts;

    @Schema(example ="30")
    @NotNull
    private Integer minTime;

    @Schema(example ="120")
    @NotNull
    private Integer maxTime;

    @Schema(example = "false")
    @NotNull
    private Boolean isReserved;

    @Schema(example = "courtId")
    private String reservationCourtId;

    @Schema(example ="2024-05-01")
    private Date reservationDate;

    @Schema(example ="20000")
    private Integer rentalCost;

    @Schema(example ="테니스 랠리 연습을 위한 매치 요청")
    private String description;

}
