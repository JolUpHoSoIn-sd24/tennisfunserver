package joluphosoin.tennisfunserver.payment.repository;

import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentInfoRepository extends MongoRepository<PaymentInfo,String> {

    Optional<List<PaymentInfo>> findAllByCourtId(String courtId);
}
