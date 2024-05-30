package joluphosoin.tennisfunserver.user.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
@Builder
@Getter
@AllArgsConstructor
public class UserResDto {
    @MongoId
    @Schema(example ="6646cd0991733e1b2d1e8b53")
    private String id;

    @Indexed
    @Schema(example ="이은정")
    private String name;

    @Indexed(unique = true)
    @Schema(example ="4@seunglab.dev")
    private String emailId;

    @Schema(example ="82")
    private Integer age; // 나이 필드 추가

    @Schema(example ="FEMALE")
    private String gender; // 성별 필드 추가

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    @Schema(example ="{ \"x\": 127.07134, \"y\": 37.251521\n}")
    private Point location;

    @Schema(example ="3.0")
    private Double maxDistance;

    @Schema(example ="[\"664704a7cb787760b7c0220f\"] ")
    private List<String> dislikedCourts;

    @Schema(example ="4.5")
    private double ntrp;

    @Schema(example ="36.5")
    private Double mannerScore;

    @Schema(example ="[ 6646cd0991733e1b2d1e8b53 ]")
    private List<String> clubIds;

    @Schema(example ="true")
    private boolean emailVerified;

    public static UserResDto toDto(User user){
        return UserResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .emailId(user.getEmailId())
                .age(user.getAge())
                .gender(user.getGender())
                .location(user.getLocation())
                .maxDistance(user.getMaxDistance())
                .dislikedCourts(user.getDislikedCourts())
                .ntrp(user.getNtrp())
                .mannerScore(user.getMannerScore())
                .clubIds(user.getClubIds())
                .emailVerified(user.isEmailVerified())
                .build();
    }

}
