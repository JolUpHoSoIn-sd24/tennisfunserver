package joluphosoin.tennisfunserver.payment.repository;

import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentInfoRepository extends MongoRepository<PaymentInfo,String> {
}
