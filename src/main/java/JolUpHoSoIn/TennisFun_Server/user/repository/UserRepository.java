package JolUpHoSoIn.TennisFun_Server.user.repository;

import JolUpHoSoIn.TennisFun_Server.user.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByEmailId(String emailId);
    Optional<User> findByEmailVerificationToken(String token);
}
