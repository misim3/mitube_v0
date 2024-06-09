package com.misim.controller.model.Response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SMS 본인 인증 검증 결과 응답 DTO")
public class VerifySMSResponse {

    @Schema(name = "video_token", description = "SMS 인증 토큰", example = "AHKW+==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;
}
