package joluphosoin.tennisfunserver.payload.code.status;

import joluphosoin.tennisfunserver.payload.code.BaseCode;
import joluphosoin.tennisfunserver.payload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // 일반적인 응답
    OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "생성 성공입니다."),
    ACCEPTED(HttpStatus.OK, "COMMON202", "처리 대기중"),
    NO_CONTENT(HttpStatus.OK, "COMMON204", "컨텐츠가 없습니다."),

    // 유저 관련 응답
    LOGIN_OK(HttpStatus.OK, "LOGIN200", "Login Successful"),

    // 코트 관련 응답
    COURT_CREATED(HttpStatus.CREATED, "COURT201", "테니스장 정보가 성공적으로 등록되었습니다."),
    COURTTIME_CREATED(HttpStatus.CREATED, "COURTTIME201", "코트별 대여 가능 시간 및 비용이 성공적으로 등록되었습니다."),

    // 사업자 관련 응답
    BUSINESSINFO_CREATED(HttpStatus.CREATED, "BUSINESSINFO201", "사업자 정보가 성공적으로 제출되었습니다. 관리자의 승인을 기다려주세요."),
    ACCOUNTINFO_CREATED(HttpStatus.CREATED, "ACCOUNTINFO201", "계좌 정보가 성공적으로 등록되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }
    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
