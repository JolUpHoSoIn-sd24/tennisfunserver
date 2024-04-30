package joluphosoin.tennisfunserver.payload.exception;

import joluphosoin.tennisfunserver.payload.code.BaseErrorCode;
import joluphosoin.tennisfunserver.payload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseErrorCode code;
    public ErrorReasonDto getErrorReason(){
        return this.code.getReason();
    }
    public ErrorReasonDto getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
