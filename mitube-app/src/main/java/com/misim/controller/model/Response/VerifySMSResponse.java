package com.misim.controller.model.Response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VerifySMSResponse {

    @Schema(name = "token", description = "SMS 인증 토큰", example = "AHKW+==", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String token;
}
