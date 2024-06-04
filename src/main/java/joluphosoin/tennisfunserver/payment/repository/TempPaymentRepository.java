package joluphosoin.tennisfunserver.payment.repository;

import joluphosoin.tennisfunserver.payment.data.entity.TempPayment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TempPaymentRepository extends MongoRepository<TempPayment,String> {

    Optional<TempPayment> findByUserId(String userId);
}
