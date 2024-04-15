package JolUpHoSoIn.TennisFun_Server.payment.repository;

import JolUpHoSoIn.TennisFun_Server.payment.data.entity.PaymentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentInfoRepository extends MongoRepository<PaymentInfo,String> {
}
