package JolUpHoSoIn.TennisFun_Server.business.repository;

import JolUpHoSoIn.TennisFun_Server.business.data.entity.BusinessInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessInfoRepository extends MongoRepository<BusinessInfo,String> {
}
