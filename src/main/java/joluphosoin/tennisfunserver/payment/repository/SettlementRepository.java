package joluphosoin.tennisfunserver.payment.repository;

import joluphosoin.tennisfunserver.payment.data.entity.Settlement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettlementRepository extends MongoRepository<Settlement,String> {
}
