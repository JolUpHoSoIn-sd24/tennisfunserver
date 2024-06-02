package joluphosoin.tennisfunserver.game.service;

import joluphosoin.tennisfunserver.business.service.CourtBusinessService;
import joluphosoin.tennisfunserver.game.data.dto.*;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.exception.CreateGameException;
import joluphosoin.tennisfunserver.game.exception.GetGameException;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
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


    public GameDetailsDto createGame(GameCreationDto gameDto) {

        if (checkPlayerInAnyGame(gameDto.getPlayerIds())) {
            throw new CreateGameException("One or more players are already in an existing game");
        }

        Map<String, Boolean> paymentStatusMap = new HashMap<>();
        for (String playerId : gameDto.getPlayerIds()) {
            paymentStatusMap.put(playerId, false);
        }
        Game game = Game.toEntity(gameDto, paymentStatusMap);

        game.setScores(gameDto.getScores() != null ? mapScores(gameDto.getScores()) : null);
        game.setNtrpFeedbacks(gameDto.getNtrpFeedbacks() != null ? mapNTRPFeedbacks(gameDto.getNtrpFeedbacks()) : null);
        game.setMannerFeedbacks(gameDto.getMannerFeedbacks() != null ? mapMannerFeedbacks(gameDto.getMannerFeedbacks()) : null);

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
        dto.setCourt(courtBusinessService.getCourtDetails(game.getCourtId()));
        dto.setStartTime(game.getStartTime());
        dto.setEndTime(game.getEndTime());
        if(game.getChatRoomId()!=null){
            dto.setChatRoomId(game.getChatRoomId());
        }
        if(game.getRentalCost()!=null){
            dto.setRentalCost(game.getRentalCost());
        }

        return dto;
    }

    private boolean checkPlayerInAnyGame(List<String> playerIds) {
        return gameRepository.findByPlayerIdsIn(playerIds).stream().anyMatch(game -> !game.getPlayerIds().isEmpty());
    }

    public List<Game.Score> mapScores(List<ScoreDto> scoreDtos) {
        return scoreDtos.stream().map(dto -> {
            Game.Score score = new Game.Score();
            score.setUserId(dto.getUserId());
            score.setScoreDetails(mapScoreDetails(dto.getScoreDetails()));
            return score;
        }).toList();
    }

    private Map<String, Game.ScoreDetail> mapScoreDetails(List<ScoreDetailDto> details) {
        Map<String, Game.ScoreDetail> scoreDetails = new HashMap<>();
        for (ScoreDetailDto detail : details) {
            Game.ScoreDetail scoreDetail = new Game.ScoreDetail();
            scoreDetail.setUserScore(detail.getUserScore());
            scoreDetail.setOpponentScore(detail.getOpponentScore());
            scoreDetails.put("detail", scoreDetail);
        }
        return scoreDetails;
    }

    private List<Game.NTRPFeedback> mapNTRPFeedbacks(List<NTRPFeedbackDto> feedbackDtos) {
        return feedbackDtos.stream().map(dto -> new Game.NTRPFeedback(
                dto.getUserId(),
                dto.getOpponentUserId(),
                dto.getNtrp(),
                dto.getComments()
        )).toList();
    }

    private List<Game.MannerFeedback> mapMannerFeedbacks(List<MannerFeedbackDto> feedbackDtos) {
        return feedbackDtos.stream().map(dto -> new Game.MannerFeedback(
                dto.getUserId(),
                dto.getOpponentUserId(),
                dto.getMannerScore(),
                dto.getComments()
        )).toList();
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



}
