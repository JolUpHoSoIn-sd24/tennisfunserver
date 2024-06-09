package joluphosoin.tennisfunserver.business.controller;

import joluphosoin.tennisfunserver.business.data.dto.SaleResDto;
import joluphosoin.tennisfunserver.business.service.SaleService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/sales")
public class SaleController {

    private final SaleService saleService;
    @GetMapping("")
    public ApiResult<SaleResDto> getSale(@RequestParam String courtId){
        return ApiResult.onSuccess(saleService.getSale(courtId));
    }
}
