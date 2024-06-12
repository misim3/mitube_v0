package com.misim.controller.model;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentDto implements Checker {

    @Schema(name = "commentId", description = "댓글 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commentId;

    @Override
    public void check() {

        if (commentId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_ID);
        }
    }
}
