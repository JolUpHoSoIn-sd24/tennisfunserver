package joluphosoin.tennisfunserver.game.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joluphosoin.tennisfunserver.game.data.entity.Feedback;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class FeedbackResDto {

    @Schema(example = "663c6230d2b544d88a6a2968")
    private String opponentId;

    @Schema(example = "663c6230d2b544d88a6a2968")
    private String opponentName;

    @Schema(example = "663c6230d2b544d88a6a2968")
    private Date startTime;

    @Schema(example = "663c6230d2b544d88a6a2968")
    private Date endTime;

    @Schema(example = "663c6230d2b544d88a6a2968")
    private boolean isSingles;

    @Schema(example = "{ \"userScore\" : 6, \"opponentScore\" : 8}")
    private ScoreDetailDto scoreDetailDto;

    @Schema(example = "EXCELLENT")
    private Feedback.MannersRating mannersRating;

    @Schema(example = "3.5")
    private double suggestedNTRP;

    @Schema(example = "Very respectful and fair play.")
    private String comments;

    public static FeedbackResDto toDto(FeedbackDto feedbackDto, User opponent, Game game){

        return FeedbackResDto.builder()
                .opponentId(opponent.getId())
                .opponentName(opponent.getName())
                .startTime(game.getMatchDetails().getStartTime())
                .endTime(game.getMatchDetails().getEndTime())
                .isSingles(game.getMatchDetails().isSingles())
                .scoreDetailDto(feedbackDto.getScoreDetailDto())
                .mannersRating(feedbackDto.getMannersRating())
                .suggestedNTRP(feedbackDto.getSuggestedNTRP())
                .comments(feedbackDto.getComments())
                .build();
    }

    public static FeedbackResDto toDto(FeedbackDto feedbackDto, User opponent, PostGame game){

        return FeedbackResDto.builder()
                .opponentId(opponent.getId())
                .opponentName(opponent.getName())
                .startTime(game.getMatchDetails().getStartTime())
                .endTime(game.getMatchDetails().getEndTime())
                .isSingles(game.getMatchDetails().isSingles())
                .scoreDetailDto(feedbackDto.getScoreDetailDto())
                .mannersRating(feedbackDto.getMannersRating())
                .suggestedNTRP(feedbackDto.getSuggestedNTRP())
                .comments(feedbackDto.getComments())
                .build();
    }
}
