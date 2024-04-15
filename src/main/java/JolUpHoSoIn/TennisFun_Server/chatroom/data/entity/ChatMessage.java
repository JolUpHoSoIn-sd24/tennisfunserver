package JolUpHoSoIn.TennisFun_Server.chatroom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "chatMessage")
@Getter
@AllArgsConstructor
@Builder
public class ChatMessage {

    @MongoId
    private String id;

    @DBRef
    private ChatRoom chatRoom; // 참조하는 채팅방, ChatRoom 문서 참조

    private String senderId; // 보낸 사람의 사용자 ID

    private String message; // 메시지 내용

    private Date timestamp; // 메시지 전송 시간

}
