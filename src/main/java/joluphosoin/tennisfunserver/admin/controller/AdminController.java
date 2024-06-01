package joluphosoin.tennisfunserver.admin.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.admin.data.dto.RegistrationDto;
import joluphosoin.tennisfunserver.admin.service.AdminService;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.user.data.dto.LoginDto;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.user.exception.UserLoginException;
import joluphosoin.tennisfunserver.user.exception.UserRegistrationException;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import joluphosoin.tennisfunserver.match.repository.MatchRequestRepository;
import joluphosoin.tennisfunserver.payment.repository.PaymentInfoRepository;
import joluphosoin.tennisfunserver.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final MatchRequestRepository matchRequestRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegistrationDto registrationDto) {
        try {
            adminService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "COMMON201", "Register Success", null)
            );
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, "USER400", e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "INTERNAL_SERVER_ERROR", "Internal Server Error", null)
            );
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        return ResponseEntity.ok(new ApiResponse(true, "USERS_RETRIEVED", "All users retrieved successfully", result));
    }

    @GetMapping("/games")
    public ResponseEntity<ApiResponse> getAllGames() {
        List<Game> games = gameRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("games", games);
        return ResponseEntity.ok(new ApiResponse(true, "GAMES_RETRIEVED", "All games retrieved successfully", result));
    }

    @GetMapping("/match-requests")
    public ResponseEntity<ApiResponse> getAllMatchRequests() {
        try {
            List<MatchRequest> matchRequests = matchRequestRepository.findAll();
            Map<String, Object> result = new HashMap<>();
            result.put("matchRequests", matchRequests);
            return ResponseEntity.ok(new ApiResponse(true, "MATCH_REQUESTS_RETRIEVED", "All match requests retrieved successfully", result));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, "MATCH_REQUESTS_RETRIEVED_FAILED", "Failed to retrieve match requests", null));
        }
    }


    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> getAllPayments() {
        List<PaymentInfo> paymentInfos = paymentInfoRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("payments", paymentInfos);
        return ResponseEntity.ok(new ApiResponse(true, "PAYMENTS_RETRIEVED", "All payment information retrieved successfully", result));
    }
}
