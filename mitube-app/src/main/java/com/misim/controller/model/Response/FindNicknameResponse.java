package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "닉네임 찾기 응답 DTO")
public class FindNicknameResponse {

    @Schema(name = "nickname", description = "닉네임 찾기 결과인 닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;
}
