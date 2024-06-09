package joluphosoin.tennisfunserver.payment.repository;

import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentInfoRepository extends MongoRepository<PaymentInfo,String> {

    Optional<List<PaymentInfo>> findAllByCourtId(String courtId);

    @Query("{ 'game.$id' : ?0 }")
    List<PaymentInfo> findAllByGameId(String gameId);
}
