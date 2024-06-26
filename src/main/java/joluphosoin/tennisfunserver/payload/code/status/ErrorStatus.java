package joluphosoin.tennisfunserver.payload.code.status;

import joluphosoin.tennisfunserver.payload.code.BaseErrorCode;
import joluphosoin.tennisfunserver.payload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {


    // 가장 일반적인 응답
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON500","서버 에러, 관리자에게 문의 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS,"COMMON429","3시간 이후에 작성해주세요."),

    // 회원가입 응답
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT,JOIN_PREFIX+HttpStatus.CONFLICT.value(),"이미 존재하는 계정입니다."),
    NICKNAME_NOT_UNIQUE(HttpStatus.CONFLICT,JOIN_PREFIX+HttpStatus.CONFLICT.value(),"이미 존재하는 닉네임입니다."),

    // 로그인 응답
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED,LOGIN_PREFIX+HttpStatus.UNAUTHORIZED.value(),"다시 로그인 해주세요."),
    ID_NOT_EXIST(HttpStatus.NOT_FOUND,LOGIN_PREFIX+HttpStatus.NOT_FOUND.value(),"ID를 잘못 입력하셨습니다."),
    PW_NOT_MATCH(HttpStatus.FORBIDDEN,LOGIN_PREFIX+HttpStatus.FORBIDDEN.value(),"입력하신 정보가 회원정보와 일치하지 않습니다."),
    EMAIL_NOT_VAILD(HttpStatus.UNAUTHORIZED,"LOGIN401","Email not verified"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "LOGIN409", "중복된 이메일 아이디입니다."),
    // 유저 응답
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "해당 id의 유저를 찾을 수 없습니다."),

    // 가게 위치 응답
    SHOP_COORDINATES_NOT_FOUND(HttpStatus.NO_CONTENT, "SHOP_COORDINATES2040" , "가게 위치를 찾을 수 없습니다."),

    // 매칭 요청 응답
    MATCHREQ_NOT_FOUND(HttpStatus.NOT_FOUND, "MATCHREQ404", "매칭요청 정보를 찾을 수 없습니다."),

    // 매칭 결과 응답
    MATCHRESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "MATCHRESULT404", "매칭결과 정보를 찾을 수 없습니다."),

    // 코트 상태 응답
    COURT_NOT_FOUND(HttpStatus.NOT_FOUND, "COURT404", "해당 이름을 가진 코트를 찾을 수 없습니다."),
    COURT_NOT_UNIQUE(HttpStatus.CONFLICT,"COURT409","이미 존재하는 코트입니다."),

    // 사업자 응답
    BUSINESS_NOT_FOUND(HttpStatus.NOT_FOUND, "BUSINESS404", "해당 id의 사업자를 찾을 수 없습니다."),
    BUSINESS_NOT_VAILD(HttpStatus.UNAUTHORIZED,"BUSINESS401","관리자로부터 승인이 필요합니다"),

    // 코트 일정 관련 응답
    TIMESLOT_NOT_FOUND(HttpStatus.NOT_FOUND, "COURTTIMESLOT404", "이 코트에 할당 된 시간을 찾을 수 없습니다."),

    // FILE 응답
    INVALID_URL(HttpStatus.BAD_REQUEST,"FILE4000","URL형식이 올바르지 않습니다."),

    // GAME 응답
    GAME_HISTORY_NO_CONTENT(HttpStatus.NO_CONTENT, "GAME204" , "참여중인 게임이 없습니다"),
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "GAME404", "게임을 찾을 수 없습니다"),
    GAME_PAYMENT_REQUIRED(HttpStatus.UNPROCESSABLE_ENTITY, "GAME422", "게임이 아직 결제되지 않았습니다."),

    // 결제 정보 응답
    PAYINFO_NOT_FOUND(HttpStatus.NOT_FOUND, "PAYINFO404", "결제 정보를 찾을 수 없습니다"),


    // SCORE 응답
    SCORE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCORE404", "등록된 점수를 찾을 수 없습니다"),
    SCORE_UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "SCORE400", "점수 확정이 완료되었습니다. 업데이트가 불가합니다"),
    // ADMIN 응답
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN404", "해당 email을 가진 admin를 찾을 수 없습니다."),
    MATCH_REQUESTS_RETRIEVED_FAILED(HttpStatus.BAD_REQUEST, "MATCH_REQUESTS_RETRIEVED400", "Failed to retrieve match requests");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    @Override
    public ErrorReasonDto getReason(){
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }
    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
