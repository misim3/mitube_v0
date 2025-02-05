package com.misim.service;

import com.misim.controller.model.Response.FindNicknameResponse;
import com.misim.entity.SmsVerification;
import com.misim.entity.User;
import com.misim.entity.VerificationToken;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.SmsVerificationRepository;
import com.misim.repository.UserRepository;
import com.misim.repository.VerificationTokenRepository;
import com.misim.util.Base64Convertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final SmsVerificationRepository smsVerificationRepository;
    private final UserRepository userRepository;

    public User findUserByToken(String token) {

        Long id = Base64Convertor.decode(token);

        if (!smsVerificationRepository.existsById(id)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_SMS_TOKEN);
        }

        VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenBySmsVerificationId(
            id);

        return verificationToken.getUser();
    }

    public FindNicknameResponse findUserNicknameByToken(String token) {

        Long id = Base64Convertor.decode(token);

        if (!smsVerificationRepository.existsById(id)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_SMS_TOKEN);
        }

        VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenBySmsVerificationId(
            id);

        FindNicknameResponse response = new FindNicknameResponse();
        response.setNickname(verificationToken.getUser().getNickname());

        return response;
    }

    public VerificationToken getVerificationToken(User user, String token) {

        Long id = Base64Convertor.decode(token);

        SmsVerification smsVerification = smsVerificationRepository.findById(id)
            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_SMS_TOKEN));

        if (verificationTokenRepository.existsVerificationTokenBySmsVerificationId(id)) {
            throw new MitubeException(MitubeErrorCode.USED_SMS_TOKEN);
        }

        return VerificationToken.builder()
            .user(user)
            .smsVerification(smsVerification)
            .build();
    }
}
