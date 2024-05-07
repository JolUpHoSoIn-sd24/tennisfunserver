package joluphosoin.tennisfunserver.user.repository;

import joluphosoin.tennisfunserver.user.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmailVerificationToken(String token);
    Optional<User> findByEmailId(String email);
}
