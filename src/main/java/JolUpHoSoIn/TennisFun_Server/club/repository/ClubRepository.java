package JolUpHoSoIn.TennisFun_Server.club.repository;

import JolUpHoSoIn.TennisFun_Server.club.data.entity.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepository extends MongoRepository<Club,String> {
}
