package joluphosoin.tennisfunserver.match.repository;

import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
public interface MatchResultRepository extends MongoRepository<MatchResult,String> {

    Optional<List<MatchResult>> findAllByUserMatchRequestsContains(String matchRequestId);

}
