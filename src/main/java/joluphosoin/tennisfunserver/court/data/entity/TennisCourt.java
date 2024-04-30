package joluphosoin.tennisfunserver.court.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "tennisCourt")
@Getter
@AllArgsConstructor
@Builder
public class TennisCourt {

    @MongoId
    private String id;

    private String address; // 한글 주소

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location; // 테니스장 위치 (위도와 경도)

    private String description; // 소개 정보
    private String ownerId; // 테니스장을 소유한 사업자의 사용자 ID
    private String type; // 코트 타입 (예: hard, clay, grass)
    private String courtName; // 코트 이름
    private List<AvailableTimeSlot> availableTimeSlots; // 이용 가능 시간 및 단위시간당 비용

    public static class AvailableTimeSlot {
        private String startTime; // 시작 시간
        private String endTime; // 종료 시간
        private Double rentalCostPerHour; // 단위시간당 대여 비용
    }
}