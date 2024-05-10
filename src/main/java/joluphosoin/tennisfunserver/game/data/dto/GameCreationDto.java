package joluphosoin.tennisfunserver.game.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import joluphosoin.tennisfunserver.game.data.entity.Game.GameStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GameCreationDto {
    @Schema(example = "PREGAME")
    private GameStatus gameStatus;
    @Schema(example = "[\"663c6230d2b544d88a6a2968\", \"663a0b8954ded836e72ea57d\"]")
    private List<String> playerIds;
    @Schema(example = "604f8d3e8f8f8f8d3e8f8f8f")
    private String courtId;
    @Schema(example = "2024-05-20T15:30:00Z")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date dateTime;
    @Schema(example = "room123")
    private String chatRoomId;
    @Schema(example = "50.0")
    private Double rentalCost;
    @Schema(description = "List of scores for each player in the game")
    private List<ScoreDto> scores;
    @Schema(example = "true")
    private boolean scoreConfirmed;
    @Schema(description = "List of NTRP feedbacks from players about each other")
    private List<NTRPFeedbackDto> ntrpFeedbacks;
    @Schema(description = "List of manner feedbacks from players about each other")
    private List<MannerFeedbackDto> mannerFeedbacks;

    @Getter
    @Setter
    public static class ScoreDto {
        @Schema(example = "663c6230d2b544d88a6a2968")
        private String userId;
        @Schema(description = "Details of the scores between this player and the opponent")
        private List<ScoreDetailDto> scoreDetails;
    }
    @Getter
    @Setter
    public static class ScoreDetailDto {
        @Schema(example = "6")
        private int userScore;
        @Schema(example = "3")
        private int opponentScore;
    }
    @Getter
    @Setter
    public static class NTRPFeedbackDto {
        @Schema(example = "663c6230d2b544d88a6a2968")
        private String userId;
        @Schema(example = "663a0b8954ded836e72ea57d")
        private String opponentUserId;
        @Schema(example = "3.5")
        private double ntrp;
        @Schema(example = "Good performance but needs consistency.")
        private String comments;
    }
    @Getter
    @Setter
    public static class MannerFeedbackDto {
        @Schema(example = "663a0b8954ded836e72ea57d")
        private String userId;
        @Schema(example = "663c6230d2b544d88a6a2968")
        private String opponentUserId;
        @Schema(example = "8")
        private int mannerScore;
        @Schema(example = "Very respectful and fair play.")
        private String comments;
    }
}
