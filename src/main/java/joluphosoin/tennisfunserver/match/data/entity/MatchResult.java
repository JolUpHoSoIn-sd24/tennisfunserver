package joluphosoin.tennisfunserver.match.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "matchResult")
@Getter
@AllArgsConstructor
@Builder
public class MatchResult {
    @MongoId
    private String id;

    private Map<String,String> userMatchRequests=new HashMap<>();

    private MatchDetails matchDetails; // 매칭 세부 사항

    @Setter
    private Map<String, FeedbackStatus> feedback; // 사용자별 피드백 정보, 키는 사용자 ID, 값은 "like" 또는 "dislike" 또는 not

    public enum FeedbackStatus{
        NOTSELECTED, LIKE, DISLIKE
    }

    // 매치 세부 정보 내부 클래스
    public static class MatchDetails {
        private Date startTime; // 시작 시간
        private Date endTime; // 종료 시간
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        private Point location;
        private String courtId;
        private String courtType; // 코트 타입

    }
}
