package joluphosoin.tennisfunserver.business.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@Builder
@AllArgsConstructor
public class CourtHoursDto {
    private DayOfWeek dayOfWeek;
    private String openTime;
    private String closeTime;
}
