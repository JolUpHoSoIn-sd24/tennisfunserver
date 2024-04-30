package joluphosoin.tennisfunserver.business.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "businessInfo")
@Getter
@AllArgsConstructor
@Builder
public class BusinessInfo {

    @MongoId
    private String businessInfoId;

    private String userId; // 사업자 정보를 제출한 사용자 ID

    private String businessRegistrationNumber; // 사업자 등록 번호

    private List<String> documents; // 관련 서류 목록

}