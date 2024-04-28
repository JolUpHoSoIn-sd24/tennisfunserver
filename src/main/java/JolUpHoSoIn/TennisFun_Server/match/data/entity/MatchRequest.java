package JolUpHoSoIn.TennisFun_Server.match.data.entity;

import JolUpHoSoIn.TennisFun_Server.match.data.dto.MatchRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
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

    private String startTime; // 시작 시간

    private String endTime; // 종료 시간

    private Boolean isSingles; // 단복식 여부 (Singles, Doubles)

    private MatchObjective objective; // 경기 목적: 경기(빡겜), 경기(즐겜), 경기(상관없음), 랠리, 서브연습

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location; // 사용자의 위치 정보

    private Double maxDistance; // 최대 이동 가능 거리

    private List<String> dislikedCourts; // 선호하지 않는 코트 ID 목록

    // 사전예약 관련 정보
    private Date reservationDate; // 예약 날짜

    private Integer rentalCost; // 코트 대여 비용
    
    private String description; // 한줄 메시지
    
    @Getter
    public enum MatchObjective {
        INTENSE,
        FUN,
        ANY,
        RALLY,
        SERVE;
    }

    public MatchRequest setEntity(MatchRequestDto matchRequestDto){

        this.startTime = matchRequestDto.getStartTime();
        this.endTime = matchRequestDto.getEndTime();
        this.isSingles = matchRequestDto.getIsSingles();
        this.objective = matchRequestDto.getObjective();
        this.location = new Point(matchRequestDto.getLongitude(), matchRequestDto.getLatitude());
        this.maxDistance = matchRequestDto.getMaxDistance();
        this.dislikedCourts = matchRequestDto.getDislikedCourts();
        this.description = matchRequestDto.getDescription();
        if (reservationDate != null && rentalCost != null) {
            this.reservationDate = matchRequestDto.getReservationDate();
            this.rentalCost = matchRequestDto.getRentalCost();
        }
        return this;

    }

}
