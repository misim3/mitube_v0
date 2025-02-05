package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "댓글 생성 응답 DTO")
public class CreateCommentResponse {

    @Schema(name = "commentId", description = "생성된 댓글 식별 정보 반환", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commentId;
}
