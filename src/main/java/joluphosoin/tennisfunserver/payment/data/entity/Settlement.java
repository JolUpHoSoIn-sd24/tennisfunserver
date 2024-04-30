package joluphosoin.tennisfunserver.payment.data.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "settlement")
public class Settlement {

    @MongoId
    private String settlementId; // 정산 ID

    private String businessId; // 정산을 요청한 사업자 ID

    private String period; // 정산 기간 (예: "2023-01")

    private Double totalAmount; // 총 금액

    private SettlementStatus status; // 정산 상태


    public enum SettlementStatus {
        Pending, // 대기 중
        Approved, // 승인됨
        Rejected // 거절됨
    }
}
