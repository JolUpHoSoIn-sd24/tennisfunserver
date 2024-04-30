package joluphosoin.tennisfunserver.payment.data.entity;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "paymentInfo")
@Getter
@AllArgsConstructor
@Builder
public class PaymentInfo {

    @MongoId
    private String id;

    @DBRef
    private Game game; // 참조하는 게임 정보, Game 문서 참조

    private String tid; // 카카오페이 거래 ID
    private String nextRedirectPcUrl; // 결제 페이지 URL
    private Date createdAt; // 결제 요청 시간
    private Double amount; // 결제 금액
    private PaymentStatus status; // 결제 상태

    public enum PaymentStatus {
        Pending, // 결제 대기 중
        Approved, // 결제 승인됨
        Cancelled // 결제 취소됨
    }
}