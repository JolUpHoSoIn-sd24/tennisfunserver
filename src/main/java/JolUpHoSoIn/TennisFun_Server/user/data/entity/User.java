package JolUpHoSoIn.TennisFun_Server.user.data.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collation = "user")
@Getter
@AllArgsConstructor
@Builder
public class User {

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

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private List<Point> avoidCourtLocation;

    private String ntrp;

    private Double mannerScore;

    private List<String> clubIds;

    private boolean emailVerified = false; // 이메일이 인증되었는지 여부

    private String emailVerificationToken; // 이메일 인증 토큰 (이 필드는 이메일 인증 로직에만 사용되므로 API 응답에서 제외될 수 있음)


}
