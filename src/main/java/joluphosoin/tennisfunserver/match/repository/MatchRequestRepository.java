package joluphosoin.tennisfunserver.match.repository;

import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface MatchRequestRepository extends MongoRepository<MatchRequest,String> {
    @Query("{ 'userId': ?0 }")
    Optional<MatchRequest> findByUserId(String userId);

    void deleteAllByUserId(String userId);
}
