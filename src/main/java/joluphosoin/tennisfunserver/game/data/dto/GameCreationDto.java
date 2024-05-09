package joluphosoin.tennisfunserver.game.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import joluphosoin.tennisfunserver.game.data.entity.Game.GameStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GameCreationDto {
    private GameStatus gameStatus;
    private List<String> playerIds;
    private String courtId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date dateTime;

    private String chatRoomId;
    private Double rentalCost;
    private List<ScoreDto> scores;
    private boolean scoreConfirmed;
    private List<NTRPFeedbackDto> ntrpFeedbacks;
    private List<MannerFeedbackDto> mannerFeedbacks;

    @Getter
    @Setter
    public static class ScoreDto {
        private String userId;
        private List<ScoreDetailDto> scoreDetails;
    }
    @Getter
    @Setter
    public static class ScoreDetailDto {
        private int userScore;
        private int opponentScore;
    }
    @Getter
    @Setter
    public static class NTRPFeedbackDto {
        private String userId;
        private String opponentUserId;
        private double ntrp;
        private String comments;
    }
    @Getter
    @Setter
    public static class MannerFeedbackDto {
        private String userId;
        private String opponentUserId;
        private int mannerScore;
        private String comments;
    }
}
