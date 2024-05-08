package joluphosoin.tennisfunserver.match.controller;

import joluphosoin.tennisfunserver.match.data.dto.FeedbackReqDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResponseDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResultResDto;
import joluphosoin.tennisfunserver.match.service.MatchService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/request")
    public ApiResult<MatchResponseDto> getMatchRequest(@RequestParam String requestId) {
        return ApiResult.onSuccess(matchService.getMatchRequest(requestId));
    }

    @PostMapping("/request")
    public ApiResult<MatchResponseDto> registerMatchRequest(@RequestBody MatchRequestDto matchRequestDto) {
        return ApiResult.onSuccess(SuccessStatus._CREATED, matchService.registermatchRequest(matchRequestDto));
    }

    @PutMapping("/request")
    public ApiResult<MatchResponseDto> updateMatchRequest(@RequestBody MatchRequestDto matchRequestDto, @RequestParam String requestId) {
        return ApiResult.onSuccess(matchService.updateMatchRequest(matchRequestDto, requestId));
    }

    @DeleteMapping("/request")
    public ApiResult<String> deleteMatchRequest(@RequestParam String requestId) {

        matchService.deleteMatchRequest(requestId);
        return ApiResult.onSuccess("Match request cancelled successfully.");
    }

    @GetMapping("/results")
    public ApiResult<MatchResultResDto> getMatchResult(@RequestParam String matchRequestId, @RequestParam String userId) {
        return ApiResult.onSuccess(matchService.getMatchResult(matchRequestId, userId));
    }

    @PostMapping("/results/{matchId}/feedback")
    public ApiResult<String> registerFeedback(@PathVariable String matchRequestId, @RequestBody FeedbackReqDto feedbackReqDto){
        matchService.registerFeedback(matchRequestId,feedbackReqDto);
        return ApiResult.onSuccess("Feedback submitted successfully.");
    }
}


