package joluphosoin.tennisfunserver.business.data.dto;

import jakarta.validation.constraints.NotBlank;
import joluphosoin.tennisfunserver.business.data.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BusinessInfoReqDto {

    @MongoId
    private String id;

    @Indexed
    private String name;

    private String password;

    @Indexed(unique = true)
    private String emailId;

    private Integer age; // 나이 필드 추가

    private String gender; // 성별 필드 추가

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    private Double maxDistance;

    private List<String> dislikedCourts;

    private double ntrp;

    private Double mannerScore;

    private List<String> clubIds;

    private boolean emailVerified;

    private String emailVerificationToken;

    private String webSocketId;


    @NotBlank
    private String businessRegistrationNumber;

    private List<String> documents;

    public Business toEntity(){

        return Business.builder()
                .businessRegistrationNumber(businessRegistrationNumber)
                .documents(documents)
                .registrationStatus(Business.RegistrationStatus.PRE_APPROVAL)
                .build();
    }
}
