package joluphosoin.tennisfunserver.game.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import joluphosoin.tennisfunserver.game.data.dto.FeedbackDto;
import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Document(collection = "game")
@Getter
@AllArgsConstructor
@Builder
public class Game {

    @MongoId
    private String gameId;

    @Setter
    private GameStatus gameStatus;

    private List<String> playerIds;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date creationTime;

    private String chatRoomId;

    private Double rentalCost;

    private Map<String, Boolean> paymentStatus;

    private MatchResult.MatchDetails matchDetails;

    public enum GameStatus {
        PREGAME,
        INPLAY,
    }

    public static Game toEntity(GameCreationDto gameDto,
                                Map<String, Boolean> paymentStatusMap){
        return Game.builder()
                .playerIds(gameDto.getPlayerIds())
                .chatRoomId(gameDto.getChatRoomId())
                .rentalCost(gameDto.getRentalCost())
                .paymentStatus(paymentStatusMap)
                .gameStatus(GameStatus.PREGAME)
                .creationTime(new Date())
                .matchDetails(gameDto.getMatchDetails())
                .build();
    }

}