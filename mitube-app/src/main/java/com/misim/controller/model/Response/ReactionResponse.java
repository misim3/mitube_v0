package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "유저의 동영상에 대한 반응 응답 DTO")
public class ReactionResponse {

    @Schema(name = "type", description = "유저의 반응으로 like, dislike, null 값이 가능하다.", example = "like", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;
}
