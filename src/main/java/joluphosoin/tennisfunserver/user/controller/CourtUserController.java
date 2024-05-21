package joluphosoin.tennisfunserver.user.controller;

import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.business.data.dto.CourtResDto;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.user.service.CourtUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/courts")
public class CourtUserController {

    private final CourtUserService courtUserService;

    @GetMapping("/search")
    public ApiResult<List<CourtResDto>> searchCourts(@RequestParam @Valid String courtName) {
        return ApiResult.onSuccess(courtUserService.searchCourts(courtName));
    }

}
