package com.misim.controller.model.Request;

import com.misim.controller.model.VerificationDto;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "비밀번호 초기화 요청 DTO")
public class ResetPasswordRequest extends VerificationDto {

    @Schema(name = "nickname", description = "닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Override
    public void check() {

        if (nickname == null || nickname.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        if (getCode() == null || getCode().isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }
    }
}
