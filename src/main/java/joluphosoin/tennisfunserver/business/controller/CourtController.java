package joluphosoin.tennisfunserver.business.controller;

import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.business.data.dto.CourtReqDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtTimeSlotReqDto;
import joluphosoin.tennisfunserver.business.service.CourtService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business/courts")
public class CourtController {
    private final CourtService courtService;

    @PostMapping("/")
    public ApiResult<String> registerCourt(@RequestBody @Valid CourtReqDto courtReqDto){
        courtService.registerCourt(courtReqDto);
        return ApiResult.onSuccess(SuccessStatus.CREATED,"테니스장 정보가 성공적으로 등록되었습니다.");
    }

    @PostMapping("/availability")
    public ApiResult<String> registerCourtTimeSlot(@RequestBody @Valid CourtTimeSlotReqDto courtTimeSlotReqDto){
        courtService.registerCourtTimeSlot(courtTimeSlotReqDto);
        return ApiResult.onSuccess(SuccessStatus.CREATED,"코트별 대여 가능 시간 및 비용이 성공적으로 등록되었습니다.");
    }


}
