package joluphosoin.tennisfunserver.match.repository;

import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchResultRepository extends MongoRepository<MatchResult,String> {
}
