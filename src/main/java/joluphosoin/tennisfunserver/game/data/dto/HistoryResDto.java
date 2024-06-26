package joluphosoin.tennisfunserver.game.data.dto;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.game.data.entity.Score;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class HistoryResDto {

    private GameDetailsDto gameDetailsDto;

    private String gameId;

    private String opponentId;

    private String opponentName;

    private String elapsedTime; // "x일 전", "x시간 전", "x분 전" 등의 형식으로 표현

    private String courtName;

    private List<Score> scores;
    public static HistoryResDto toDto (PostGame game, UserResDto opponentResDto,
                                       GameDetailsDto gameDetailsDto){
        return HistoryResDto.builder()
                .gameDetailsDto(gameDetailsDto)
                .gameId(game.getGameId())
                .opponentId(opponentResDto.getId())
                .opponentName(opponentResDto.getName())
                .elapsedTime(calculateElapsedTime(game.getCreationTime()))
                .courtName(gameDetailsDto.getCourt().getName())
                .scores(game.getScores())
                .build();
    }

    private static String calculateElapsedTime(Date startTime) {
        LocalDateTime startDateTime = convertToLocalDateTimeViaInstant(startTime);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startDateTime, now);

        if (duration.toDays() > 0) {
            return duration.toDays() + "일 전";
        } else if (duration.toHours() > 0) {
            return duration.toHours() + "시간 전";
        } else {
            return duration.toMinutes() + "분 전";
        }
    }

    private static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
