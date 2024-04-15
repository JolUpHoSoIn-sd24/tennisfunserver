package JolUpHoSoIn.TennisFun_Server.club.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "club")
@Getter
@AllArgsConstructor
@Builder
public class Club {

    @MongoId
    private String clubId; // 동호회 ID

    private String name; // 동호회 이름

    private String description; // 동호회 설명

    private List<String> memberIds; // 멤버들의 사용자 ID 목록


}