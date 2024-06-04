package joluphosoin.tennisfunserver.business.data.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.business.data.dto.BusinessReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "business")
@Getter
@AllArgsConstructor
@Builder
public class Business {

    @MongoId
    private String id;

    @Indexed
    private String name;

    @Indexed(unique = true)
    private String emailId;

    private String password;

    private LocalDate birthDate;

    @Setter
    private boolean emailVerified;

    private String emailVerificationToken;

    private String businessRegistrationNumber; // 사업자 등록 번호

    private List<String> documentUrls= new ArrayList<>(); // 관련 서류 목록

    @Setter
    private String bank;

    @Setter
    private String accountNumber;

    private RegistrationStatus registrationStatus; // 가입 상태

    private String shopName;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    private String address;

    public enum RegistrationStatus {
        PRE_APPROVAL, // 승인 전
        APPROVED // 승인 완료
    }
    public static Business toEntity(BusinessReqDto businessReqDto, PasswordEncoder passwordEncoder){
        BusinessBuilder businessBuilder = Business.builder()
                .name(businessReqDto.getName())
                .emailId(businessReqDto.getEmailId())
                .password(passwordEncoder.encode(businessReqDto.getPassword()))
                .birthDate(businessReqDto.getBirthDate())
                .emailVerified(false)
                .emailVerificationToken(UUID.randomUUID().toString())
                .businessRegistrationNumber(businessReqDto.getBusinessRegistrationNumber())
                .bank(businessReqDto.getBank())
                .accountNumber(businessReqDto.getAccountNumber())
                .registrationStatus(RegistrationStatus.PRE_APPROVAL)
                .shopName(businessReqDto.getShopName())
                .location(businessReqDto.getLocation())
                .address(businessReqDto.getAddress());

        if(!businessReqDto.getDocumentUrls().isEmpty()){
            businessBuilder.documentUrls(businessReqDto.getDocumentUrls());
        }
        return businessBuilder.build();
    }

    public static Business setEntity(Business existingBusiness,BusinessReqDto businessReqDto,PasswordEncoder passwordEncoder){
        BusinessBuilder businessBuilder = Business.builder()
                .id(existingBusiness.getId())
                .name(businessReqDto.getName())
                .emailId(businessReqDto.getEmailId())
                .password(passwordEncoder.encode(businessReqDto.getPassword()))
                .birthDate(businessReqDto.getBirthDate())
                .emailVerified(existingBusiness.isEmailVerified())
                .emailVerificationToken(existingBusiness.getEmailVerificationToken())
                .businessRegistrationNumber(businessReqDto.getBusinessRegistrationNumber())
                .bank(businessReqDto.getBank())
                .accountNumber(businessReqDto.getAccountNumber())
                .registrationStatus(existingBusiness.getRegistrationStatus())
                .shopName(businessReqDto.getShopName())
                .location(businessReqDto.getLocation());


        if(!businessReqDto.getDocumentUrls().isEmpty()){
            if(existingBusiness.getDocumentUrls().isEmpty()){
                businessBuilder.documentUrls(businessReqDto.getDocumentUrls());
            }
            else{
                List<String> reqDtoDocumentUrls = businessReqDto.getDocumentUrls();
                List<String> existingBusinessDocumentUrls = existingBusiness.getDocumentUrls();
                existingBusinessDocumentUrls.addAll(reqDtoDocumentUrls);
            }
        }
        else{
            businessBuilder.documentUrls(existingBusiness.getDocumentUrls());
        }
        return businessBuilder.build();
    }
}