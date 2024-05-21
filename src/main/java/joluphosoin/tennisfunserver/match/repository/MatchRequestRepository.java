package joluphosoin.tennisfunserver.match.repository;

import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MatchRequestRepository extends MongoRepository<MatchRequest,String> {
    Optional<MatchRequest> findByUserId(String userId);
}
