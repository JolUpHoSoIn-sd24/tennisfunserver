package joluphosoin.tennisfunserver.payment.data.entity;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "paymentInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInfo {

    @Id
    private String id;

    @DBRef
    private Game game; // Reference to the game information

    private String userId; // ID of the user who made the payment
    private String transactionId; // Unique transaction ID (e.g., KakaoPay TID)
    private String paymentMethodType; // Payment method type
    private String itemName; // Item name
    private int quantity; // Quantity
    private Map<String, Integer> amount; // Payment amount details
    private Date createdAt; // When the payment request was made
    private Date approvedAt; // When the payment was approved
    private PaymentStatus status; // Current status of the payment

    public enum PaymentStatus {
        PENDING, // Payment is pending
        APPROVED, // Payment approved
        FAILED, // Payment failed
        CANCELLED // Payment cancelled
    }
}
