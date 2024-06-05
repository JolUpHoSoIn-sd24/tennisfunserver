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
        AWAIT_FEEDBACK,
        AWAIT_SCORE_CONFIRM,
        POST,
    }

    public static PostGame toEntity(Game game){
        return PostGame.builder()
                .gameId(game.getGameId())
                .postGameStatus(PostGameStatus.AWAIT_FEEDBACK)
                .playerIds(game.getPlayerIds())
                .creationTime(game.getCreationTime())
                .chatRoomId(game.getChatRoomId())
                .rentalCost(game.getRentalCost())
                .paymentStatus(game.getPaymentStatus())
                .matchDetails(game.getMatchDetails())
                .scores(new ArrayList<>())
                .feedbacks(new ArrayList<>())
                .scoreConfirmed(false)
                .build();
    }
    public void addFeedback(FeedbackDto feedbackDto, String userId) {
        Optional<Feedback> existingFeedback = this.feedbacks.stream()
                .filter(feedback -> feedback.getEvaluatorId().equals(userId))
                .findFirst();

        if (existingFeedback.isPresent()) {
            existingFeedback.get().setEntity(feedbackDto);
        } else {
            this.feedbacks.add(Feedback.toEntity(feedbackDto, userId));
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
        if(this.scores.size()==this.playerIds.size()){
            this.postGameStatus = PostGameStatus.AWAIT_SCORE_CONFIRM;
        }
    }

}

