package JolUpHoSoIn.TennisFun_Server.apiPayload.exception;

import JolUpHoSoIn.TennisFun_Server.apiPayload.code.BaseErrorCode;
import JolUpHoSoIn.TennisFun_Server.apiPayload.code.ErrorReasonDto;
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
