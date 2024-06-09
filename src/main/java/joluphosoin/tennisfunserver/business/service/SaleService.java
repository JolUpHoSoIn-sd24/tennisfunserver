package joluphosoin.tennisfunserver.business.service;

import joluphosoin.tennisfunserver.business.data.dto.SaleCustomerDto;
import joluphosoin.tennisfunserver.business.data.dto.SaleResDto;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.game.repository.PostGameRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import joluphosoin.tennisfunserver.payment.repository.PaymentInfoRepository;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final PaymentInfoRepository paymentInfoRepository;
    private final GameRepository gameRepository;
    private final PostGameRepository postGameRepository;
    private final UserRepository userRepository;
    public SaleResDto getSale(String courtId) {

        int sum=0;

        List<SaleCustomerDto> saleCustomerDtos = new ArrayList<>();

        sum+=addSaleInfoByPostGame(courtId, saleCustomerDtos);

        sum+=addSaleInfoByGame(courtId, saleCustomerDtos);

        return SaleResDto.toDto(sum,saleCustomerDtos);
    }

    private int addSaleInfoByPostGame(String courtId, List<SaleCustomerDto> saleCustomerDtos) {
        List<PostGame> postGames = postGameRepository.findAllByCourtId(courtId).orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NOT_FOUND));
        int postGameSales=0;
        for (PostGame postGame : postGames) {
            List<String> userNames = postGame.getPlayerIds().stream()
                    .map(playerId -> userRepository.findById(playerId)
                            .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND)))
                    .map(User::getName)
                    .toList();

            List<PaymentInfo> paymentInfos = paymentInfoRepository.findAllByGameId(postGame.getGameId());

            postGameSales += postGame.getRentalCost();
            saleCustomerDtos.add(SaleCustomerDto.toDto(postGame, userNames, paymentInfos.get(paymentInfos.size() - 1)));
        }
        return postGameSales;
    }

    private int addSaleInfoByGame(String courtId, List<SaleCustomerDto> saleCustomerDtos) {
        List<Game> games = gameRepository.findByCourtIdAndGameStatus(courtId, Game.GameStatus.INPLAY);
        int gameSales=0;

        for (Game game : games) {
            List<String> userNames = game.getPlayerIds().stream()
                    .map(playerId -> userRepository.findById(playerId)
                            .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND)))
                    .map(User::getName)
                    .toList();

            List<PaymentInfo> paymentInfos = paymentInfoRepository.findAllByGameId(game.getGameId());

            gameSales += game.getRentalCost(); // 계산된 금액을 gameSales에 더합니다.
            saleCustomerDtos.add(SaleCustomerDto.toDto(game, userNames, paymentInfos.get(paymentInfos.size() - 1)));
        }
        return gameSales;
    }
}
