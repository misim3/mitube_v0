package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@Builder
public class CommentListResponse {

    @Schema(name = "commentResponses", description = "댓글 목록 10개", example = "재밌네요!, 좋아요!, ...", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Slice<CommentResponse> commentResponses;
}
