package joluphosoin.tennisfunserver.admin.service;

import joluphosoin.tennisfunserver.admin.data.dto.AdminRegistrationDto;
import joluphosoin.tennisfunserver.admin.data.dto.AdminResDto;
import joluphosoin.tennisfunserver.admin.data.entity.Admin;
import joluphosoin.tennisfunserver.admin.exception.AdminRegistrationException;
import joluphosoin.tennisfunserver.admin.repository.AdminRepository;
import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.game.service.GameService;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestResDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import joluphosoin.tennisfunserver.match.repository.MatchRequestRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.payment.data.entity.PaymentInfo;
import joluphosoin.tennisfunserver.payment.repository.PaymentInfoRepository;
import joluphosoin.tennisfunserver.user.data.dto.LoginDto;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final MatchRequestRepository matchRequestRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    private final GameService gameService;

    public AdminResDto registerAdmin(AdminRegistrationDto adminRegistrationDto) throws AdminRegistrationException {
        Optional<Admin> existingAdminOpt = adminRepository.findByEmailId(adminRegistrationDto.getEmail());

        if (existingAdminOpt.isPresent()) {
            Admin existingAdmin = existingAdminOpt.get();
            if (existingAdmin.isEmailVerified()) {
                throw new GeneralException(ErrorStatus.EMAIL_ALREADY_EXISTS);
            }
            else{
                existingAdmin.setEntity(adminRegistrationDto, passwordEncoder);
                adminRepository.save(existingAdmin);
                return AdminResDto.toDto(existingAdmin);
            }
        }
        Admin admin = Admin.toEntity(adminRegistrationDto, passwordEncoder);
        adminRepository.save(admin);

        return AdminResDto.toDto(admin);

    }

    public Admin loginAdmin(LoginDto loginDto) {

        Admin admin = adminRepository.findByEmailId(loginDto.getEmail()).orElseThrow(() -> new GeneralException(ErrorStatus.ADMIN_NOT_FOUND));

        if (!passwordEncoder.matches(loginDto.getPassword(), admin.getPassword())) {
            throw new GeneralException(ErrorStatus.PW_NOT_MATCH);
        }

        if(!admin.isEmailVerified()){
            throw new GeneralException(ErrorStatus.EMAIL_NOT_VAILD);
        }

        return admin;
    }

    public List<UserResDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResDto> userResDtos = new ArrayList<>();
        users.forEach(user ->
            userResDtos.add(UserResDto.toDto(user))
        );
        return userResDtos;
    }

    public List<GameDetailsDto> getAllGames() {
        List<Game> games = gameRepository.findAll();
        List<GameDetailsDto> gameDetailsDtos = new ArrayList<>();

        games.forEach(game ->
            gameDetailsDtos.add(gameService.transformGameToDto(game))
        );

        return gameDetailsDtos;
    }

    public List<MatchRequestResDto> getAllMatchRequests() {

        List<MatchRequest> matchRequests = matchRequestRepository.findAll();
        List<MatchRequestResDto> matchRequestResDtos = new ArrayList<>();
        if(matchRequests.isEmpty()){
            throw new GeneralException(ErrorStatus.MATCH_REQUESTS_RETRIEVED_FAILED);
        }

        matchRequests.forEach(matchRequest ->
            matchRequestResDtos.add(MatchRequestResDto.toDto(matchRequest))
        );
        return matchRequestResDtos;
    }

    public List<PaymentInfo> getAllPayments() {
        return paymentInfoRepository.findAll();
    }
}
