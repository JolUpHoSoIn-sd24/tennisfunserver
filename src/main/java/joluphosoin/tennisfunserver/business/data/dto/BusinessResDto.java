package joluphosoin.tennisfunserver.business.data.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import joluphosoin.tennisfunserver.business.data.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BusinessResDto {

    @Schema(example = "665988635aa22a5e7b207f28")
    private String id;

    @Schema(example = "ssh@seunglab.dev")
    private String emailId;

    @Schema(example = "123-45-67890")
    private String businessRegistrationNumber;

    @Schema(example = "[\"https://example.com/document1.pdf\",\"https://example.com/document2.pdf\"]")
    private List<String> documentUrls;

    @Schema(example = "농협은행")
    private String bank;

    @Schema(example = "302-0663-2837-11")
    private String accountNumber;

    @Schema(example = "PRE_APPROVAL")
    private Business.RegistrationStatus registrationStatus; // 가입 상태

    @Schema(example = "김관주")
    private String name;

    @Schema(example = "1999-04-17")
    private LocalDate birthDate;

    @Schema(example = "MyShop")
    private String shopName;

    @Schema(example = "{\"x\": 127.07134, \"y\": 37.251521}")
    private Point location;

    @Schema(example = "")
    private String address;

    public static BusinessResDto toDto(Business business){
        BusinessResDtoBuilder businessInfoResDtoBuilder = BusinessResDto.builder()
                .id(business.getId())
                .emailId(business.getEmailId())
                .businessRegistrationNumber(business.getBusinessRegistrationNumber())
                .bank(business.getBank())
                .accountNumber(business.getAccountNumber())
                .registrationStatus(business.getRegistrationStatus())
                .name(business.getName())
                .birthDate(business.getBirthDate())
                .shopName(business.getShopName())
                .location(business.getLocation())
                .address(business.getAddress());

        if (business.getDocumentUrls() != null) {
            businessInfoResDtoBuilder.documentUrls(business.getDocumentUrls());
        }

        return businessInfoResDtoBuilder.build();
    }

}