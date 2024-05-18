package joluphosoin.tennisfunserver.business.service;

import joluphosoin.tennisfunserver.business.data.dto.AutoMatchApplyDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtHoursDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtReqDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtResDto;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import joluphosoin.tennisfunserver.business.repository.BusinessInfoRepository;
import joluphosoin.tennisfunserver.business.repository.CourtRepository;
import joluphosoin.tennisfunserver.business.repository.DayTimeSlotRepository;
import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CourtBusinessService {

    private final BusinessInfoRepository businessInfoRepository;
    private final CourtRepository courtRepository;
    private final DayTimeSlotRepository dayTimeSlotRepository;

    @Transactional
    public CourtResDto registerCourt(CourtReqDto courtReqDto) {

        businessInfoRepository.findById(courtReqDto.getOwnerId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.BUSINESS_NOT_FOUND));

        if(courtRepository.findByOwnerIdAndCourtName(courtReqDto.getOwnerId(), courtReqDto.getCourtName()).isPresent()){
            throw new GeneralException(ErrorStatus.COURT_NOT_UNIQUE);
        }

        Court court = courtReqDto.toEntity();

        courtRepository.save(court);

        saveTimeSlot(court);

        return CourtResDto.toDTO(court);
    }

    private void saveTimeSlot(Court court) {

        List<CourtHoursDto> businessHours = court.getBusinessHours();
        final String ZONE_ID_ASIA_SEOUL = "Asia/Seoul";
        Date now = new Date();
        Date endDate = new Date(now.getTime() + TimeUnit.DAYS.toMillis(60));

        Map<DayOfWeek, CourtHoursDto> hoursMap = new EnumMap<>(DayOfWeek.class);

        businessHours.forEach(hours -> hoursMap.put(hours.getDayOfWeek(), hours));

        Date date = now;

        while(date.before(endDate)) {

            LocalDate localDate = Instant.ofEpochMilli(date.getTime())
                    .atZone(ZoneId.of(ZONE_ID_ASIA_SEOUL))
                    .toLocalDate();

            DayOfWeek dayOfWeek = localDate.getDayOfWeek();

            if (hoursMap.containsKey(dayOfWeek)) {
                CourtHoursDto hours = hoursMap.get(dayOfWeek);
                Date openDateTime = Date.from(
                        LocalDateTime.of(localDate, LocalTime.parse(hours.getOpenTime()))
                                .atZone(ZoneId.of(ZONE_ID_ASIA_SEOUL))
                                .toInstant());

                Date closeDate = date;
                if (LocalTime.parse(hours.getCloseTime()).isBefore(LocalTime.parse(hours.getOpenTime()))) {
                    closeDate = new Date(closeDate.getTime() + TimeUnit.DAYS.toMillis(1));
                }

                Date closeDateTime = Date.from(
                        LocalDateTime.of(
                                        Instant.ofEpochMilli(closeDate.getTime())
                                                .atZone(ZoneId.of(ZONE_ID_ASIA_SEOUL))
                                                .toLocalDate(),
                                        LocalTime.parse(hours.getCloseTime()))
                                .atZone(ZoneId.of(ZONE_ID_ASIA_SEOUL))
                                .toInstant());

                Map<String, DayTimeSlot.ReservationStatus> timeSlots = new HashMap<>();

                while (openDateTime.before(closeDateTime)) {
                    timeSlots.put(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(openDateTime), DayTimeSlot.ReservationStatus.NOT_OPEN);
                    openDateTime = new Date(openDateTime.getTime() + TimeUnit.MINUTES.toMillis(30));
                }
                DayTimeSlot dayTimeSlot = DayTimeSlot.toEntity(court, date, timeSlots);
                dayTimeSlotRepository.save(dayTimeSlot);
            }

            date = new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
        }
    }

//    @Transactional
//    public CourtResDto registerCourtTimeSlot(CourtTimeSlotReqDto courtTimeSlotReqDto) {
//
//        Court court = courtRepository.findById(courtTimeSlotReqDto.getCourtId())
//                .orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));
//
//        List<TimeSlotDto> timeSlotDtos = courtTimeSlotReqDto.getTimeSlots();
//        List<TimeSlot> timeSlots = new ArrayList<>();
//        timeSlotDtos.forEach(timeSlotDto -> {
//            TimeSlot timeSlot = TimeSlotDto.toEntity(timeSlotDto,court);
//            timeSlots.add(timeSlot);
//            timeSlotRepository.save(timeSlot);
//        });
//
//        return CourtResDto.toDTO(court);
//
//    }

    public GameDetailsDto.CourtDetail getCourtDetails(String courtId) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));

        GameDetailsDto.CourtDetail courtDetail = new GameDetailsDto.CourtDetail();
        courtDetail.setCourtId(court.getId());
        courtDetail.setName(court.getCourtName());
        courtDetail.setLocation(court.getLocation().toString());
        courtDetail.setSurfaceType(court.getCourtType().name());

        return courtDetail;
    }

    @Transactional
    public void applyAutoMatching(AutoMatchApplyDto autoMatchApplyDto) {

        List<String> dates = autoMatchApplyDto.getDates();

        dates.forEach(date->{
            DayTimeSlot dayTimeSlot = dayTimeSlotRepository.findByCourtIdAndDate(autoMatchApplyDto.getCourtId(),date)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.TIMESLOT_NOT_FOUND));

            Map<String, DayTimeSlot.ReservationStatus> timeSlots = dayTimeSlot.getTimeSlots();

            timeSlots.forEach((key, value) -> {
                if (value == DayTimeSlot.ReservationStatus.NOT_OPEN) {
                    timeSlots.put(key, DayTimeSlot.ReservationStatus.BEFORE);
                }

            });
            dayTimeSlot.setTimeSlots(timeSlots);
            dayTimeSlotRepository.save(dayTimeSlot);
        });
    }

//    public SimpleCourtResDto getReservationCourts(String courtId) {
//
//        List<TimeSlot> timeSlots = timeSlotRepository.findAllByCourtIdAndStatus(courtId, TimeSlot.ReservationStatus.CONFIRMED)
//                .orElseThrow(()->new GeneralException(ErrorStatus.TIMESLOT_NOT_FOUND));
//
//        return SimpleCourtResDto.toDto(courtId, timeSlots);
//    }
//
//    public SimpleCourtResDto getPendingReservationCourts(String courtId) {
//
//        List<TimeSlot> timeSlots = timeSlotRepository.findAllByCourtIdAndStatus(courtId, TimeSlot.ReservationStatus.PENDING)
//                .orElseThrow(()->new GeneralException(ErrorStatus.TIMESLOT_NOT_FOUND));
//
//        return SimpleCourtResDto.toDto(courtId, timeSlots);
//    }
//
//    @Transactional
//    public String cancelReservationCourts(SimpleTimeSlotDto timeSlotDto) {
//        List<String> timeSlotIds = timeSlotDto.getTimeSlotIds();
//
//        timeSlotIds.forEach(timeSlotId->{
//            TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
//                    .orElseThrow(()->new GeneralException(ErrorStatus.TIMESLOT_NOT_FOUND));
//
//            if(timeSlot.getStatus()== TimeSlot.ReservationStatus.CONFIRMED){
//                timeSlot.setStatus(TimeSlot.ReservationStatus.NOT_OPEN);
//                timeSlotRepository.save(timeSlot);
//            }
//        });
//        return null;
//    }
//
//    public String cancelPendingReservationCourts(String courtId, String timeSlotId) {
//        return null;
//    }
//
//    public String blockReservationCourts(SimpleTimeSlotDto timeSlotDto) {
//        return null;
//    }
}