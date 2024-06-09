package joluphosoin.tennisfunserver.game.repository;

import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostGameRepository extends MongoRepository<PostGame,String> {
    @Query("{ 'playerIds': ?0 }")
    Optional<List<PostGame>> findByUserIdContainingPlayerIds(String userId);

    @Query("{ 'matchDetails.courtId': ?0 }")
    Optional<List<PostGame>> findAllByCourtId(String courtId);
}
