package JolUpHoSoIn.TennisFun_Server.user.exception;

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException(String message) {
        super(message);
    }
}