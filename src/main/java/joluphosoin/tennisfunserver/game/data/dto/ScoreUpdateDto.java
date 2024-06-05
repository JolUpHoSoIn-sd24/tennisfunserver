package joluphosoin.tennisfunserver.game.data.dto;

import lombok.Getter;

@Getter
public class ScoreUpdateDto {

    private String gameId;

    private ScoreDetailDto scoreDetailDto;
}
