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

@Document(collection = "user")
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

    private List<String> avoidCourtLocation;

    private double ntrp;

    private Double mannerScore;

    private List<String> clubIds;

    private boolean emailVerified = false; // 이메일이 인증되었는지 여부

    private String emailVerificationToken; // 이메일 인증 토큰 (이 필드는 이메일 인증 로직에만 사용되므로 API 응답에서 제외될 수 있음)

    public User() {

    }

    public void setAvoidCourtLocation(List<String> avoidCourtLocation) {
        this.avoidCourtLocation = avoidCourtLocation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setNtrp(double ntrp) {
        this.ntrp = ntrp;
    }

    public void setMannerScore(Double mannerScore) {
        this.mannerScore = mannerScore;
    }

    public void setClubIds(List<String> clubIds) {
        this.clubIds = clubIds;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }
}
