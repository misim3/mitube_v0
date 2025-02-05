package com.misim.service;

import com.misim.controller.model.Response.VerifySMSResponse;
import com.misim.entity.SmsVerification;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.SmsVerificationRepository;
import com.misim.util.Base64Convertor;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsVerificationRepository smsVerificationRepository;

    @Value(value = "${coolsms.api-key}")
    private String apiKey;
    @Value(value = "${coolsms.api-secret-key}")
    private String apiSecretKey;
    @Value(value = "${coolsms.api-url}")
    private String apiUrl;
    @Value(value = "${coolsms.from-phonenumber}")
    private String fromPhoneNumber;

    public void sendSMS(String phoneNumber) {

        smsVerificationRepository.save(makeSmsVerification(phoneNumber, makeCode()));

        /* 매 실행 혹은 테스트마다 sms api를 호출하는 것이 비 효율적이다.
         따라서 위 코드처럼 문자 전송이 완료되었다고 가정하자.

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, apiUrl);

        String code = makeCode();

        Message message = new Message();
        message.setFrom(fromPhoneNumber);
        message.setTo(phoneNumber);
        message.setText("인증코드:[" + code + "]");

        // 전송 결과 확인
        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

        if (response != null) {
            String responseStatus = response.getStatusCode();

            if (responseStatus.equals("2000") || responseStatus.equals("3000")) {

                SmsVerification smsVerification = makeSmsVerification(phoneNumber, code);

                smsVerificationRepository.save(smsVerification);
            }
        }
        
        // 에러처리
        */
    }

    private SmsVerification makeSmsVerification(String phoneNumber, String code) {

        SmsVerification smsVerification = smsVerificationRepository.findTopByPhoneNumberOrderByExpiryDateDesc(
            phoneNumber);

        if (smsVerification == null) {
            smsVerification = SmsVerification.builder()
                .phoneNumber(phoneNumber)
                .build();
        }

        smsVerification.setVerificationCode(code);
        smsVerification.setExpiryDate(LocalDateTime.now());
        smsVerification.setCurrentFailures(0);
        smsVerification.setVerified(false);

        return smsVerification;
    }

    public String makeCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public VerifySMSResponse matchSMS(String phoneNumber, String code, LocalDateTime current) {

        SmsVerification smsVerification = smsVerificationRepository.findTopByPhoneNumberOrderByExpiryDateDesc(
            phoneNumber);

        if (smsVerification == null) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_CODE);
        }

        if (!smsVerification.getVerificationCode().equals(code)) {
            throw new MitubeException(MitubeErrorCode.NOT_MATCH_CODE);
        }

        if (smsVerification.getIsVerified()) {
            throw new MitubeException(MitubeErrorCode.VERIFIED_CODE);
        }

        if (current.isAfter(smsVerification.getExpiryDate())) {
            throw new MitubeException(MitubeErrorCode.EXPIRED_CODE);
        }

        smsVerification.addCurrentFailures();

        smsVerification.setVerified(true);
        smsVerificationRepository.save(smsVerification);

        VerifySMSResponse response = new VerifySMSResponse();
        response.setToken(Base64Convertor.encode(smsVerification.getId()));

        return response;
    }

    public boolean checkVerification(String token) {

        SmsVerification smsVerification = smsVerificationRepository
            .findById(Base64Convertor.decode(token))
            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_SMS_TOKEN));

        return smsVerification.getIsVerified();
    }
}
