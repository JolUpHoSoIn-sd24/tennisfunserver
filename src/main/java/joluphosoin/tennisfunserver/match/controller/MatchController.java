package joluphosoin.tennisfunserver.match.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/request")
    public ApiResult<MatchResponseDto> getMatchRequest(@RequestParam String requestId,@SessionAttribute(name="id") String userId) {
        return ApiResult.onSuccess(matchService.getMatchRequest(requestId,userId));
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MatchResponseDto> registerMatchRequest(@RequestBody MatchRequestDto matchRequestDto,@SessionAttribute(name="id") String userId) {
        return ApiResult.onSuccess(SuccessStatus.CREATED, matchService.registermatchRequest(matchRequestDto,userId));
    }

    @PutMapping("/request")
    public ApiResult<MatchResponseDto> updateMatchRequest(@RequestBody MatchRequestDto matchRequestDto, @RequestParam String requestId,@SessionAttribute(name="id") String userId) {
        return ApiResult.onSuccess(matchService.updateMatchRequest(matchRequestDto, requestId,userId));
    }

    @DeleteMapping("/request")
    public ApiResult<String> deleteMatchRequest(@RequestParam String requestId) {

        matchService.deleteMatchRequest(requestId);
        return ApiResult.onSuccess("Match request cancelled successfully.");
    }

    @GetMapping("/results")
    public ApiResult<MatchResultResDto> getMatchResult(@RequestParam String matchRequestId, @SessionAttribute(name="id")
    String userId) {
        return ApiResult.onSuccess(matchService.getMatchResult(matchRequestId, userId));
    }

    @PostMapping("/results/{matchRequestId}/feedback")
    public ApiResult<String> registerFeedback(@PathVariable String matchRequestId, @RequestBody FeedbackReqDto feedbackReqDto){
        matchService.registerFeedback(matchRequestId,feedbackReqDto);
        return ApiResult.onSuccess("Feedback submitted successfully.");
    }
}


