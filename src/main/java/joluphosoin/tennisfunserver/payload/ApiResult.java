package joluphosoin.tennisfunserver.payload;

import joluphosoin.tennisfunserver.payload.code.BaseCode;
import joluphosoin.tennisfunserver.payload.code.BaseErrorCode;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@NoArgsConstructor(force = true)
public class ApiResult<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    /*
    성공한 경우 응답 생성
    1. 일반 200번대 성공 응답
    2. 커스텀 성공 응답 - 추가
     */
    public static <Void> ApiResult<Void> onSuccess() {
        return new ApiResult<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), null);
    }

    public static <T> ApiResult<T> onSuccess(T result) {
        return new ApiResult<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), result);
    }

    public static <T> ApiResult<T> onSuccess(BaseCode code, T result) {
        return new ApiResult<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), result);
    }

    /*
    실패한 경우 응답 생성
    1. ExceptionAdvice에서 사용하는 에러 응답
    2. 커스텀 에러 응답
     */
    public static <T> ApiResult<T> onFailure(String code, String message, T data) {
        return new ApiResult<>(false, code, message, data);
    }

    public static <T> ApiResult<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResult<>(false, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), result);
    }

}
