package JolUpHoSoIn.TennisFun_Server.chatroom.repository;

import JolUpHoSoIn.TennisFun_Server.chatroom.data.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
}
