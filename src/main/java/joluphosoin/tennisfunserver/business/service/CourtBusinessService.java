package joluphosoin.tennisfunserver.business.service;

import joluphosoin.tennisfunserver.business.data.dto.*;
import joluphosoin.tennisfunserver.business.data.entity.Court;
import joluphosoin.tennisfunserver.business.data.entity.DayTimeSlot;
import joluphosoin.tennisfunserver.business.repository.BusinessRepository;
import joluphosoin.tennisfunserver.business.repository.CourtRepository;
import joluphosoin.tennisfunserver.business.repository.DayTimeSlotRepository;
import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CourtBusinessService {

    private final BusinessRepository businessRepository;
    private final CourtRepository courtRepository;
    private final DayTimeSlotRepository dayTimeSlotRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    @Transactional
    public CourtResDto registerCourt(CourtReqDto courtReqDto) {

        businessRepository.findById(courtReqDto.getOwnerId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.BUSINESS_NOT_FOUND));

        if(courtRepository.findByOwnerIdAndCourtName(courtReqDto.getOwnerId(), courtReqDto.getCourtName()).isPresent()){
            throw new GeneralException(ErrorStatus.COURT_NOT_UNIQUE);
        }

        Court court = courtReqDto.toEntity();

        courtRepository.save(court);

        saveTimeSlot(court);

        return CourtResDto.toDTO(court);
    }

    private void saveTimeSlot(Court court){

        List<CourtHoursDto> businessHours = court.getBusinessHours();
        final String ZONE_ID_ASIA_SEOUL = "Asia/Seoul";
        Date now = new Date();
        Date endDate = new Date(now.getTime() + TimeUnit.DAYS.toMillis(7));

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

                List<TimeSlotDto> timeSlots = new ArrayList<>();

                while (openDateTime.before(closeDateTime)) {
                    timeSlots.add(TimeSlotDto.toDto(openDateTime));
                    openDateTime = new Date(openDateTime.getTime() + TimeUnit.MINUTES.toMillis(30));
                }
                DayTimeSlot dayTimeSlot = DayTimeSlot.toEntity(court, date, timeSlots);
                dayTimeSlotRepository.save(dayTimeSlot);
            }

            date = new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
        }
    }


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

            List<TimeSlotDto> timeSlots = dayTimeSlot.getTimeSlots();

            timeSlots.forEach(timeSlot ->{
                if (timeSlot.getStatus() == DayTimeSlot.ReservationStatus.NOT_OPEN) {
                    timeSlot.setStatus(DayTimeSlot.ReservationStatus.BEFORE);
                }
            });

            dayTimeSlot.setTimeSlots(timeSlots);
            dayTimeSlotRepository.save(dayTimeSlot);
        });
    }

    public CourtResDto registerCourtTime(String courtId) {

        Court court = courtRepository.findById(courtId).orElseThrow();

        saveTimeSlot(court);

        return CourtResDto.toDTO(court);
    }

    public List<SimpleCourtResDto> getReservationCourts(String businessId) {
        return getReservationCourtsByStatus(businessId, Game.GameStatus.INPLAY);
    }

    private List<SimpleCourtResDto> getReservationCourtsByStatus(String businessId, Game.GameStatus status) {
        List<Court> courts = courtRepository.findAllByOwnerId(businessId).orElseThrow(() -> new GeneralException(ErrorStatus.COURT_NOT_FOUND));

        List<SimpleCourtResDto> simpleCourtResDtos = new ArrayList<>();


        courts.forEach(court -> {

            List<Game> preGames = gameRepository.findByCourtIdAndGameStatus(court.getId(), status);
            preGames.forEach(game -> {
                List<String> playerIds = game.getPlayerIds();
                List<String> userNames = new ArrayList<>();
                playerIds.forEach(playerId->{
                    User user = userRepository.findById(playerId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
                    userNames.add(user.getName());
                });
                simpleCourtResDtos.add(SimpleCourtResDto.toDto(court.getCourtName(),SimpleCustomerDto.toDto(game,userNames)));
            });
        });
        return simpleCourtResDtos;
    }

    public List<SimpleCourtResDto> getPendingReservationCourts(String businessId) {
        return getReservationCourtsByStatus(businessId, Game.GameStatus.PREGAME);
    }

}
