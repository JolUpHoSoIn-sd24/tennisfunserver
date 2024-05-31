package joluphosoin.tennisfunserver.game.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "game")
@Getter
@AllArgsConstructor
@Builder
public class Game {

    @MongoId
    private String gameId;

    private GameStatus gameStatus;

    private List<String> playerIds;

    private String courtId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date endTime;

    private String chatRoomId;

    private Double rentalCost;

    @Setter
    private List<Score> scores;

    private boolean scoreConfirmed;

    @Setter
    private List<NTRPFeedback> ntrpFeedbacks;

    @Setter
    private List<MannerFeedback> mannerFeedbacks;

    private Map<String, Boolean> paymentStatus;

    public enum GameStatus {
        PREGAME, INPLAY, POSTGAME
    }
    @Getter
    @Setter
    public static class Score {
        private String userId;
        private Map<String, ScoreDetail> scoreDetails;

    }
    @Getter
    @Setter
    public static class ScoreDetail {
        private int userScore;
        private int opponentScore;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class NTRPFeedback {
        private String userId;
        private String opponentUserId;
        private double ntrp;
        private String comments;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class MannerFeedback {
        private String userId;
        private String opponentUserId;
        private int mannerScore;
        private String comments;
    }

    public static Game toEntity(GameCreationDto gameDto,
                                Map<String, Boolean> paymentStatusMap){
        return Game.builder()
                .playerIds(gameDto.getPlayerIds())
                .courtId(gameDto.getCourtId())
                .startTime(gameDto.getStartTime())
                .endTime(gameDto.getEndTime())
                .chatRoomId(gameDto.getChatRoomId())
                .rentalCost(gameDto.getRentalCost())
                .scoreConfirmed(false)
                .paymentStatus(paymentStatusMap)
                .gameStatus(Game.GameStatus.PREGAME)
                .build();
    }

}