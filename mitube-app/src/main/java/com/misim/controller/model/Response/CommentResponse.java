package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "댓글 정보 응답 DTO")
public class CommentResponse {

    @Schema(name = "commentId", description = "댓글 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commentId;

    @Schema(name = "content", description = "댓글 내용", example = "재밌네요!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(name = "writerNickname", description = "댓글 작성자 닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED)
    private String writerNickname;
}
