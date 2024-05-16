package joluphosoin.tennisfunserver.match.repository;

import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface MatchResultRepository extends MongoRepository<MatchResult,String> {

    Optional<MatchResult> findByUserMatchRequestsContains(String userId, String matchRequestId);
    Optional<MatchResult> findByUserMatchRequestsContains(String matchRequestId);

}
