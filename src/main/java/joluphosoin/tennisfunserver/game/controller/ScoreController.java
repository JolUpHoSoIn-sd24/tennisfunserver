package joluphosoin.tennisfunserver.game.controller;

import joluphosoin.tennisfunserver.game.data.dto.ConfirmDto;
import joluphosoin.tennisfunserver.game.data.dto.ScoreUpdateDto;
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

    @PostMapping("/confirm")
    public ApiResult<List<Score>> confirmScore(@RequestBody ConfirmDto confirmDto, @SessionAttribute("id")String userId){
        return ApiResult.onSuccess(scoreService.confirmScore(confirmDto,userId));
    }
    @PatchMapping("")
    public ApiResult<List<Score>> updateScore(@RequestBody ScoreUpdateDto scoreUpdateDto, @SessionAttribute("id")String userId){
        return ApiResult.onSuccess(scoreService.updateScore(scoreUpdateDto,userId));
    }
}
