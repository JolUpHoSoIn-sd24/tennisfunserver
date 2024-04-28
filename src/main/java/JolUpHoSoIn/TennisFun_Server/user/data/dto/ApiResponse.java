package JolUpHoSoIn.TennisFun_Server.user.data.dto;

import java.util.Map;

public class ApiResponse {
    private boolean isSuccess;
    private String code;
    private String message;
    private Map<String, String> result;

    public ApiResponse(boolean isSuccess, String code, String message, Map<String, String> result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }
}