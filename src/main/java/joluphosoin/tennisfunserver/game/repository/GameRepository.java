package joluphosoin.tennisfunserver.game.repository;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends MongoRepository<Game,String> {
    @Transactional
    void deleteByPlayerIdsContaining(String playerId);

    List<Game> findByPlayerIdsIn(List<String> playerIds);

    Collection<Game> findByPlayerIdsContaining(String userId);

    @Query("{ 'playerIds': ?0 }")
    Optional<List<Game>> findByUserIdContainingPlayerIds(String userId);

}
