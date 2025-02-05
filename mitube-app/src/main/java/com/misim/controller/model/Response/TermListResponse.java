package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TermListResponse {

    @Schema(name = "termResponseList", description = "전체 약관 목록", example = "개인 정보 수신 동의 (필수)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TermResponse> termResponseList;
}
