package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SimpleCustomerDto {

    private List<String> userName;

    private Date reservationDate;

    public static SimpleCustomerDto toDto(Game game,List<String> userName){
        return SimpleCustomerDto.builder()
                .userName(userName)
                .reservationDate(game.getMatchDetails().getStartTime())
                .build();
    }

}
