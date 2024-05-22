package joluphosoin.tennisfunserver.user.data.entity;


import lombok.*;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    private List<String> dislikedCourts;

    private double ntrp;

    private Double mannerScore;

    private List<String> clubIds;

    private boolean emailVerified;

    private String emailVerificationToken;

    private String webSocketId;

}
