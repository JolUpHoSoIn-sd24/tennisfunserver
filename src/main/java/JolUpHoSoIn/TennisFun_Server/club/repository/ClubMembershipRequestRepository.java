package JolUpHoSoIn.TennisFun_Server.club.repository;

import JolUpHoSoIn.TennisFun_Server.club.data.entity.ClubMembershipRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubMembershipRequestRepository extends MongoRepository<ClubMembershipRequest,String> {
}
