package joluphosoin.tennisfunserver.game.data.dto;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class GameDetailsDto {
    private String gameId;
    private String state;
    @Setter
    private List<PlayerDetail> players;
    private CourtDetail court;
    private Date startTime;
    private Date endTime;
    private String chatRoomId;
    private Double rentalCost;
    private Map<String, Boolean> paymentStatus;
    @Getter
    @Setter
    public static class PlayerDetail {
        private String userId;
        private String name;
        private double ntrp;
        private int age;
        private String gender;
        private boolean isFeedback;
    }

    @Getter
    @Setter
    public static class CourtDetail {
        private String courtId;
        private String name;
        private String location;
        private String surfaceType;
    }

    public static GameDetailsDto toDto(Game game,CourtDetail courtDetail){
        GameDetailsDtoBuilder gameDetailsDtoBuilder = GameDetailsDto.builder()
                .gameId(game.getGameId())
                .state(game.getGameStatus().name())
                .court(courtDetail)
                .startTime(game.getMatchDetails().getStartTime())
                .endTime(game.getMatchDetails().getEndTime())
                .paymentStatus(game.getPaymentStatus());
        if (game.getChatRoomId() != null) {
            gameDetailsDtoBuilder.chatRoomId(game.getChatRoomId());
        }
        if (game.getRentalCost() != null) {
            gameDetailsDtoBuilder.rentalCost(game.getRentalCost());
        }
        return gameDetailsDtoBuilder.build();
    }
    public static GameDetailsDto toDto(PostGame game, CourtDetail courtDetail){
        GameDetailsDtoBuilder gameDetailsDtoBuilder = GameDetailsDto.builder()
                .gameId(game.getGameId())
                .state(game.getPostGameStatus().name())
                .court(courtDetail)
                .startTime(game.getMatchDetails().getStartTime())
                .endTime(game.getMatchDetails().getEndTime())
                .paymentStatus(game.getPaymentStatus());
        if (game.getChatRoomId() != null) {
            gameDetailsDtoBuilder.chatRoomId(game.getChatRoomId());
        }
        if (game.getRentalCost() != null) {
            gameDetailsDtoBuilder.rentalCost(game.getRentalCost());
        }
        return gameDetailsDtoBuilder.build();
    }
}
