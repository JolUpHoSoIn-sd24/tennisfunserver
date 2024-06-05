package joluphosoin.tennisfunserver.game.repository;

import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostGameRepository extends MongoRepository<PostGame,String> {
    Optional<PostGame> findByPlayerIdsContaining(String userId, String opponentId);

}
