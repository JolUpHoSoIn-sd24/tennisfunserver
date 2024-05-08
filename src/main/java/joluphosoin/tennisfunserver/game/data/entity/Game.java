package joluphosoin.tennisfunserver.game.data.entity;

import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "game")
@Getter
@AllArgsConstructor
@Builder
public class Game {

    @MongoId
    private String gameId;

    private GameStatus gameStatus; // 게임의 진행 상태

    @DBRef
    private List<User> players; // 참여 플레이어 정보, User 문서 참조

    @DBRef
    private Court court; // 테니스장 정보, TennisCourt 문서 참조

    private Date dateTime; // 경기 날짜 및 시간

    private String chatRoomId; // 채팅방 ID

    private Double rentalCost; // 코트 대여 비용

    // 스코어 정보
    private List<Score> scores; // 사용자별 스코어

    private boolean scoreConfirmed; // 스코어가 모두에 의해 합의되었는지 여부

    // NTRP 및 매너 평가
    private List<NTRPFeedback> ntrpFeedbacks;
    private List<MannerFeedback> mannerFeedbacks;


    public enum GameStatus {
        PREGAME, INPLAY, POSTGAME
    }

    public static class Score {
        private String userId; // 스코어를 등록한 사용자 ID
        private Map<String, ScoreDetail> scoreDetails; // 세트별 스코어

    }

    public static class ScoreDetail {
        private int userScore;
        private int opponentScore;

    }

    public static class NTRPFeedback {
        private String userId; // 피드백을 등록한 사용자 ID
        private String opponentUserId; // 평가받는 상대방 사용자 ID
        private double ntrp;
        private String comments;

    }

    public static class MannerFeedback {
        private String userId; // 피드백을 등록한 사용자 ID
        private String opponentUserId; // 평가받는 상대방 사용자 ID
        private int mannerScore;
        private String comments;

    }
}