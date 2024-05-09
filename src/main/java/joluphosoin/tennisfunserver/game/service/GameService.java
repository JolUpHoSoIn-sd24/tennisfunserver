package joluphosoin.tennisfunserver.game.service;

import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public void createGame(GameCreationDto gameDto) {
            Game game = Game.builder()
                    .gameStatus(gameDto.getGameStatus())
                    .playerIds(gameDto.getPlayerIds())
                    .courtId(gameDto.getCourtId())
                    .dateTime(gameDto.getDateTime())
                    .chatRoomId(gameDto.getChatRoomId())
                    .rentalCost(gameDto.getRentalCost())
                    .scores(mapScores(gameDto.getScores()))
                    .scoreConfirmed(gameDto.isScoreConfirmed())
                    .ntrpFeedbacks(mapNTRPFeedbacks(gameDto.getNtrpFeedbacks()))
                    .mannerFeedbacks(mapMannerFeedbacks(gameDto.getMannerFeedbacks()))
                    .build();
            gameRepository.save(game);
    }

    private List<Game.Score> mapScores(List<GameCreationDto.ScoreDto> scoreDtos) {
        return scoreDtos.stream().map(dto -> {
            Game.Score score = new Game.Score();
            score.setUserId(dto.getUserId());
            score.setScoreDetails(mapScoreDetails(dto.getScoreDetails()));
            return score;
        }).toList();
    }

    private Map<String, Game.ScoreDetail> mapScoreDetails(List<GameCreationDto.ScoreDetailDto> details) {
        Map<String, Game.ScoreDetail> scoreDetails = new HashMap<>();
        for (GameCreationDto.ScoreDetailDto detail : details) {
            Game.ScoreDetail scoreDetail = new Game.ScoreDetail();
            scoreDetail.setUserScore(detail.getUserScore());
            scoreDetail.setOpponentScore(detail.getOpponentScore());
            scoreDetails.put("detail", scoreDetail);  // Assuming a key is needed. Modify as necessary.
        }
        return scoreDetails;
    }

    private List<Game.NTRPFeedback> mapNTRPFeedbacks(List<GameCreationDto.NTRPFeedbackDto> feedbackDtos) {
        return feedbackDtos.stream().map(dto -> new Game.NTRPFeedback(
                dto.getUserId(),
                dto.getOpponentUserId(),
                dto.getNtrp(),
                dto.getComments()
        )).toList();
    }

    private List<Game.MannerFeedback> mapMannerFeedbacks(List<GameCreationDto.MannerFeedbackDto> feedbackDtos) {
        return feedbackDtos.stream().map(dto -> new Game.MannerFeedback(
                dto.getUserId(),
                dto.getOpponentUserId(),
                dto.getMannerScore(),
                dto.getComments()
        )).toList();
    }
}
