package JolUpHoSoIn.TennisFun_Server.court.repository;

import JolUpHoSoIn.TennisFun_Server.court.data.entity.TennisCourt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TennisCourtRepository extends MongoRepository<TennisCourt,String> {
}
