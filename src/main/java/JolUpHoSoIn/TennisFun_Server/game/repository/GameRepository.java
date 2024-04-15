package JolUpHoSoIn.TennisFun_Server.game.repository;

import JolUpHoSoIn.TennisFun_Server.game.data.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game,String> {
}
