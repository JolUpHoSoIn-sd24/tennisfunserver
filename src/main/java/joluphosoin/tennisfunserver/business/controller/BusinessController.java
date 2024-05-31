package joluphosoin.tennisfunserver.business.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.business.data.dto.AccountReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessResDto;
import joluphosoin.tennisfunserver.business.service.BusinessService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import joluphosoin.tennisfunserver.user.data.dto.LoginDto;
import joluphosoin.tennisfunserver.user.exception.EmailVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BusinessResDto> registerBusinessInfo(@RequestBody @Valid BusinessReqDto businessReqDto) throws MessagingException {
        return ApiResult.onSuccess(SuccessStatus.BUSINESSINFO_CREATED,businessService.registerBusiness(businessReqDto));
    }

    @PutMapping("/account")
    public ApiResult<BusinessResDto> updateAccount(@RequestBody @Valid AccountReqDto accountReqDto){
        return ApiResult.onSuccess(SuccessStatus.ACCOUNTINFO_CREATED,businessService.updateAccount(accountReqDto));
    }
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        try {
            businessService.verifyEmail(token);
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
    @PostMapping(value = "/login", produces = "application/json")
    public ApiResult<BusinessResDto> loginBusiness(@Valid @RequestBody LoginDto loginDto, HttpSession session) {
        BusinessResDto businessResDto = businessService.loginBusiness(loginDto.getEmail(), loginDto.getPassword());

        session.setAttribute("businessId", businessResDto.getId());

        return ApiResult.onSuccess(SuccessStatus.LOGIN_OK,businessResDto);
    }
}
