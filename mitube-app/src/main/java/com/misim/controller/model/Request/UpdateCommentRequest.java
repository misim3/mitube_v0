package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "댓글 수정 요청 DTO")
public class UpdateCommentRequest implements Checker {

    @Schema(name = "commentId", description = "댓글 식별 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commentId;

    @Schema(name = "content", description = "수정할 댓글 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Override
    public void check() {

        if (commentId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_ID);
        }

        if (content.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_CONTENT);
        }
    }
}
