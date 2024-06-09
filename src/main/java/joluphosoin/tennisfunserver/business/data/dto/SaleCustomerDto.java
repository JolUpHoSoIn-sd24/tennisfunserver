package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class SaleCustomerDto {

    private String gameId;

    private List<String> userName;

    private Date reservationDate;

    private double totalPrice;

    private Date paymentConfirmDate;

    public static SaleCustomerDto toDto(Game game, List<String> userName, PaymentInfo paymentInfo){
        return SaleCustomerDto.builder()
                .gameId(game.getGameId())
                .userName(userName)
                .reservationDate(game.getMatchDetails().getStartTime())
                .totalPrice(game.getRentalCost())
                .paymentConfirmDate(paymentInfo.getApprovedAt())
                .build();
    }
    public static SaleCustomerDto toDto(PostGame game, List<String> userName, PaymentInfo paymentInfo){
        return SaleCustomerDto.builder()
                .gameId(game.getGameId())
                .userName(userName)
                .reservationDate(game.getMatchDetails().getStartTime())
                .totalPrice(game.getRentalCost())
                .paymentConfirmDate(paymentInfo.getApprovedAt())
                .build();
    }
}
