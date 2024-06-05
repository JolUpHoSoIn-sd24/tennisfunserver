package joluphosoin.tennisfunserver.game.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.game.data.entity.Game.GameStatus;
import joluphosoin.tennisfunserver.game.data.entity.Score;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GameCreationDto {

    @Schema(example = "PREGAME")
    private GameStatus gameStatus;

    @Schema(example = "[\"663c6230d2b544d88a6a2968\", \"663a0b8954ded836e72ea57d\"]")
    private List<String> playerIds;

    @Schema(example = "604f8d3e8f8f8f8d3e8f8f8f")
    private String courtId;

    @Schema(example = "2024-05-20T15:30:00Z")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date startTime;

    @Schema(example = "2024-05-20T15:30:00Z")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date endTime;

    @Schema(example = "room123")
    private String chatRoomId;

    @Schema(example = "50.0")
    private Double rentalCost;

    private MatchResult.MatchDetails matchDetails;

    public static GameCreationDto toDto(MatchResult matchResult, Court court){

        Map<String, String> userAndMatchRequests = matchResult.getUserAndMatchRequests();

        MatchResult.MatchDetails matchDetails = matchResult.getMatchDetails();

        long durationInMillis = matchDetails.getEndTime().getTime() - matchDetails.getStartTime().getTime();
        long durationInMinutes = durationInMillis / 60000;
        double rentalCost = ((double) durationInMinutes / 30) * court.getRentalCostPerHalfHour();

        return GameCreationDto.builder()
                .gameStatus(GameStatus.PREGAME)
                .playerIds(new ArrayList<>(userAndMatchRequests.keySet()))
                .courtId(matchResult.getMatchDetails().getCourtId())
                .startTime(matchDetails.getStartTime())
                .endTime(matchDetails.getEndTime())
                .rentalCost(rentalCost) // 계산된 임대 비용 설정
                .matchDetails(matchDetails)
                .build();
    }

}
