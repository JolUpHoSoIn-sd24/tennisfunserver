package joluphosoin.tennisfunserver.user.repository;

import joluphosoin.tennisfunserver.user.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByEmailId(String emailId);
    Optional<User> findByEmailVerificationToken(String token);
    Optional<Object> findByEmailId(String email);
}
