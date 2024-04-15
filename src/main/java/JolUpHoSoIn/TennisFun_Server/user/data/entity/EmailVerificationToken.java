package JolUpHoSoIn.TennisFun_Server.user.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "emailVerificationToken")
@Getter
@AllArgsConstructor
@Builder
public class EmailVerificationToken {

    @MongoId
    private String id;

    private String userId; // 관련된 사용자의 ID

    private String token; // 인증 토큰

    private LocalDateTime expiryDate; // 토큰 만료 시간

}
