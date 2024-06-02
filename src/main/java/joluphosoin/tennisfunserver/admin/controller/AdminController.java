package joluphosoin.tennisfunserver.admin.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.admin.data.dto.AdminRegistrationDto;
import joluphosoin.tennisfunserver.admin.data.dto.AdminResDto;
import joluphosoin.tennisfunserver.admin.data.entity.Admin;
import joluphosoin.tennisfunserver.admin.service.AdminService;
import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestResDto;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import joluphosoin.tennisfunserver.user.data.dto.LoginDto;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<AdminResDto> registerAdmin(@Valid @RequestBody AdminRegistrationDto adminRegistrationDto) {
        return ApiResult.onSuccess(SuccessStatus.CREATED, adminService.registerAdmin(adminRegistrationDto));
    }
    @PostMapping(value = "/login")
    public ApiResult<AdminResDto> loginAdmin(@Valid @RequestBody LoginDto loginDto, HttpSession session) {
        Admin admin = adminService.loginAdmin(loginDto);
        session.setAttribute("adminId",admin.getId() );

        return ApiResult.onSuccess(SuccessStatus.LOGIN_OK, AdminResDto.toDto(admin));
    }

    @GetMapping("/users")
    public ApiResult<List<UserResDto>> getAllUsers() {
        return ApiResult.onSuccess(SuccessStatus.USERS_RETRIEVED,adminService.getAllUsers());
    }

    @GetMapping("/games")
    public ApiResult<List<GameDetailsDto>> getAllGames() {
        return ApiResult.onSuccess(SuccessStatus.GAMES_RETRIEVED, adminService.getAllGames());
    }

    @GetMapping("/match-requests")
    public ApiResult<List<MatchRequestResDto>> getAllMatchRequests() {
        return ApiResult.onSuccess(SuccessStatus.MATCH_REQUESTS_RETRIEVED,
                adminService.getAllMatchRequests());
    }

    @GetMapping("/payments")
    public ApiResult<List<PaymentInfo>> getAllPayments() {
        return ApiResult.onSuccess(SuccessStatus.PAYMENTS_RETRIEVED, adminService.getAllPayments());
    }
}
