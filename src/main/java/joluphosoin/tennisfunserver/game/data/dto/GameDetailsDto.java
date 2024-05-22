package joluphosoin.tennisfunserver.game.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GameDetailsDto {
    private String gameId;
    private String state;
    private List<PlayerDetail> players;
    private CourtDetail court;
    private Date startTime;
    private Date endTime;
    private String chatRoomId;
    private Double rentalCost;

    @Getter
    @Setter
    public static class PlayerDetail {
        private String userId;
        private String name;
        private double ntrp;
        private int age;
        private String gender;
    }

    @Getter
    @Setter
    public static class CourtDetail {
        private String courtId;
        private String name;
        private String location;
        private String surfaceType;
    }
}
