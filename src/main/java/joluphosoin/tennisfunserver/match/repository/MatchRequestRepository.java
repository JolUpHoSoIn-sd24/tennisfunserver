package joluphosoin.tennisfunserver.match.repository;

import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRequestRepository extends MongoRepository<MatchRequest,String> {
}
