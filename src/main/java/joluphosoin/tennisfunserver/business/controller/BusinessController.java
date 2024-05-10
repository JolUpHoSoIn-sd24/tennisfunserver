package joluphosoin.tennisfunserver.business.controller;

import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.business.data.dto.AccountReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessInfoReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessInfoResDto;
import joluphosoin.tennisfunserver.business.service.BusinessService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BusinessInfoResDto> registerBusinessInfo(@RequestBody @Valid BusinessInfoReqDto businessInfoReqDto){
        return ApiResult.onSuccess(SuccessStatus.BUSINESSINFO_CREATED,businessService.registerBusinessInfo(businessInfoReqDto));
    }

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BusinessInfoResDto> registerAccount(@RequestBody @Valid AccountReqDto accountReqDto){
        return ApiResult.onSuccess(SuccessStatus.ACCOUNTINFO_CREATED,businessService.registerAccount(accountReqDto));
    }


}
