package joluphosoin.tennisfunserver.club.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "clubMembershipRequest")
@Getter
@AllArgsConstructor
@Builder
public class ClubMembershipRequest {

    @MongoId
    private String requestId; // 요청 ID

    @DBRef
    private Club club; // 요청이 속한 동호회, Club 문서 참조

    private String userId; // 요청한 사용자의 ID

    private Date requestDate; // 요청 일자

    private RequestStatus status; // 요청 상태

    public enum RequestStatus {
        Pending, // 대기 중
        Approved, // 승인됨
        Rejected // 거절됨
    }
}
