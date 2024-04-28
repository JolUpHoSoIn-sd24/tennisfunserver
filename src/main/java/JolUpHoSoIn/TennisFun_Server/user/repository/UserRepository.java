package JolUpHoSoIn.TennisFun_Server.user.repository;

import JolUpHoSoIn.TennisFun_Server.user.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByEmailId(String emailId);
}
