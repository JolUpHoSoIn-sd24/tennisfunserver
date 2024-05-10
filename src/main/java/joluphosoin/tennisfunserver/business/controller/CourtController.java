package joluphosoin.tennisfunserver.business.controller;

import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.business.data.dto.CourtReqDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtResDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtTimeSlotReqDto;
import joluphosoin.tennisfunserver.business.service.CourtService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business/courts")
public class CourtController {
    private final CourtService courtService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CourtResDto> registerCourt(@RequestBody @Valid CourtReqDto courtReqDto){
        return ApiResult.onSuccess(SuccessStatus.COURT_CREATED,courtService.registerCourt(courtReqDto));
    }

    @PostMapping("/availability")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CourtResDto> registerCourtTimeSlot(@RequestBody @Valid CourtTimeSlotReqDto courtTimeSlotReqDto){
        return ApiResult.onSuccess(SuccessStatus.COURTTIME_CREATED,courtService.registerCourtTimeSlot(courtTimeSlotReqDto));
    }


}
