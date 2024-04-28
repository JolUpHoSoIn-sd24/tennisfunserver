package JolUpHoSoIn.TennisFun_Server.apiPayload.exception.handler;


import JolUpHoSoIn.TennisFun_Server.apiPayload.code.BaseErrorCode;
import JolUpHoSoIn.TennisFun_Server.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
