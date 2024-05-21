package joluphosoin.tennisfunserver.business.controller;

import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.business.data.dto.*;
import joluphosoin.tennisfunserver.business.service.CourtBusinessService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business/courts")
public class CourtBusinessController {
    private final CourtBusinessService courtBusinessService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CourtResDto> registerCourt(@RequestBody @Valid CourtReqDto courtReqDto){
        return ApiResult.onSuccess(SuccessStatus.COURT_CREATED, courtBusinessService.registerCourt(courtReqDto));
    }

    @PostMapping("/timeslot")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CourtResDto> registerCourtTime(@RequestParam String courtId){
        return ApiResult.onSuccess(SuccessStatus.COURT_CREATED, courtBusinessService.registerCourtTime(courtId));
    }

//    @PostMapping("/availability")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ApiResult<CourtResDto> registerCourtTimeSlot(@RequestBody @Valid CourtTimeSlotReqDto courtTimeSlotReqDto){
//        return ApiResult.onSuccess(SuccessStatus.COURTTIME_CREATED,courtService.registerCourtTimeSlot(courtTimeSlotReqDto));
//    }

    @PostMapping("/auto-match")
    public ApiResult<String> applyAutoMatching(@RequestBody @Valid AutoMatchApplyDto autoMatchApplyDto){
        courtBusinessService.applyAutoMatching(autoMatchApplyDto);
        return ApiResult.onSuccess("코트 예약 자동 매칭이 성공적으로 신청되었습니다.");
    }
//    @GetMapping("/reservations")
//    public ApiResult<SimpleCourtResDto> getReservationCourts(@RequestParam @Valid String courtId){
//        return ApiResult.onSuccess(courtService.getReservationCourts(courtId));
//    }
//    @GetMapping("/reservations/pending")
//    public ApiResult<SimpleCourtResDto> getPendingReservationCourts(@RequestParam @Valid String courtId){
//        return ApiResult.onSuccess(courtService.getPendingReservationCourts(courtId));
//    }
//    @DeleteMapping("/reservations/cancel")
//    public ApiResult<String> cancelReservationCourts(@RequestBody SimpleTimeSlotDto timeSlotDto){
//        return ApiResult.onSuccess(courtService.cancelReservationCourts(timeSlotDto));
//    }
//    @PatchMapping("/reservations/block-times")
//    public ApiResult<String> blockReservationCourts(@RequestBody SimpleTimeSlotDto timeSlotDto){
//        return ApiResult.onSuccess(courtService.blockReservationCourts(timeSlotDto));
//    }

}
