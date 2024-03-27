package com.misim.controller.model.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SendSMSRequest {

    @Schema(name = "phoneNumber", description = "전화번호", example = "01012345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;
}
