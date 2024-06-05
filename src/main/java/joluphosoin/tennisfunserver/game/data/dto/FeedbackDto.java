package joluphosoin.tennisfunserver.game.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joluphosoin.tennisfunserver.game.data.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FeedbackDto {

    @Schema(example = "{ \"userScore\" : 6, \"opponentScore\" : 8}")
    private ScoreDetailDto scoreDetailDto;

    @Schema(example = "EXCELLENT")
    private Feedback.MannersRating mannersRating;

    @Schema(example = "3.5")
    private double suggestedNTRP;

    @Schema(example = "Very respectful and fair play.")
    private String comments;
}
