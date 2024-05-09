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
    CREATED(HttpStatus.OK, "COMMON201", "생성 성공입니다."),
    ACCEPTEED(HttpStatus.OK, "COMMON202", "처리 대기중"),
    NO_CONTENT(HttpStatus.OK, "COMMON204", "컨텐츠가 없습니다.");
    // ~~ 관련 응답
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
