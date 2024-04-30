package joluphosoin.tennisfunserver.club.repository;

import joluphosoin.tennisfunserver.club.data.entity.ClubMembershipRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubMembershipRequestRepository extends MongoRepository<ClubMembershipRequest,String> {
}
