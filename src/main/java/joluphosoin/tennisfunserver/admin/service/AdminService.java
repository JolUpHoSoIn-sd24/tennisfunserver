package joluphosoin.tennisfunserver.admin.service;

import joluphosoin.tennisfunserver.admin.data.dto.RegistrationDto;
import joluphosoin.tennisfunserver.admin.data.entity.Admin;
import joluphosoin.tennisfunserver.admin.exception.AdminRegistrationException;
import joluphosoin.tennisfunserver.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    public void registerUser(RegistrationDto registrationDto) throws AdminRegistrationException {
        Optional<Admin> existingAdminOpt = adminRepository.findByEmailId(registrationDto.getEmail());

        if (existingAdminOpt.isPresent()) {
            Admin existingAdmin = existingAdminOpt.get();
            if (existingAdmin.isEmailVerified()) {
                throw new AdminRegistrationException("Email already in use");
            }
        } else {
            createUser(registrationDto);
        }
    }

    private void createUser(RegistrationDto registrationDto) {
        Admin admin = Admin.toEntity(registrationDto, passwordEncoder);
        adminRepository.save(admin);
    }
}