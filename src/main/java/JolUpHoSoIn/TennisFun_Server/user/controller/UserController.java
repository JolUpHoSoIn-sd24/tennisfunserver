package JolUpHoSoIn.TennisFun_Server.user.controller;

import JolUpHoSoIn.TennisFun_Server.user.Service.UserService;
import JolUpHoSoIn.TennisFun_Server.user.data.dto.ApiResponse;
import JolUpHoSoIn.TennisFun_Server.user.data.dto.RegistrationDto;
import JolUpHoSoIn.TennisFun_Server.user.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegistrationDto registrationDto) {
        try {
            userService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "COMMON201", "회원가입 성공", null)
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
}
