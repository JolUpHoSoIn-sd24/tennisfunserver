package joluphosoin.tennisfunserver.auth;

import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=utf-8")
public class AuthController {
    @GetMapping("/expired")
    public void expired() {
        throw new GeneralException(ErrorStatus.SESSION_EXPIRED);
    }

}
