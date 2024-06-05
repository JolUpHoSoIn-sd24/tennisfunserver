package joluphosoin.tennisfunserver.game.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import joluphosoin.tennisfunserver.game.data.dto.FeedbackDto;
import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import joluphosoin.tennisfunserver.game.data.dto.ScoreDetailDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.*;

@Document(collection = "game")
@Getter
@AllArgsConstructor
@Builder
public class Game {

    @MongoId
    private String gameId;

    @Setter
    private GameStatus gameStatus;

    private List<String> playerIds;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date creationTime;

    private String chatRoomId;

    private Double rentalCost;

    private Map<String, Boolean> paymentStatus;

    private MatchResult.MatchDetails matchDetails;

    @Setter
    private List<Score> scores;

    private List<Feedback> feedbacks;

    public enum GameStatus {
        PREGAME,
        INPLAY,
        AWAIT_FEEDBACK
    }

    public static Game toEntity(GameCreationDto gameDto,
                                Map<String, Boolean> paymentStatusMap){
        return Game.builder()
                .playerIds(gameDto.getPlayerIds())
                .chatRoomId(gameDto.getChatRoomId())
                .rentalCost(gameDto.getRentalCost())
                .paymentStatus(paymentStatusMap)
                .gameStatus(GameStatus.PREGAME)
                .creationTime(new Date())
                .matchDetails(gameDto.getMatchDetails())
                .scores(new ArrayList<>())
                .feedbacks(new ArrayList<>())
                .build();
    }

    public void addFeedback(FeedbackDto feedbackDto, String userId,String opponentId) {
        this.gameStatus = GameStatus.AWAIT_FEEDBACK;
        Optional<Feedback> existingFeedback = this.feedbacks.stream()
                .filter(feedback -> feedback.getEvaluatorId().equals(userId))
                .findFirst();

        if (existingFeedback.isPresent()) {
            existingFeedback.get().setEntity(feedbackDto);
        } else {
            this.feedbacks.add(Feedback.toEntity(feedbackDto, userId,opponentId));
        }
    }


    public void addScore(ScoreDetailDto scoreDetailDto, String userId) {
        Optional<Score> existingScore = this.scores.stream()
                .filter(score -> score.getUserId().equals(userId))
                .findFirst();

        if (existingScore.isPresent()) {
            existingScore.get().setScoreDetailDto(scoreDetailDto);  // 기존 점수 업데이트
        } else {
            this.scores.add(Score.toEntity(scoreDetailDto, userId));  // 새 점수 추가
        }
    }

}