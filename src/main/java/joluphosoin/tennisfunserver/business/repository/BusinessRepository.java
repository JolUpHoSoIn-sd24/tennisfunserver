package joluphosoin.tennisfunserver.business.repository;

import joluphosoin.tennisfunserver.business.data.entity.Business;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessRepository extends MongoRepository<Business,String> {
}
