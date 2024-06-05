package joluphosoin.tennisfunserver.game.data.entity;

import joluphosoin.tennisfunserver.game.data.dto.FeedbackDto;
import joluphosoin.tennisfunserver.game.data.dto.FeedbackResDto;
import joluphosoin.tennisfunserver.game.data.dto.ScoreDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Feedback {

    private String evaluatorId; // 평가자

    private String evaluatedId; // 평가 당하는 자

    private MannersRating mannersRating;

    private double suggestedNTRP;

    private String comments;

    public enum MannersRating {
        POOR,
        AVERAGE,
        EXCELLENT
    }
    public static Feedback toEntity(FeedbackDto feedbackDto,String userId,String opponentId){

        return Feedback.builder()
                .evaluatorId(userId)
                .evaluatedId(opponentId)
                .suggestedNTRP(feedbackDto.getSuggestedNTRP())
                .mannersRating(feedbackDto.getMannersRating())
                .comments(feedbackDto.getComments())
                .build();
    }

    public Feedback setEntity(FeedbackDto feedbackDto){
        this.suggestedNTRP = feedbackDto.getSuggestedNTRP();
        this.mannersRating = feedbackDto.getMannersRating();
        return this;
    }

}
