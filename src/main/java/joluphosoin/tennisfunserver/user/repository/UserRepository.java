package joluphosoin.tennisfunserver.user.repository;

import joluphosoin.tennisfunserver.user.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmailVerificationToken(String token);
    Optional<User> findByEmailId(String email);
    Collection<User> findByIdIn(List<String> playerIds);
}
