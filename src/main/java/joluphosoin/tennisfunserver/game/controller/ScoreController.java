package joluphosoin.tennisfunserver.game.controller;

import joluphosoin.tennisfunserver.game.data.dto.ConfirmDto;
import joluphosoin.tennisfunserver.game.data.dto.ScoreDetailDto;
import joluphosoin.tennisfunserver.game.data.entity.Score;
import joluphosoin.tennisfunserver.game.service.ScoreService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/game/score")
public class ScoreController {

    private final ScoreService scoreService;

    @PatchMapping("")
    public ApiResult<?> updateScore(@RequestBody ScoreDetailDto scoreDetailDto, @SessionAttribute("id")String userId){
//        scoreService.reconfirmScore(scoreDetailDto,userId);
        return ApiResult.onSuccess();
    }
    @PostMapping("/confirm")
    public ApiResult<List<Score>> confirmScore(@RequestBody ConfirmDto confirmDto, @SessionAttribute("id")String userId){
        return ApiResult.onSuccess(scoreService.confirmScore(confirmDto,userId));
    }
}
