package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Slice;

@Data
@Builder
public class StartWatchingVideoResponse {

    @Schema(name = "watchingTime", description = "동영상 시청 시각. 동영상 시청한 기록이 없다면, 0. 있다면, 시청 시각 반환", example = "1234", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long watchingTime;

    @Schema(name = "commentListResponse", description = "댓글 목록", example = "...", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private CommentListResponse commentListResponse;

    @Schema(name = "views", description = "동영상 시청 횟수", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long views;
}
