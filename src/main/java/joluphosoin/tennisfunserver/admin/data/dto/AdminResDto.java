package joluphosoin.tennisfunserver.admin.data.dto;

import joluphosoin.tennisfunserver.admin.data.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AdminResDto {

    private String id;

    private String emailId;

    private boolean emailVerified;

    public static AdminResDto toDto(Admin admin){
        return AdminResDto.builder()
                .id(admin.getId())
                .emailId(admin.getEmailId())
                .emailVerified(admin.isEmailVerified())
                .build();
    }
}
