package joluphosoin.tennisfunserver.user.service;

import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.user.data.dto.LocationUpdateDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.data.dto.RegistrationDto;
import joluphosoin.tennisfunserver.user.exception.EmailVerificationException;
import joluphosoin.tennisfunserver.user.exception.LocationUpdateException;
import joluphosoin.tennisfunserver.user.exception.UserLoginException;
import joluphosoin.tennisfunserver.user.exception.UserRegistrationException;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        Optional<User> existingUserOpt = userRepository.findByEmailId(registrationDto.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (existingUser.isEmailVerified()) {
                throw new UserRegistrationException("Email already in use");
            } else {
                updateUser(existingUser, registrationDto);
            }
        } else {
            createUser(registrationDto);
        }
    }

    public User loginUser(String email, String password) throws UserLoginException {
        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UserLoginException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserLoginException("Invalid credentials");
        }

        if(!user.isEmailVerified()){
            throw new UserLoginException(("Email not verified"));
        }

        return user;
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
        userRepository.save(user);
    }

    public void updateUserLocation(String id, LocationUpdateDto locationUpdateDto) throws LocationUpdateException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new LocationUpdateException("User not found with ID"));

        user.setLocation(locationUpdateDto.getLocation());
        user.setMaxDistance(locationUpdateDto.getMaxDistance());
        userRepository.save(user);
    }

    private void updateUser(User user, RegistrationDto registrationDto) throws UserRegistrationException {
        user.setName(registrationDto.getName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setNtrp(registrationDto.getNtrp());
        user.setAge(registrationDto.getAge());
        user.setGender(registrationDto.getGender());

        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.setEmailVerified(false);

        userRepository.save(user);
        sendVerificationEmail(user.getEmailId(), user.getEmailVerificationToken());
    }

    private void createUser(RegistrationDto registrationDto) {
        User newUser = User.builder()
                .emailId(registrationDto.getEmail())
                .name(registrationDto.getName())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .ntrp(registrationDto.getNtrp())
                .age(registrationDto.getAge())
                .gender(registrationDto.getGender())
                .emailVerificationToken(UUID.randomUUID().toString())
                .emailVerified(false)
                .mannerScore(36.5)
                .build();

        userRepository.save(newUser);
        sendVerificationEmail(newUser.getEmailId(), newUser.getEmailVerificationToken());
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

    public List<GameDetailsDto.PlayerDetail> getPlayerDetails(List<String> playerIds) {
        return userRepository.findByIdIn(playerIds).stream()
                .map(this::transformToPlayerDetail)
                .collect(Collectors.toList());
    }

    private GameDetailsDto.PlayerDetail transformToPlayerDetail(User user) {
        GameDetailsDto.PlayerDetail playerDetail = new GameDetailsDto.PlayerDetail();
        playerDetail.setUserId(user.getId());
        playerDetail.setName(user.getName());
        playerDetail.setNtrp(user.getNtrp());
        playerDetail.setAge(user.getAge());
        playerDetail.setGender(user.getGender());
        return playerDetail;
    }

}