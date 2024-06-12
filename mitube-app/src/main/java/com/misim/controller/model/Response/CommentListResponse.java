package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(name = "댓글 목록 정보 응답 DTO")
public class CommentListResponse {

    @Schema(name = "commentResponses", description = "댓글 목록 10개", example = "재밌네요!, 좋아요!, ...", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CommentResponse> commentResponses;

    @Schema(name = "hasNext", description = "다음 페이지가 존재하는지 true or false", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean hasNext;
}
