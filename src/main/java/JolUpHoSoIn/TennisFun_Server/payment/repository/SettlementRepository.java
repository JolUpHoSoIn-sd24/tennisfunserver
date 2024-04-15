package JolUpHoSoIn.TennisFun_Server.payment.repository;

import JolUpHoSoIn.TennisFun_Server.payment.data.entity.Settlement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettlementRepository extends MongoRepository<Settlement,String> {
}
