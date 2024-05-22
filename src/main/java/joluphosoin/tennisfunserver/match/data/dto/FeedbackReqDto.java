package joluphosoin.tennisfunserver.match.data.dto;

import jakarta.validation.constraints.NotNull;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import lombok.Getter;

@Getter
public class FeedbackReqDto {
    @NotNull
    MatchResult.FeedbackStatus feedback;
}
