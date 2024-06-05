package joluphosoin.tennisfunserver.game.data.entity;

import joluphosoin.tennisfunserver.game.data.dto.ScoreDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Score {

    private String userId;

    private ScoreDetailDto scoreDetailDto;

    private int disagreementCount;

    public Score setScoreDetailDto(ScoreDetailDto  scoreDetailDto){
        this.scoreDetailDto = scoreDetailDto;
        return this;
    }
    public static Score toEntity(ScoreDetailDto scoreDetailDto,String userId){

        return Score.builder()
                .userId(userId)
                .scoreDetailDto(scoreDetailDto)
                .disagreementCount(0)
                .build();
    }
}