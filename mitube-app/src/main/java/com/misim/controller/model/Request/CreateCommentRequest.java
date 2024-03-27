package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCommentRequest implements Checker {

    @Schema(name = "content", description = "댓글 내용", example = "프로그래밍 공부에 도움이 됩니다!", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String content;

    @Schema(name = "videoId", description = "댓글을 생성할 동영상 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long videoId;

    @Schema(name = "userId", description = "댓글 작성자 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long userId;

    @Schema(name = "parentCommentId", description = "대댓글의 경우, 댓글의 식별 정보", example = "2", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long parentCommentId;

    @Override
    public void check() {

        if (content == null || content.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_CONTENT);
        }

        if (videoId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_VIDEO);
        }

        if (userId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_VIDEO);
        }
    }
}
