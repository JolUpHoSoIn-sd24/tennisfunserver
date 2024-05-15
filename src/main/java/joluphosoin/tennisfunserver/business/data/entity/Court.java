package joluphosoin.tennisfunserver.business.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "court")
@Getter
@AllArgsConstructor
@Builder
public class Court {

    @MongoId
    private String id;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location; // 테니스장 위치 (위도와 경도)

    private String description; // 소개 정보

    private String ownerId; // 테니스장을 소유한 사업자의 사용자 ID

    private CourtType courtType; // 코트 타입 (예: hard, clay, grass)

    private String courtName; // 코트 이름

    private String openTime;

    private String closeTime;


    public enum CourtType{
        HARD, CLAY, GRASS,
    }

}