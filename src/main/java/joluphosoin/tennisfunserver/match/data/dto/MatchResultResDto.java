package joluphosoin.tennisfunserver.match.data.dto;

import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class MatchResultResDto {
    private String matchRequestId;

    private UserResDto opponent;

    private MatchResult.MatchDetails matchDetails;

    private MatchResult.FeedbackStatus status;

    public static MatchResultResDto toDto(MatchResult matchResult,User user,User opponent){

        Map<String, MatchResult.FeedbackStatus> feedback = matchResult.getFeedback();
        MatchResult.FeedbackStatus status = feedback.get(user.getId());


        Map<String, String> userAndMatchRequests = matchResult.getUserAndMatchRequests();

        String matchRequestId = userAndMatchRequests.get(user.getId());

        return MatchResultResDto.builder()
                .matchRequestId(matchRequestId)
                .opponent(UserResDto.toDto(opponent))
                .matchDetails(matchResult.getMatchDetails())
                .status(status)
                .build();
    }


}
