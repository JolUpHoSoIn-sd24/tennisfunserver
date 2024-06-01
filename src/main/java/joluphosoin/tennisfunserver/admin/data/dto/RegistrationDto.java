package joluphosoin.tennisfunserver.admin.data.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RegistrationDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
