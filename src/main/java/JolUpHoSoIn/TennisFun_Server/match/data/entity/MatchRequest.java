package JolUpHoSoIn.TennisFun_Server.match.data.entity;

import JolUpHoSoIn.TennisFun_Server.court.data.entity.TennisCourt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document(collection = "matchRequest")
@Getter
@AllArgsConstructor
@Builder
public class MatchRequest {
    @MongoId
    private String id;

    private String userId; // 사용자 ID

    private String availableTime; // 가능한 시간

    private String matchType; // 단복식 여부 (Singles, Doubles)

    private String seriousness; // 진지한 정도

    private String objective; // 경기 목적

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location; // 사용자의 위치 정보

    private Double maxDistance; // 최대 이동 가능 거리

    private List<String> dislikedCourts; // 선호하지 않는 코트 ID 목록

    @DBRef
    private TennisCourt tennisCourt; // 참조하는 테니스 코트 정보

    // 예약 관련 정보
    private Date reservationDate; // 예약 날짜
    private String startTime; // 시작 시간
    private String endTime; // 종료 시간
    private Double rentalCost; // 코트 대여 비용
    private String additionalInfo; // 추가 정보 (선택 사항)

}
