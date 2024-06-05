package joluphosoin.tennisfunserver.game.controller;

import joluphosoin.tennisfunserver.game.data.dto.ScoreDetailDto;
import joluphosoin.tennisfunserver.game.service.ScoreService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/game/score")
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/confirm")
    public ApiResult<?> registerTempScore(@RequestBody ScoreDetailDto scoreDetailDto, @SessionAttribute("id")String userId){
        scoreService.registerTempScore(scoreDetailDto,userId);
        return ApiResult.onSuccess();
    }
    @PatchMapping("/confirm")
    public ApiResult<?> updateTempScore(@RequestBody ScoreDetailDto scoreDetailDto, @SessionAttribute("id")String userId){
        scoreService.updateTempScore(scoreDetailDto,userId);
        return ApiResult.onSuccess();
    }
}
