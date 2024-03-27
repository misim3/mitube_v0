package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.util.Validator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Schema(description = "유저 DTO")
public class SignUpUserRequest implements Checker {

    @Schema(name = "email", description = "User 이메일", example = "hongkildong@example.com", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String email;

    @Schema(name = "password", description = "User 비밀번호", example = "Qwer1234%", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String password;

    @Schema(name = "confirmPassword", description = "User 확인 비밀번호", example = "Qwer1234%", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String confirmPassword;

    @Schema(name = "nickname", description = "User 닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String nickname;

    @Schema(name = "phoneNumber", description = "User 전화번호", example = "01012345678", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String phoneNumber;

    @Schema(name = "code", description = "User 토큰", example = "AIHR==", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String token;

    // 리스트
    @Schema(name = "checkedTerms", description = "체크된 약관 제목", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private List<String> checkedTermTitles;

    @Override
    public void check() {

        // null 체크
        if (email == null || email.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_EMAIL);
        }

        if (password == null || password.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_PASSWORD);
        }

        if (confirmPassword == null || confirmPassword.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_CONFIRM_PASSWORD);
        }

        if (nickname == null || nickname.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        if (token == null || token.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_SMS_TOKEN);
        }

        // 사이즈 체크
        if (!(!email.isEmpty() && email.length() <= 32)) {
            throw new MitubeException(MitubeErrorCode.INVALID_EMAIL);
        }

        if (!(password.length() >= 8 && password.length() <= 15)) {
            throw new MitubeException(MitubeErrorCode.INVALID_PASSWORD);
        }

        if (!(confirmPassword.length() >= 8 && confirmPassword.length() <= 15)) {
            throw new MitubeException(MitubeErrorCode.INVALID_CONFIRM_PASSWORD);
        }

        if (!(!nickname.isEmpty() && nickname.length() <= 20)) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        // 패턴 체크
        Validator.validateEmail(email);
        Validator.validatePassword(password);
        Validator.validatePassword(confirmPassword);
        Validator.matchPassword(password, confirmPassword);
    }
}
