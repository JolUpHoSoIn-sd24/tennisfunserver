package joluphosoin.tennisfunserver.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import joluphosoin.tennisfunserver.response.ApiResponse;
import joluphosoin.tennisfunserver.user.data.dto.LocationUpdateDto;
import joluphosoin.tennisfunserver.user.data.dto.LoginDto;
import joluphosoin.tennisfunserver.user.data.dto.RegistrationDto;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.exception.EmailVerificationException;
import joluphosoin.tennisfunserver.user.exception.UserRegistrationException;
import joluphosoin.tennisfunserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegistrationDto registrationDto) {
        try {
            userService.registerUser(registrationDto);
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

    @PostMapping(value = "/login")
    public ApiResult<UserResDto> loginUser(@Valid @RequestBody LoginDto loginDto, HttpSession session) {
        User user = userService.loginUser(loginDto.getEmail(), loginDto.getPassword());
        session.setAttribute("id", user.getId());

        return ApiResult.onSuccess(SuccessStatus.LOGIN_OK,UserResDto.toDto(user));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        try {
            userService.verifyEmail(token);
            String htmlResponse = "<html><body><h1>Verification Successful</h1>" +
                    "<p>Your email has been successfully verified.</p></body></html>";
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlResponse);
        } catch (EmailVerificationException e) {
            String htmlResponse = "<html><body><h1>Verification Failed</h1>" +
                    "<p>" + e.getMessage() + "</p></body></html>";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_HTML).body(htmlResponse);
        } catch (Exception e) {
            String htmlResponse = "<html><body><h1>Internal Server Error</h1>" +
                    "<p>Please try again later.</p></body></html>";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_HTML).body(htmlResponse);
        }
    }

    @PatchMapping("/location")
    public ResponseEntity<ApiResponse> updateLocation(@RequestBody LocationUpdateDto locationUpdateDto, HttpSession session) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse(false, "AUTH001", "User is not logged in", null)
            );
        }

        try {
            userService.updateUserLocation(id, locationUpdateDto);
            return ResponseEntity.ok(
                    new ApiResponse(true, "LOC200", "Location updated successfully", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "INTERNAL_ERROR", e.getMessage(), null)
            );
        }
    }
    @GetMapping(value = "", produces = "application/json; charset=utf-8")
    public ApiResult<UserResDto> getUserInfo(@SessionAttribute("id")String userId){
        return ApiResult.onSuccess(userService.getUserInfo(userId));
    }

    @DeleteMapping("/revoke")
    public ApiResult<UserResDto> revoke(@SessionAttribute("id")String userId){
        return ApiResult.onSuccess(userService.revoke(userId));
    }

}
