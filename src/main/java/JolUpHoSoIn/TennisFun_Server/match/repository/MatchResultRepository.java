package JolUpHoSoIn.TennisFun_Server.match.repository;

import JolUpHoSoIn.TennisFun_Server.match.data.entity.MatchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchResultRepository extends MongoRepository<MatchResult,String> {
}
