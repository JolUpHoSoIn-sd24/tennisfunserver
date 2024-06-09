package joluphosoin.tennisfunserver.game.data.dto;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class HistoryResDto {

    private PostGame game;

    private String gameId;

    private String opponentId;

    private String opponentName;

    private String elapsedTime; // "x일 전", "x시간 전", "x분 전" 등의 형식으로 표현

    public static HistoryResDto toDto (PostGame game, UserResDto opponentResDto){
        return HistoryResDto.builder()
                .game(game)
                .gameId(game.getGameId())
                .opponentId(opponentResDto.getId())
                .opponentName(opponentResDto.getName())
                .elapsedTime(calculateElapsedTime(game.getCreationTime()))
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
