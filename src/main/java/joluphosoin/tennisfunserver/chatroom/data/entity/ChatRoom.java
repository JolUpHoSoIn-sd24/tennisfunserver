package joluphosoin.tennisfunserver.chatroom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "chatRoom")
@Getter
@AllArgsConstructor
@Builder
public class ChatRoom {

    @MongoId
    private String chatRoomId; // 채팅방 ID

    private List<String> participants; // 참가자들의 사용자 ID 목록

}