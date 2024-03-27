package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "약관 정보 반환 DTO")
public class TermResponse {

    @Schema(name = "title", description = "약관 제목", example = "개인 정보 수신 동의", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String title;

    @Schema(name = "isRequired", description = "필수 약관 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Boolean isRequired;

    @Builder
    public TermResponse(String title, Boolean isRequired) {
        this.title = title;
        this.isRequired = isRequired;
    }
}
