package joluphosoin.tennisfunserver.game.repository;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface GameRepository extends MongoRepository<Game,String> {
    @Transactional
    void deleteByPlayerIdsContaining(String playerId);

    List<Game> findByPlayerIdsIn(List<String> playerIds);
}
