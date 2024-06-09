package joluphosoin.tennisfunserver.business.data.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SaleResDto {

    private int totalSales;

    private List<SaleCustomerDto> saleCustomerDtos;

    public static SaleResDto toDto(int totalSales, List<SaleCustomerDto> saleCustomerDtos){

        return SaleResDto.builder()
                .totalSales(totalSales)
                .saleCustomerDtos(saleCustomerDtos)
                .build();
    }
}
