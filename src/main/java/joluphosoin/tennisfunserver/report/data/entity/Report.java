package joluphosoin.tennisfunserver.report.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "report")
@Getter
@AllArgsConstructor
@Builder
public class Report {

    @MongoId
    private String reportId; // 신고 ID

    private String reporterId; // 신고한 사용자 ID

    private String reportedUserId; // 신고된 사용자 ID

    private String category; // 신고 카테고리

    private String description; // 신고 설명

    private String gameId; // 관련 게임 ID (선택적)

    private Date reportDate; // 신고 날짜

    private ReportStatus status; // 신고 처리 상태


    public enum ReportStatus {
        Pending, // 처리 중
        Resolved // 처리 완료
    }
}