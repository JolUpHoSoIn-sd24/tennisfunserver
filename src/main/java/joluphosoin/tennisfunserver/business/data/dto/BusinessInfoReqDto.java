package joluphosoin.tennisfunserver.business.data.dto;

import jakarta.validation.constraints.NotBlank;
import joluphosoin.tennisfunserver.business.data.entity.BusinessInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BusinessInfoReqDto {
    @NotBlank
    private String businessRegistrationNumber;

    private List<String> documents;

    public BusinessInfo toEntity(){

        return BusinessInfo.builder()
                .businessRegistrationNumber(businessRegistrationNumber)
                .documents(documents)
                .registrationStatus(BusinessInfo.RegistrationStatus.PRE_APPROVAL)
                .build();
    }
}
