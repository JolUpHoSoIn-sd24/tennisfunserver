package joluphosoin.tennisfunserver.game.service;

import joluphosoin.tennisfunserver.game.data.dto.ConfirmDto;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.game.data.entity.Score;
import joluphosoin.tennisfunserver.game.repository.PostGameRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final PostGameRepository postGameRepository;
    public List<Score> confirmScore(ConfirmDto confirmDto,String userId) {
        PostGame postGame = postGameRepository.findById(confirmDto.getGameId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NOT_FOUND));

        List<Score> scores = postGame.getScores();

        Score opponentUploadScore = scores.stream().filter(score -> !score.getUserId().equals(userId))
                .findFirst().orElseThrow(() -> new GeneralException(ErrorStatus.SCORE_NOT_FOUND));

        if(confirmDto.isConfirm()){
            opponentUploadScore.setAgreement(true);
            boolean allAgreed = scores.stream().allMatch(Score::isAgreement);
            if (allAgreed) {
                postGame.setScoreConfirmed(true);
                postGame.setPostGameStatus(PostGame.PostGameStatus.POSTGAME);
            }
        }
        else{
            opponentUploadScore.setAgreement(false);
            opponentUploadScore.disagreement();
        }

        postGameRepository.save(postGame);
        return postGame.getScores();
    }
}
