package joluphosoin.tennisfunserver.business.repository;

import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DayTimeSlotRepository extends MongoRepository<DayTimeSlot,String> {

    Optional<DayTimeSlot> findByCourtIdAndDate(String courtId, String date);

    Boolean existsByCourtIdAndDate(String courtId, String date);

    @Query("{ 'courtId': ?0, 'timeSlots.status': ?1 }")
    Optional<List<DayTimeSlot>> findAllByCourtIdAndStatus(String courtId, DayTimeSlot.ReservationStatus reservationStatus);
}
