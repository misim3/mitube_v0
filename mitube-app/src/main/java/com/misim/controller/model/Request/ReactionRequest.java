package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "반응 요청 DTO")
public class ReactionRequest implements Checker {

    @Schema(name = "type", description = "동영상에 대한 유저의 반응 타입으로 like, dislike가 가능합니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(name = "userId", description = "동영상에 반응한 유저 식별 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(name = "videoId", description = "유저가 반응한 동영상 식별 정보", requiredMode = Schema.RequiredMode.REQUIRED)
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
