package joluphosoin.tennisfunserver.business.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SimpleCourtResDto {
    private String courtName;
    private SimpleCustomerDto simpleCustomerDto;

    public static SimpleCourtResDto toDto(String courtName, SimpleCustomerDto simpleCustomerDto){
        return SimpleCourtResDto.builder()
                .courtName(courtName)
                .simpleCustomerDto(simpleCustomerDto)
                .build();
    }

}

