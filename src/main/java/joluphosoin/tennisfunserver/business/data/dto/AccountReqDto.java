package joluphosoin.tennisfunserver.business.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AccountReqDto {

    @NotBlank
    private String businessInfoId;

    @NotBlank
    private String bank;

    @NotBlank
    private String accountNumber;

    private List<String> files;
}
