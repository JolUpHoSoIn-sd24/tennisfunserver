package joluphosoin.tennisfunserver.business.data.dto;

import joluphosoin.tennisfunserver.business.data.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SimpleBusinessResDto {
    private String shopName;

    private String address;

    private List<CourtResDto> courtInfo;

    public static SimpleBusinessResDto toDto(Business business, List<CourtResDto> courtResDtos){

        return SimpleBusinessResDto.builder()
                .shopName(business.getShopName())
                .address(business.getAddress())
                .courtInfo(courtResDtos)
                .build();
    }
}
