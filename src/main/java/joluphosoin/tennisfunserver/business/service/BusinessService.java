package joluphosoin.tennisfunserver.business.service;

import joluphosoin.tennisfunserver.business.data.dto.AccountReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessInfoReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessInfoResDto;
import joluphosoin.tennisfunserver.business.data.entity.Business;
import joluphosoin.tennisfunserver.business.repository.BusinessRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    @Transactional
    public BusinessInfoResDto registerBusinessInfo(BusinessInfoReqDto businessInfoReqDto) {

        Business business = businessInfoReqDto.toEntity();

        businessRepository.save(business);

        return BusinessInfoResDto.toDto(business);
    }

    @Transactional
    public BusinessInfoResDto registerAccount(AccountReqDto accountReqDto) {

        Business business = businessRepository.findById(accountReqDto.getBusinessInfoId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.BUSINESS_NOT_FOUND));

        business.setBank(accountReqDto.getBank());
        business.setAccountNumber(accountReqDto.getAccountNumber());

        businessRepository.save(business);

        return BusinessInfoResDto.toDto(business);

    }
}
