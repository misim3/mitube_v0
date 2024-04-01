package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import lombok.Data;

@Data
public class ReactionRequest implements Checker {

    private String type;

    private Long userId;

    private Long videoId;

    @Override
    public void check() {

        if (type == null || type.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_REACTION_TYPE);
        }

        if (userId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_REACTION_USER);
        }

        if (videoId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_REACTION_VIDEO);
        }
    }
}
