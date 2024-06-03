package joluphosoin.tennisfunserver.admin.data.entity;

import joluphosoin.tennisfunserver.admin.data.dto.AdminRegistrationDto;
import joluphosoin.tennisfunserver.admin.data.dto.RegistrationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.crypto.password.PasswordEncoder;

@Document(collection = "admin")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Admin {

    @MongoId
    private String id;

    private String emailId;

    private String password;

    private boolean emailVerified;

    public static Admin toEntity(RegistrationDto adminRegistrationDto,
                                 PasswordEncoder passwordEncoder){

        return Admin.builder()
                .emailId(adminRegistrationDto.getEmail())
                .password(passwordEncoder.encode(adminRegistrationDto.getPassword()))
                .emailVerified(false)
                .build();
    }

    public Admin setEntity(RegistrationDto adminRegistrationDto, PasswordEncoder passwordEncoder){
        this.emailId = adminRegistrationDto.getEmail();
        this.password = passwordEncoder.encode(adminRegistrationDto.getPassword());
        this.emailVerified = false;
        return this;
    }
}
