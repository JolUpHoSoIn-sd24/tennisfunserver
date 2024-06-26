package joluphosoin.tennisfunserver.business.repository;

import joluphosoin.tennisfunserver.business.data.entity.Court;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CourtRepository extends MongoRepository<Court,String> {
    Optional<Court> findByOwnerIdAndCourtName(String ownerId, String courtName);

    Optional<List<Court>> findAllByOwnerId(String ownerId);

    Optional<List<Court>> findAllByCourtNameContaining(String courtName);
}
