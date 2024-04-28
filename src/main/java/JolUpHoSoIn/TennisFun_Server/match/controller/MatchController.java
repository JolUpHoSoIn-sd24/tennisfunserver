package JolUpHoSoIn.TennisFun_Server.match.controller;

import JolUpHoSoIn.TennisFun_Server.apiPayload.ApiResult;
import JolUpHoSoIn.TennisFun_Server.apiPayload.code.status.SuccessStatus;
import JolUpHoSoIn.TennisFun_Server.match.data.dto.MatchRequestDto;
import JolUpHoSoIn.TennisFun_Server.match.data.dto.MatchResponseDto;
import JolUpHoSoIn.TennisFun_Server.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/request")
    public ApiResult<MatchResponseDto> getMatchRequest(@RequestParam String requestId){
        return ApiResult.onSuccess(matchService.getMatchRequest(requestId));
    }

    @PostMapping("/request")
    public ApiResult<MatchResponseDto> registerMatchRequest(@RequestBody MatchRequestDto matchRequestDto){
        return ApiResult.onSuccess(SuccessStatus._CREATED,matchService.registermatchRequest(matchRequestDto));
    }
    @PutMapping("/request")
    public ApiResult<MatchResponseDto> updateMatchRequest(@RequestBody MatchRequestDto matchRequestDto, @RequestParam String requestId){
        return ApiResult.onSuccess(matchService.updateMatchRequest(matchRequestDto,requestId));
    }
    @DeleteMapping("/request")
    public ApiResult<String> deleteMatchRequest(@RequestParam String requestId){

        matchService.deleteMatchRequest(requestId);
        return ApiResult.onSuccess("Match request cancelled successfully.");
    }
}


