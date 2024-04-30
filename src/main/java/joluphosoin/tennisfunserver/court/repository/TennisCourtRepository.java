package joluphosoin.tennisfunserver.court.repository;

import joluphosoin.tennisfunserver.court.data.entity.TennisCourt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TennisCourtRepository extends MongoRepository<TennisCourt,String> {
}
