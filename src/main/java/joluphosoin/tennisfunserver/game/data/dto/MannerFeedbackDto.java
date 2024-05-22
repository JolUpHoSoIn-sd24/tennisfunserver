package joluphosoin.tennisfunserver.game.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MannerFeedbackDto {
    @Schema(example = "663a0b8954ded836e72ea57d")
    private String userId;
    @Schema(example = "663c6230d2b544d88a6a2968")
    private String opponentUserId;
    @Schema(example = "8")
    private int mannerScore;
    @Schema(example = "Very respectful and fair play.")
    private String comments;
}