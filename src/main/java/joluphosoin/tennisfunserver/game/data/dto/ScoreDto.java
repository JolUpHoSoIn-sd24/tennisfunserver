package joluphosoin.tennisfunserver.game.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ScoreDto {
    @Schema(example = "663c6230d2b544d88a6a2968")
    private String userId;
    @Schema(description = "Details of the scores between this player and the opponent")
    private List<ScoreDetailDto> scoreDetails;
}
