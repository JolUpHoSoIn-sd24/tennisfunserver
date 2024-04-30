package joluphosoin.tennisfunserver.user.controller;

import joluphosoin.tennisfunserver.user.service.UserService;
import joluphosoin.tennisfunserver.user.data.dto.ApiResponse;
import joluphosoin.tennisfunserver.user.data.dto.RegistrationDto;
import joluphosoin.tennisfunserver.user.exception.EmailVerificationException;
import joluphosoin.tennisfunserver.user.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


}
