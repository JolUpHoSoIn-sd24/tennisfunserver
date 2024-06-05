package joluphosoin.tennisfunserver.game.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ConfirmDto {

    private String gameId;

    private boolean isConfirm;
}
