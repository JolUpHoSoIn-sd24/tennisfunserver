package joluphosoin.tennisfunserver.payload.exception.handler;


import joluphosoin.tennisfunserver.payload.code.BaseErrorCode;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
