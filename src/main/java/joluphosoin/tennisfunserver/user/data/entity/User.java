package joluphosoin.tennisfunserver.user.data.entity;


import joluphosoin.tennisfunserver.user.data.dto.RegistrationDto;
import lombok.*;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Document(collection = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @MongoId
    private String id;

    @Indexed
    private String name;

    private String password;

    @Indexed(unique = true)
    private String emailId;

    private Integer age; // 나이 필드 추가

    private String gender; // 성별 필드 추가

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    private Double maxDistance;

    private List<String> dislikedCourts;

    private double ntrp;

    private Double mannerScore;

    private List<String> clubIds;

    private boolean emailVerified;

    private String emailVerificationToken;

    private String webSocketId;

    public static User toEntity(RegistrationDto registrationDto,
                                PasswordEncoder passwordEncoder){

        Integer age = calculateAge(registrationDto.getBirthDate());

        return User.builder()
                .emailId(registrationDto.getEmail())
                .name(registrationDto.getName())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .ntrp(registrationDto.getNtrp())
                .age(age)
                .gender(registrationDto.getGender())
                .emailVerificationToken(UUID.randomUUID().toString())
                .emailVerified(false)
                .mannerScore(36.5)
                .maxDistance(5.5)
                .location(new Point(127.0443767,37.2843727))
                .build();
    }

    public static Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return null;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }


}
