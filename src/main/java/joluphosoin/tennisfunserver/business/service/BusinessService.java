package joluphosoin.tennisfunserver.business.service;

import joluphosoin.tennisfunserver.business.data.dto.AccountReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessInfoReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessInfoResDto;
import joluphosoin.tennisfunserver.business.data.entity.BusinessInfo;
import joluphosoin.tennisfunserver.business.repository.BusinessInfoRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessInfoRepository businessInfoRepository;

    @Transactional
    public BusinessInfoResDto registerBusinessInfo(BusinessInfoReqDto businessInfoReqDto) {

        BusinessInfo businessInfo = businessInfoReqDto.toEntity();

        businessInfoRepository.save(businessInfo);

        return BusinessInfoResDto.toDto(businessInfo);
    }

    @Transactional
    public BusinessInfoResDto registerAccount(AccountReqDto accountReqDto) {

        BusinessInfo businessInfo = businessInfoRepository.findById(accountReqDto.getBusinessInfoId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.BUSINESS_NOT_FOUND));

        businessInfo.setBank(accountReqDto.getBank());
        businessInfo.setAccountNumber(accountReqDto.getAccountNumber());

        businessInfoRepository.save(businessInfo);

        return BusinessInfoResDto.toDto(businessInfo);

    }
}
