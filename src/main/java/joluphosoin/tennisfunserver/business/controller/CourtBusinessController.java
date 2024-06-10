package joluphosoin.tennisfunserver.business.controller;

import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.business.data.dto.AutoMatchApplyDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtReqDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtResDto;
import joluphosoin.tennisfunserver.business.data.dto.SimpleCourtResDto;
import joluphosoin.tennisfunserver.business.service.CourtBusinessService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/courts")
public class CourtBusinessController {
    private final CourtBusinessService courtBusinessService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CourtResDto> registerCourt(@RequestBody @Valid CourtReqDto courtReqDto,@SessionAttribute String businessId){
        return ApiResult.onSuccess(SuccessStatus.COURT_CREATED, courtBusinessService.registerCourt(courtReqDto,businessId));
    }

    @PostMapping("/timeslot")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CourtResDto> registerCourtTime(@RequestParam String courtId){
        return ApiResult.onSuccess(SuccessStatus.COURT_CREATED, courtBusinessService.registerCourtTime(courtId));
    }

    @PostMapping("/auto-match")
    public ApiResult<String> applyAutoMatching(@RequestBody @Valid AutoMatchApplyDto autoMatchApplyDto){
        courtBusinessService.applyAutoMatching(autoMatchApplyDto);
        return ApiResult.onSuccess("코트 예약 자동 매칭이 성공적으로 신청되었습니다.");
    }
    @GetMapping(value = "/reservations",produces ="application/json; charset=utf-8")
    public ApiResult<List<SimpleCourtResDto>> getReservationCourts(@RequestParam String courtId){
        return ApiResult.onSuccess(courtBusinessService.getReservationCourts(courtId));
    }
    @GetMapping(value = "/reservations/pending",produces ="application/json; charset=utf-8")
    public ApiResult<List<SimpleCourtResDto>> getPendingReservationCourts(@RequestParam String courtId){
        return ApiResult.onSuccess(courtBusinessService.getPendingReservationCourts(courtId));
    }
}
