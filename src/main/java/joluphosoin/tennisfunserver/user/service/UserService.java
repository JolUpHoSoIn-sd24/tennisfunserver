package joluphosoin.tennisfunserver.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.data.entity.PostGame;
import joluphosoin.tennisfunserver.match.repository.MatchRequestRepository;
import joluphosoin.tennisfunserver.match.repository.MatchResultRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.user.data.dto.LocationUpdateDto;
import joluphosoin.tennisfunserver.user.data.dto.RegistrationDto;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.exception.EmailVerificationException;
import joluphosoin.tennisfunserver.user.exception.LocationUpdateException;
import joluphosoin.tennisfunserver.user.exception.UserLoginException;
import joluphosoin.tennisfunserver.user.exception.UserRegistrationException;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${app.base-url}")
    private String baseUrl;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    private final MatchRequestRepository matchRequestRepository;
    private final MatchResultRepository matchResultRepository;


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
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new GeneralException(ErrorStatus.PW_NOT_MATCH);
        }

        if(!user.isEmailVerified()){
            throw new GeneralException(ErrorStatus.EMAIL_NOT_VAILD);
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

        user.updateLocation(locationUpdateDto);
        userRepository.save(user);
    }

    private void updateUser(User user, RegistrationDto registrationDto) throws UserRegistrationException {
        user.setEntity(registrationDto, passwordEncoder);
        userRepository.save(user);
//        sendVerificationEmail(user.getEmailId(), user.getEmailVerificationToken());
    }

    private void createUser(RegistrationDto registrationDto) {
        User user = User.toEntity(registrationDto, passwordEncoder);
        userRepository.save(user);
//        sendVerificationEmail(user.getEmailId(), user.getEmailVerificationToken());
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

    public List<GameDetailsDto.PlayerDetail> getPlayerDetails(Game game) {
        return userRepository.findByIdIn(game.getPlayerIds()).stream()
                .map(user -> transformToPlayerDetail(user, game))  // 람다 표현식을 사용하여 Game 객체를 포함
                .toList();
    }
    public List<GameDetailsDto.PlayerDetail> getPlayerDetails(PostGame game) {
        return userRepository.findByIdIn(game.getPlayerIds()).stream()
                .map(user -> transformToPlayerDetail(user, game))  // 람다 표현식을 사용하여 Game 객체를 포함
                .toList();
    }

    private GameDetailsDto.PlayerDetail transformToPlayerDetail(User user,Game game) {
        GameDetailsDto.PlayerDetail playerDetail = new GameDetailsDto.PlayerDetail();
        playerDetail.setUserId(user.getId());
        playerDetail.setName(user.getName());
        playerDetail.setNtrp(user.getNtrp());
        playerDetail.setAge(user.getAge());
        playerDetail.setGender(user.getGender());

        boolean hasFeedback = game.getFeedbacks().stream()
                .anyMatch(feedback -> Objects.equals(feedback.getEvaluatorId(), user.getId()));

        playerDetail.setFeedback(hasFeedback);

        return playerDetail;
    }
    private GameDetailsDto.PlayerDetail transformToPlayerDetail(User user,PostGame game) {
        GameDetailsDto.PlayerDetail playerDetail = new GameDetailsDto.PlayerDetail();
        playerDetail.setUserId(user.getId());
        playerDetail.setName(user.getName());
        playerDetail.setNtrp(user.getNtrp());
        playerDetail.setAge(user.getAge());
        playerDetail.setGender(user.getGender());

        boolean hasFeedback = game.getFeedbacks().stream()
                .anyMatch(feedback -> Objects.equals(feedback.getEvaluatorId(), user.getId()));

        playerDetail.setFeedback(hasFeedback);

        return playerDetail;
    }

    public UserResDto getUserInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        return UserResDto.toDto(user);
    }


    public UserResDto revoke(String userId) {
        User user = userRepository.findById((userId)).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        matchRequestRepository.deleteAllByUserId(userId);
        matchResultRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);

        return UserResDto.toDto(user);
    }
}