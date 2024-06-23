package joluphosoin.tennisfunserver.user.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegistrationDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Setter
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    // You can add regex for password constraints and additional messages if you like
    private String password;

    @NotNull(message = "NTRP rating is required")
    @DecimalMin(value = "1.0", message = "NTRP rating must be at least 1.0")
    @DecimalMax(value = "7.0", message = "NTRP rating must be at most 7.0")
    private double ntrp;

    @NotNull(message = "Age is required")
    @Schema(example = "1999-04-17") 
    private LocalDate birthDate;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "MALE|FEMALE", message = "Gender must be either MALE or FEMALE")
    private String gender;


}