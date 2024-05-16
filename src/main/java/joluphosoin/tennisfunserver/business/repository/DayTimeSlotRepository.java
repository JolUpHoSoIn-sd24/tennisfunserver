package joluphosoin.tennisfunserver.business.repository;

import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DayTimeSlotRepository extends MongoRepository<DayTimeSlot,String> {

    Optional<List<DayTimeSlot>> findAllByCourtIdAndDate(String courtId, String date);

}
