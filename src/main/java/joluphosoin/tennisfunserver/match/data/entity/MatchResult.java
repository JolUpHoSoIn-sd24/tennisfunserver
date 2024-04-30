package joluphosoin.tennisfunserver.match.data.entity;

import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.Map;

@Document(collection = "matchResult")
@Getter
@AllArgsConstructor
@Builder
public class MatchResult {
    @MongoId
    private String matchId;

    @DBRef
    private User user; // 매칭을 요청한 사용자

    @DBRef
    private User opponent; // 상대방 사용자

    private MatchDetails matchDetails; // 매칭 세부 사항
    private String status; // 매칭 상태
    private Map<String, String> feedback; // 사용자별 피드백 정보, 키는 사용자 ID, 값은 "like" 또는 "dislike"


    // 매치 세부 정보 내부 클래스
    public static class MatchDetails {
        private Date date; // 경기 날짜
        private String startTime; // 시작 시간
        private String endTime; // 종료 시간
        private String location; // 경기 장소
        private String courtType; // 코트 타입

    }
}
