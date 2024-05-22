package joluphosoin.tennisfunserver.match.repository;

import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
public interface MatchResultRepository extends MongoRepository<MatchResult,String> {
    @Query("{ 'userAndMatchRequests.?0': { $exists: true } }")
    Optional<List<MatchResult>> findAllByUserAndMatchRequests(String matchRequestId);

}
