package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermDetailResponse extends TermResponse {

    @Schema(name = "content", description = "약관 내용", example = "개인 정보 보호법 ...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Builder(builderMethodName = "detailBuidler")
    public TermDetailResponse(String title, Boolean isRequired, String content) {
        super(title, isRequired);
        this.content = content;
    }
}