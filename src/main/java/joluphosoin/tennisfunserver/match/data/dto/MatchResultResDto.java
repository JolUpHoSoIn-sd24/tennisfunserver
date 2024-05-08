package joluphosoin.tennisfunserver.match.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import joluphosoin.tennisfunserver.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class MatchResultResDto {
    private String matchRequestId;

    private User opponent;

    private MatchResult.MatchDetails matchDetails;

    private MatchResult.FeedbackStatus status;

    public static MatchResultResDto toDto(MatchResult matchResult,String userId){

        List<User> users = matchResult.getUsers();

        User opponent=users.stream()
                .filter(user -> !user.getId().equals(userId))
                .findAny()
                .orElse(null);

        Map<String, MatchResult.FeedbackStatus> feedback = matchResult.getFeedback();
        MatchResult.FeedbackStatus status = feedback.get(userId);

        return MatchResultResDto.builder()
                .matchRequestId(matchResult.getMatchRequestId())
                .opponent(opponent)
                .matchDetails(matchResult.getMatchDetails())
                .status(status)
                .build();
    }


}
