package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.Court;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CourtTimeSlotReqDto {

    private String courtId;

    private List<Court.TimeSlot> timeSlots;

}


