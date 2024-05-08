package joluphosoin.tennisfunserver.match.data.dto;

import jakarta.validation.constraints.NotBlank;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import lombok.Getter;

@Getter
public class FeedbackReqDto {
    @NotBlank
    String userId;
    @NotBlank
    MatchResult.FeedbackStatus feedback;
}
