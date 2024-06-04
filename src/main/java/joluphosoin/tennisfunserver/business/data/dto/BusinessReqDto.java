package joluphosoin.tennisfunserver.business.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BusinessReqDto {

    @NotBlank
    @Email
    @Schema(example = "ssh@seunglab.dev")
    private String emailId;

    @NotBlank
    @Schema(example = "Abc123@")
    private String password;

    @NotBlank
    @Schema(example = "김관주")
    private String name;

    @NotNull(message = "Age is required")
    @Schema(example = "1999-04-17")
    private LocalDate birthDate;

    @NotBlank
    @Schema(example = "123-45-67890")
    private String businessRegistrationNumber;

    @NotBlank
    @Schema(example = "농협은행")
    private String bank;

    @NotBlank
    @Schema(example = "302-0663-2837-11")
    private String accountNumber;

    private List<String> documentUrls;

    @NotBlank
    private String shopName;

    @NotNull
    private Point location;

    @Schema(example = "경기도 수원시 장안구 만석로 65")
    private String address;

}
