package joluphosoin.tennisfunserver.payment.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerificationRequestDto {
    @Schema(example = "6575ce79f790946987df0cd4")
    private String tid;
    @Schema(example = "2058e18857482ea0b081")
    private String pgToken;
}
