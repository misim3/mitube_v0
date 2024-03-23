package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import lombok.Data;

@Data
public class CreateCommentRequest implements Checker {

    private String content;

    private Long videoId;

    private Long userId;

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
