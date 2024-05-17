package joluphosoin.tennisfunserver.business.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AutoMatchApplyDto {

    @NotBlank
    private String courtId;

    private List<String> dates;
}
