package joluphosoin.tennisfunserver.payload.code;

public interface BaseErrorCode {
    public static final String LOGIN_PREFIX = "LOGIN";
    public static final String JOIN_PREFIX = "JOIN";
    public ErrorReasonDto getReason();
    public ErrorReasonDto getReasonHttpStatus();
}
