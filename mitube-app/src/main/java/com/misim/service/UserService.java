package com.misim.service;

import com.misim.controller.model.Request.SignUpUserRequest;
import com.misim.entity.TermAgreement;
import com.misim.entity.User;
import com.misim.entity.VerificationToken;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.UserRepository;
import com.misim.util.TemporaryPasswordGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TermService termService;
    private final TermAgreementService termAgreementService;
    private final SmsService smsService;
    private final VerificationTokenService verificationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Transactional
    public void registerUser(SignUpUserRequest signUpUserRequest) {

        // 약관 확인 - checkTerms 내부에서 예외 발생
        termService.checkTerms(signUpUserRequest.getCheckedTermTitles());

        // 본인 인증 확인
        if (!smsService.checkVerification(signUpUserRequest.getToken())) {
            throw new MitubeException(MitubeErrorCode.NOT_VERIFIED_SMS_TOKEN);
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(signUpUserRequest.getNickname())) {
            throw new MitubeException(MitubeErrorCode.EXIST_NICKNAME);
        }

        // 이메일 중복 확인
        if (userRepository.existsByEmail(signUpUserRequest.getEmail())) {
            throw new MitubeException(MitubeErrorCode.EXIST_EMAIL);
        }

        // userDto -> user로 변환 (비밀번호 암호화)
        User user = User.builder()
            .nickname(signUpUserRequest.getNickname())
            .password(passwordEncoder.encode(signUpUserRequest.getPassword()))
            .email(signUpUserRequest.getEmail())
            .phoneNumber(signUpUserRequest.getPhoneNumber())
            .build();

        // 유저 정보와 약관 동의 정보 연결
        List<TermAgreement> termAgreements = termAgreementService.getTermAgreements(user,
            signUpUserRequest.getCheckedTermTitles());

        user.setTermAgreements(termAgreements);

        // 유저 정보와 본인 인증 정보 연결
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(user,
            signUpUserRequest.getToken());

        user.setVerificationToken(verificationToken);

        // 유저 정보 저장
        userRepository.save(user);
    }

    @Transactional
    public void resetUserPassword(String nickname, String token) {

        User user = verificationTokenService.findUserByToken(token);

        if (nickname.equals(user.getNickname())) {
            String randomPassword = TemporaryPasswordGenerator.generateRandomPassword();
            user.setPassword(passwordEncoder.encode(randomPassword));

            sendTemporaryPasswordByEmail(user.getEmail(), user.getPassword());

            userRepository.save(user);
        } else {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }
    }

    private void sendTemporaryPasswordByEmail(String toAddress, String password) {

        String fromAddress = "hongkildong990@gmail.com";
        String subject = "Temporary Password Notification";
        String content =
            "Hello,\n\nWe are sending you a temporary password.\n\nTemporary Password: " + password
                + "\n\nPlease be sure to change your password after logging in.\n\nThank you.";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toAddress);
        mail.setFrom(fromAddress);
        mail.setSubject(subject);
        mail.setText(content);

        mailSender.send(mail);
    }
}
