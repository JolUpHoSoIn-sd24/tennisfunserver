package joluphosoin.tennisfunserver.business.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "business")
@Getter
@AllArgsConstructor
@Builder
public class Business {

    @MongoId
    private String id;

    private String businessRegistrationNumber; // 사업자 등록 번호

    private List<String> documents= new ArrayList<>(); // 관련 서류 목록

    @Setter
    private String bank;

    @Setter
    private String accountNumber;

    private RegistrationStatus registrationStatus; // 가입 상태

    public enum RegistrationStatus {
        PRE_APPROVAL, // 승인 전
        APPROVED // 승인 완료
    }
}