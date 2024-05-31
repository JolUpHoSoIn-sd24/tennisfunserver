package joluphosoin.tennisfunserver.business.data.dto;


import joluphosoin.tennisfunserver.business.data.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

    private Business.RegistrationStatus registrationStatus; // 가입 상태


    public static BusinessInfoResDto toDto(Business business){
        BusinessInfoResDtoBuilder businessInfoResDtoBuilder = BusinessInfoResDto.builder()
                .id(business.getId())
                .businessRegistrationNumber(business.getBusinessRegistrationNumber())
                .registrationStatus(business.getRegistrationStatus());

        if (business.getBank() != null) {
            businessInfoResDtoBuilder.bank(business.getBank());
        }

        if (business.getAccountNumber() != null) {
            businessInfoResDtoBuilder.accountNumber(business.getAccountNumber());
        }

        if (business.getDocuments() != null) {
            businessInfoResDtoBuilder.documents(business.getDocuments());
        }

        return businessInfoResDtoBuilder.build();
    }

}
