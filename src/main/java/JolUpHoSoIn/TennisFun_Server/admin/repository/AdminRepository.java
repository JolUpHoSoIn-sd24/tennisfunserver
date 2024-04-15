package JolUpHoSoIn.TennisFun_Server.admin.repository;

import JolUpHoSoIn.TennisFun_Server.admin.data.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin,String> {
}
