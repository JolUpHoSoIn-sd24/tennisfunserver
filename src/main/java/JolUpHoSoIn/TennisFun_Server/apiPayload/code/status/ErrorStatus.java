package JolUpHoSoIn.TennisFun_Server.apiPayload.code.status;

import JolUpHoSoIn.TennisFun_Server.apiPayload.code.BaseErrorCode;
import JolUpHoSoIn.TennisFun_Server.apiPayload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON500","서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS,"COMMON429","3시간 이후에 작성해주세요."),

    // 회원가입 응답
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 계정입니다."),
    NICKNAME_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 닉네임입니다."),

    // 로그인 응답
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED,"LOGIN"+HttpStatus.UNAUTHORIZED.value(),"세션이 만료되었습니다."),
    ID_NOT_EXIST(HttpStatus.NOT_FOUND,"LOGIN"+HttpStatus.NOT_FOUND.value(),"ID를 잘못 입력하셨습니다."),
    PW_NOT_MATCH(HttpStatus.FORBIDDEN,"LOGIN"+HttpStatus.FORBIDDEN.value(),"입력하신 정보가 회원정보와 일치하지 않습니다."),

    // 유저 응답
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "해당 id의 유저를 찾을 수 없습니다."),

    // 가게 위치 응답
    SHOP_COORDINATES_NOT_FOUND(HttpStatus.NO_CONTENT, "SHOP_COORDINATES2040" , "가게 위치를 찾을 수 없습니다."),

    // 매칭 요청 응답
    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "MATCH404", "매칭요청 정보를 찾을 수 없습니다.");


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
