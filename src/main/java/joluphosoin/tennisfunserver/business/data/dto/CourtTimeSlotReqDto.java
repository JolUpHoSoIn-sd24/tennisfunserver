package joluphosoin.tennisfunserver.business.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CourtTimeSlotReqDto {

    @NotBlank
    private String ownerId;

    @NotBlank
    private String courtId;

    private List<TimeSlotDto> timeSlots;

}


