package joluphosoin.tennisfunserver.business.repository;

import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DayTimeSlotRepository extends MongoRepository<DayTimeSlot,String> {

    Optional<DayTimeSlot> findByCourtIdAndDate(String courtId, String date);

}
