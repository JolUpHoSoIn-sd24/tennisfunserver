package JolUpHoSoIn.TennisFun_Server.user.repository;

import JolUpHoSoIn.TennisFun_Server.user.data.entity.EmailVerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailVerificationTokenRepository extends MongoRepository<EmailVerificationToken,String> {
}
