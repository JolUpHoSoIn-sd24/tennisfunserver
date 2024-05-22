package joluphosoin.tennisfunserver.match.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.match.data.dto.FeedbackReqDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResponseDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResultResDto;
import joluphosoin.tennisfunserver.match.service.MatchService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping(value = "/request",produces = "application/json; charset=utf-8")
    public ApiResult<MatchResponseDto> getMatchRequest(@SessionAttribute(name="id") String userId) {
        return ApiResult.onSuccess(matchService.getMatchRequest(userId));
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MatchResponseDto> registerMatchRequest(@RequestBody @Valid MatchRequestDto matchRequestDto, @SessionAttribute(name="id") @Parameter(hidden = true) String userId) {
        return ApiResult.onSuccess(SuccessStatus.CREATED, matchService.registerMatchRequest(matchRequestDto,userId));
    }

    @PutMapping("/request")
    public ApiResult<MatchResponseDto> updateMatchRequest(@RequestBody @Valid MatchRequestDto matchRequestDto,@SessionAttribute(name="id") String userId) {
        return ApiResult.onSuccess(matchService.updateMatchRequest(matchRequestDto,userId));
    }

    @DeleteMapping("/request")
    public ApiResult<String> deleteMatchRequest(@SessionAttribute(name="id") String userId) {

        matchService.deleteMatchRequest(userId);
        return ApiResult.onSuccess("Match request cancelled successfully.");
    }

    @GetMapping(value = "/results",produces = "application/json; charset=utf-8")
    public ApiResult<List<MatchResultResDto>> getMatchResult(@SessionAttribute(name="id") String userId) {
        return ApiResult.onSuccess(matchService.getMatchResult(userId));
    }

    @PostMapping("/results/{matchResultId}/feedback")
    public ApiResult<String> registerFeedback(@PathVariable String matchResultId,
                                              @RequestBody @Valid FeedbackReqDto feedbackReqDto,
                                              @SessionAttribute("id") String userId
    ){
        matchService.registerFeedback(matchResultId,feedbackReqDto,userId);
        return ApiResult.onSuccess("Feedback submitted successfully.");
    }
}


