package JolUpHoSoIn.TennisFun_Server.match.repository;

import JolUpHoSoIn.TennisFun_Server.match.data.entity.MatchRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRequestRepository extends MongoRepository<MatchRequest,String> {
}
