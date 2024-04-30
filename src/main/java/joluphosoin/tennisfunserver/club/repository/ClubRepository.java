package joluphosoin.tennisfunserver.club.repository;

import joluphosoin.tennisfunserver.club.data.entity.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepository extends MongoRepository<Club,String> {
}
