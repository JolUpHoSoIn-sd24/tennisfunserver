package joluphosoin.tennisfunserver.business.repository;

import joluphosoin.tennisfunserver.business.data.entity.Business;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BusinessRepository extends MongoRepository<Business,String> {

    Optional<Business> findByEmailId(String emailId);

    Optional<Business> findByEmailVerificationToken(String emailVerificationToken);
}
