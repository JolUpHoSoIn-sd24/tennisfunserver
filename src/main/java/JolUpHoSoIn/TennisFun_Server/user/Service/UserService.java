package JolUpHoSoIn.TennisFun_Server.user.Service;

import JolUpHoSoIn.TennisFun_Server.user.data.entity.User;
import JolUpHoSoIn.TennisFun_Server.user.data.dto.RegistrationDto;
import JolUpHoSoIn.TennisFun_Server.user.exception.UserRegistrationException;
import JolUpHoSoIn.TennisFun_Server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegistrationDto registrationDto) throws Exception {

        validatePassword(registrationDto.getPassword());

        if(userRepository.existsByEmailId(registrationDto.getEmail())) {
            throw new UserRegistrationException("Email already in use");
        }

        User newUser = new User();
        newUser.setEmailId(registrationDto.getEmail());
        newUser.setName(registrationDto.getName());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setNtrp(registrationDto.getNtrp());
        newUser.setAge(registrationDto.getAge());
        newUser.setGender(registrationDto.getGender());

        return userRepository.save(newUser);
    }

    private void validatePassword(String password) throws UserRegistrationException {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (StringUtils.isBlank(password) || !Pattern.matches(pattern, password)) {
            throw new UserRegistrationException(
                    "Password does not meet complexity requirements. It must be at least 8 characters long, " +
                            "contain at least one uppercase letter, one lowercase letter, one digit, and one special character " +
                            "(@#$%^&+=). No whitespace allowed."
            );
        }
    }
}