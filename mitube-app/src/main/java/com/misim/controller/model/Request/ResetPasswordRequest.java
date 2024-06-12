package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "비밀번호 초기화 요청 DTO")
public class ResetPasswordRequest implements Checker {

    @Schema(name = "nickname", description = "닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Schema(name = "token", description = "토큰", example = "AIHR==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

    @Override
    public void check() {

        if (this.nickname == null || this.nickname.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        if (this.token == null || this.token.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_SMS_TOKEN);
        }
    }
}
