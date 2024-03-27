package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TermListResponse {

    @Schema(name = "termResponseList", description = "전체 약관 목록", example = "개인 정보 수신 동의 (필수)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private List<TermResponse> termResponseList;
}
