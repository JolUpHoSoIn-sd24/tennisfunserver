package joluphosoin.tennisfunserver.game.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import joluphosoin.tennisfunserver.game.data.dto.FeedbackDto;
import joluphosoin.tennisfunserver.game.data.dto.ScoreDetailDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.*;

@Document(collection = "postgame")
@Getter
@Builder
public class PostGame{

    @MongoId
    private String gameId;

    @Setter
    private PostGameStatus postGameStatus;

    private List<String> playerIds;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date creationTime;

    private String chatRoomId;

    private Double rentalCost;

    private Map<String, Boolean> paymentStatus;

    private MatchResult.MatchDetails matchDetails;

    @Setter
    private List<Score> scores;

    private boolean scoreConfirmed;

    private List<Feedback> feedbacks;

    public enum PostGameStatus {
        AWAIT_SCORE_CONFIRM,
        POSTGAME,
    }

    public static PostGame toEntity(Game game){
        return PostGame.builder()
                .gameId(game.getGameId())
                .postGameStatus(PostGameStatus.AWAIT_SCORE_CONFIRM)
                .playerIds(game.getPlayerIds())
                .creationTime(game.getCreationTime())
                .chatRoomId(game.getChatRoomId())
                .rentalCost(game.getRentalCost())
                .paymentStatus(game.getPaymentStatus())
                .matchDetails(game.getMatchDetails())
                .scores(game.getScores())
                .feedbacks(game.getFeedbacks())
                .scoreConfirmed(false)
                .build();
    }


}

