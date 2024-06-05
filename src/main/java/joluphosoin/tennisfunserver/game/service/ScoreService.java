package joluphosoin.tennisfunserver.game.service;

import joluphosoin.tennisfunserver.game.data.dto.ScoreDetailDto;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final GameService gameService;
    private final GameRepository gameRepository;

    public void registerTempScore(ScoreDetailDto scoreDetailDto, String userId) {

//        Game game = gameService.findGameByUserId(userId);
//        List<Score> scores = game.getScores();
//        Boolean flag = false;
//        for (Score score : scores) {
//            if(score.getUserId().equals(userId)){
//                score.setScoreDetailDto(scoreDetailDto);
//                flag = true;
//                break;
//            }
//        }
//        if(!flag){
//            scores.add(new Score(userId, scoreDetailDto,0));
//        }
//        game.setScores(scores);
//        gameRepository.save(game);
    }

    public void updateTempScore(ScoreDetailDto scoreDetailDto, String userId) {

    }
}
