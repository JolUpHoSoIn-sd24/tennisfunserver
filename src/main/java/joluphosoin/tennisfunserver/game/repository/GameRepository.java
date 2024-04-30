package joluphosoin.tennisfunserver.game.repository;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game,String> {
}
