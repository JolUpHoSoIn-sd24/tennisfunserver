package joluphosoin.tennisfunserver.business.data.dto;


import joluphosoin.tennisfunserver.business.data.entity.BusinessInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BusinessInfoResDto {

    private String id;

    private String businessRegistrationNumber;

    private List<String> documents;

    private String bank;

    private String accountNumber;

    private BusinessInfo.RegistrationStatus registrationStatus; // 가입 상태


    public static BusinessInfoResDto toDto(BusinessInfo businessInfo){
        BusinessInfoResDtoBuilder businessInfoResDtoBuilder = BusinessInfoResDto.builder()
                .id(businessInfo.getId())
                .businessRegistrationNumber(businessInfo.getBusinessRegistrationNumber())
                .registrationStatus(businessInfo.getRegistrationStatus());

        if (businessInfo.getBank() != null) {
            businessInfoResDtoBuilder.bank(businessInfo.getBank());
        }

        if (businessInfo.getAccountNumber() != null) {
            businessInfoResDtoBuilder.accountNumber(businessInfo.getAccountNumber());
        }

        if (businessInfo.getDocuments() != null) {
            businessInfoResDtoBuilder.documents(businessInfo.getDocuments());
        }

        return businessInfoResDtoBuilder.build();
    }

}
