package com.misim.service;

import com.misim.controller.model.SmsVerificationDto;
import com.misim.entity.SmsVerification;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.SmsVerificationRespository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsVerificationRespository smsVerificationRespository;

    @Value(value = "${coolsms.api-key}")
    private String apiKey;
    @Value(value = "${coolsms.api-secret-key}")
    private String apiSecretKey;
    @Value(value = "${coolsms.api-url}")
    private String apiUrl;
    @Value(value = "${coolsms.from-phonenumber}")
    private String fromPhoneNumber;

    public void sendSMS(String phoneNumber) {

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

                SmsVerification smsVerification = SmsVerification.builder()
                        .phoneNumber(phoneNumber)
                        .verificationCode(code)
                        .build();

                smsVerificationRespository.save(smsVerification);
            }
        }
        
        // 에러처리
    }

    public String makeCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public String matchSMS(SmsVerificationDto smsVerificationDto, LocalDateTime current) {

        SmsVerification smsVerification = smsVerificationRespository.findTopByPhoneNumberAndVerificationCodeOrderByExpiryDate(smsVerificationDto.getPhoneNumber(), smsVerificationDto.getCode());

        if (smsVerification == null) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_CODE);
        }

        if (smsVerification.getIsVerified()) {
            throw new MitubeException(MitubeErrorCode.VERIFIED_CODE);
        }

        if (current.isAfter(smsVerification.getExpiryDate())) {
            throw new MitubeException(MitubeErrorCode.EXPIRED_CODE);
        }

        smsVerification.setVerified(true);
        smsVerificationRespository.save(smsVerification);

        smsVerification.getId();

        return ;
    }
}
