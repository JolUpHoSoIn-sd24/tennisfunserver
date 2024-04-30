package JolUpHoSoIn.TennisFun_Server.user.Service;

import JolUpHoSoIn.TennisFun_Server.user.data.entity.User;
import JolUpHoSoIn.TennisFun_Server.user.data.dto.RegistrationDto;
import JolUpHoSoIn.TennisFun_Server.user.exception.EmailVerificationException;
import JolUpHoSoIn.TennisFun_Server.user.exception.UserRegistrationException;
import JolUpHoSoIn.TennisFun_Server.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${app.base-url}")
    private String baseUrl;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public void registerUser(RegistrationDto registrationDto) throws UserRegistrationException {

        validatePassword(registrationDto.getPassword());

        if(userRepository.existsByEmailId(registrationDto.getEmail())) {
            throw new UserRegistrationException("Email already in use");
        }

        User newUser = User.builder()
                .emailId(registrationDto.getEmail())
                .name(registrationDto.getName())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .ntrp(registrationDto.getNtrp())
                .age(registrationDto.getAge())
                .gender(registrationDto.getGender())
                .emailVerificationToken(UUID.randomUUID().toString())
                .expirationDate(Timestamp.valueOf(LocalDateTime.now().plusHours(1)))
                .emailVerified(false)
                .build();

        userRepository.save(newUser);
        sendVerificationEmail(newUser.getEmailId(), newUser.getEmailVerificationToken());
    }

    public void verifyEmail(String token) throws EmailVerificationException {
        Optional<User> userOptional = userRepository.findByEmailVerificationToken(token);
        if (userOptional.isEmpty()) {
            throw new EmailVerificationException("Invalid email verification token");
        }

        User user = userOptional.get();
        if (user.isEmailVerified()) {
            throw new EmailVerificationException("Email already verified");
        }

        user.setEmailVerified(true);
        user.setExpirationDate(null);
        userRepository.save(user);
    }

    private void sendVerificationEmail(String email, String token) {
        String link = baseUrl + "/api/user/verify-email?token=" + token;
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Verify your email");
            String content = "<html><body><h1>Email Verification</h1>" +
                    "<p>Please click on the link to verify your email:</p>" +
                    "<a href=\"" + link + "\">Verify Email</a></body></html>";
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void validatePassword(String password) throws UserRegistrationException {
        String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (StringUtils.isBlank(password) || !Pattern.matches(pattern, password)) {
            throw new UserRegistrationException(
                    "Password does not meet complexity requirements. It must be at least 8 characters long, " +
                            "contain at least one uppercase letter, one lowercase letter, one digit, and one special character " +
                            "(@#$%^&+=). No whitespace allowed."
            );
        }
    }
}