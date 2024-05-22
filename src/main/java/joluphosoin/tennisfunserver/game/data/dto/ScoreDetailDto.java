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
public class ScoreDetailDto {
    @Schema(example = "6")
    private int userScore;
    @Schema(example = "3")
    private int opponentScore;
}