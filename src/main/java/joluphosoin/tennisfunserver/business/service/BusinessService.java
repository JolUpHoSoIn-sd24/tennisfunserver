package joluphosoin.tennisfunserver.business.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import joluphosoin.tennisfunserver.business.data.dto.AccountReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessReqDto;
import joluphosoin.tennisfunserver.business.data.dto.BusinessResDto;
import joluphosoin.tennisfunserver.business.data.entity.Business;
import joluphosoin.tennisfunserver.business.repository.BusinessRepository;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.user.exception.EmailVerificationException;
import joluphosoin.tennisfunserver.user.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BusinessService {

    @Value("${app.base-url}")
    private String baseUrl;
    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    @Transactional
    public BusinessResDto registerBusiness(BusinessReqDto businessReqDto) throws MessagingException{
        validatePassword(businessReqDto.getPassword());
        Optional<Business> optBusiness = businessRepository.findByEmailId(businessReqDto.getEmailId());
        Business business;
        if(optBusiness.isPresent()){
            Business existingBusiness = optBusiness.get();
            if(existingBusiness.isEmailVerified()){
                throw new GeneralException(ErrorStatus.EMAIL_ALREADY_EXISTS);
            }
            else{
                business = Business.setEntity(existingBusiness, businessReqDto, passwordEncoder);
            }
        }
        else{
            business = Business.toEntity(businessReqDto,passwordEncoder);
        }
        businessRepository.save(business);
        sendVerificationEmail(business.getEmailId(), business.getEmailVerificationToken());
        return BusinessResDto.toDto(business);
    }

    @Transactional
    public BusinessResDto updateAccount(AccountReqDto accountReqDto) {

        Business business = businessRepository.findById(accountReqDto.getBusinessInfoId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.BUSINESS_NOT_FOUND));

        business.setBank(accountReqDto.getBank());
        business.setAccountNumber(accountReqDto.getAccountNumber());

        businessRepository.save(business);

        return BusinessResDto.toDto(business);
    }
    public void verifyEmail(String token) throws EmailVerificationException {

        Business business = businessRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new EmailVerificationException("Invalid email verification token"));

        if (business.isEmailVerified()){
            throw new EmailVerificationException("Email already verified");
        }

        business.setEmailVerified(true);
        businessRepository.save(business);
    }
    private void sendVerificationEmail(String email, String token) throws MessagingException {
        String link = baseUrl + "/api/business/verify-email?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Verify your email");
        String content = "<html><body><h1>Email Verification</h1>" +
                "<p>Please click on the link to verify your email:</p>" +
                "<a href=\"" + link + "\">Verify Email</a></body></html>";
        helper.setText(content, true);
        mailSender.send(message);
    }

    private void validatePassword(String password) throws UserRegistrationException {
        String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (StringUtils.isBlank(password) || !Pattern.matches(pattern, password)) {
            // erro 커스텀 만들기
            throw new UserRegistrationException(
                    "Password does not meet complexity requirements. It must be at least 8 characters long, " +
                            "contain at least one uppercase letter, one lowercase letter, one digit, and one special character " +
                            "(@#$%^&+=). No whitespace allowed."
            );
        }
    }

}
