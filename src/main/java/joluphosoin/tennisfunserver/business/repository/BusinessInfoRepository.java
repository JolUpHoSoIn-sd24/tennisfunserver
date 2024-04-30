package joluphosoin.tennisfunserver.business.repository;

import joluphosoin.tennisfunserver.business.data.entity.BusinessInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessInfoRepository extends MongoRepository<BusinessInfo,String> {
}
