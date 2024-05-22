package joluphosoin.tennisfunserver.user.data.dto;

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
    private String id;

    @Indexed
    private String name;

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
