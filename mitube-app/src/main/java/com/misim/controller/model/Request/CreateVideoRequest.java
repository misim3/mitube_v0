package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "새로운 동영상 생성 요청 DTO")
public class CreateVideoRequest implements Checker {

    @Schema(name = "title", description = "동영상 제목", example = "Hello world!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(name = "description", description = "동영상 설명", example = "프로그래밍의 시작", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(name = "nickname", description = "동영상 소유자 닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Schema(name = "token", description = "동영상 파일 토큰", example = "HAK+==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

    @Schema(name = "categoryId", description = "동영상 카테고리 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer categoryId;

    @Override
    public void check() {

        // null 체크
        if (title == null || title.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_TITLE);
        }

        if (description == null || description.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_DESCRIPTION);
        }

        if (nickname == null || nickname.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        if (token == null || token.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_VIDEO_TOKEN);
        }

        if (categoryId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_CATEGORY);
        }
    }
}
