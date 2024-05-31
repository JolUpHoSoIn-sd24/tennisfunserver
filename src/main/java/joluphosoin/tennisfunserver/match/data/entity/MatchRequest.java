package joluphosoin.tennisfunserver.match.data.entity;

import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
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

    private Date startTime; // 시작 시간

    private Date endTime; // 종료 시간

    private Boolean isSingles; // 단복식 여부 (Singles, Doubles)

    private MatchObjective objective; // 경기 목적: 경기(빡겜), 경기(즐겜)

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location; // 사용자의 위치 정보

    private Double maxDistance; // 최대 이동 가능 거리

    private List<String> dislikedCourts; // 선호하지 않는 코트 ID 목록

    private Integer minTime;

    private Integer maxTime;

    private Boolean isReserved;

    // 사전예약 관련 정보
    private String reservationCourtId;

    private Date reservationDate; // 예약 날짜

    private Integer rentalCost; // 코트 대여 비용

    private String description; // 한줄 메시지
    
    @Getter
    public enum MatchObjective {
        INTENSE, // 진지하게
        FUN, // 부담없이
    }
    public static MatchRequest toEntity(MatchRequestDto matchRequestDto, User user){

        Point location = new Point(matchRequestDto.getX(), matchRequestDto.getY());
        MatchRequest.MatchRequestBuilder builder = MatchRequest.builder()
                .userId(user.getId())
                .startTime(matchRequestDto.getStartTime())
                .endTime(matchRequestDto.getEndTime())
                .isSingles(matchRequestDto.getIsSingles())
                .objective(matchRequestDto.getObjective())
                .location(location)
                .maxDistance(matchRequestDto.getMaxDistance())
                .dislikedCourts(matchRequestDto.getDislikedCourts())
                .minTime(matchRequestDto.getMinTime())
                .maxTime(matchRequestDto.getMaxTime())
                .description(matchRequestDto.getDescription())
                .isReserved(matchRequestDto.getIsReserved());

        if (Boolean.TRUE.equals(matchRequestDto.getIsReserved())) {
            builder.reservationDate(matchRequestDto.getReservationDate())
                    .rentalCost(matchRequestDto.getRentalCost())
                    .reservationCourtId(matchRequestDto.getReservationCourtId());
        }

        return builder.build();
    }
    public MatchRequest setEntity(MatchRequestDto matchRequestDto){

        Point location = new Point(matchRequestDto.getX(), matchRequestDto.getY());
        this.startTime = matchRequestDto.getStartTime();
        this.endTime = matchRequestDto.getEndTime();
        this.isSingles = matchRequestDto.getIsSingles();
        this.objective = matchRequestDto.getObjective();
        this.maxDistance = matchRequestDto.getMaxDistance();
        this.dislikedCourts = matchRequestDto.getDislikedCourts();
        this.description = matchRequestDto.getDescription();
        this.location = location;
        this.minTime = matchRequestDto.getMinTime();
        this.maxTime = matchRequestDto.getMaxTime();
        if (Boolean.TRUE.equals(matchRequestDto.getIsReserved())) {
            this.reservationCourtId= matchRequestDto.getReservationCourtId();
            this.reservationDate = matchRequestDto.getReservationDate();
            this.rentalCost = matchRequestDto.getRentalCost();
        }
        return this;

    }

}
