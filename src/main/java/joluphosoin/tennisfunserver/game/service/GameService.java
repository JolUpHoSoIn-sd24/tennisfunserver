package joluphosoin.tennisfunserver.game.service;

import joluphosoin.tennisfunserver.business.service.CourtBusinessService;
import joluphosoin.tennisfunserver.game.data.dto.*;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.game.exception.CreateGameException;
import joluphosoin.tennisfunserver.game.exception.GetGameException;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.game.repository.PostGameRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import joluphosoin.tennisfunserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final CourtBusinessService courtBusinessService;
    private final UserRepository userRepository;
    private final PostGameRepository postGameRepository;

    public GameDetailsDto createGame(GameCreationDto gameDto) {

        if (checkPlayerInAnyGame(gameDto.getPlayerIds())) {
            throw new CreateGameException("One or more players are already in an existing game");
        }

        Map<String, Boolean> paymentStatusMap = new HashMap<>();
        for (String playerId : gameDto.getPlayerIds()) {
            paymentStatusMap.put(playerId, false);
        }
        Game game = Game.toEntity(gameDto, paymentStatusMap);

        gameRepository.save(game);

        return transformGameToDto(game);
    }

    public Game findGameByUserId(String userId) {
        return gameRepository.findByPlayerIdsContaining(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new GetGameException("No game found for this user"));
    }

    public GameDetailsDto transformGameToDto(Game game) {
        GameDetailsDto dto = new GameDetailsDto();
        dto.setGameId(game.getGameId());
        dto.setState(game.getGameStatus().name());
        dto.setPlayers(userService.getPlayerDetails(game.getPlayerIds()));
        dto.setCourt(courtBusinessService.getCourtDetails(game.getMatchDetails().getCourtId()));
        dto.setStartTime(game.getMatchDetails().getStartTime());
        dto.setEndTime(game.getMatchDetails().getEndTime());
        dto.setPaymentStatus(game.getPaymentStatus());
        if (game.getChatRoomId() != null) {
            dto.setChatRoomId(game.getChatRoomId());
        }
        if (game.getRentalCost() != null) {
            dto.setRentalCost(game.getRentalCost());
        }

        return dto;
    }

    private boolean checkPlayerInAnyGame(List<String> playerIds) {
        return gameRepository.findByPlayerIdsIn(playerIds).stream().anyMatch(game -> !game.getPlayerIds().isEmpty());
    }


    public List<HistoryResDto> getGameHistory(String userId) {
        List<Game> games = gameRepository.findByUserIdContainingPlayerIds(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_HISTORY_NO_CONTENT));

        return games.stream()
                .map(game -> {
                    String opponentId = game.getPlayerIds().stream()
                            .filter(playerId -> !playerId.equals(userId))
                            .findFirst()
                            .orElse(null);
                    return HistoryResDto.toDto(game, userService.getUserInfo(opponentId));
                })
                .toList();
    }

    public FeedbackResDto registerFeedback(FeedbackDto feedbackDto, String userId) {

        Game game = gameRepository.findByPlayerIdsContaining(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NOT_FOUND));
        if(game.getGameStatus()== Game.GameStatus.PREGAME){
            throw new GeneralException(ErrorStatus.GAME_PAYMENT_REQUIRED);
        }
        String opponentId = game.getPlayerIds().stream().filter(id -> !id.equals(userId)).findFirst().orElseThrow();

        game.addFeedback(feedbackDto, userId,opponentId);
        game.addScore(feedbackDto.getScoreDetailDto(), userId);

        User opponent = userRepository.findById(opponentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        opponent.updateFeedback(feedbackDto);

        if (game.getFeedbacks().size() == game.getPlayerIds().size()) {
            PostGame postGame = PostGame.toEntity(game);
            postGameRepository.save(postGame);
            gameRepository.delete(game);
            return FeedbackResDto.toDto(feedbackDto, opponent, postGame);
        }
        gameRepository.save(game);
        return FeedbackResDto.toDto(feedbackDto, opponent, game);
    }


}
