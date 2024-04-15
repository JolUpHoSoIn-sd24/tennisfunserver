package JolUpHoSoIn.TennisFun_Server.chatroom.repository;

import JolUpHoSoIn.TennisFun_Server.chatroom.data.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {
}
