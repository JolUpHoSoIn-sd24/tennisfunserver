package joluphosoin.tennisfunserver.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private boolean isSuccess;
    private String code;
    private String message;
    private Map<String, Object> result;
}