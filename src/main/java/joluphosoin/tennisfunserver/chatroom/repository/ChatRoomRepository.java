package joluphosoin.tennisfunserver.chatroom.repository;

import joluphosoin.tennisfunserver.chatroom.data.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {
}
