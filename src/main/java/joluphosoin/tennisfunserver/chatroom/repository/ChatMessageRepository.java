package joluphosoin.tennisfunserver.chatroom.repository;

import joluphosoin.tennisfunserver.chatroom.data.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
}
