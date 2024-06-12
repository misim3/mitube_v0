package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "동영상 시청 시작 정보 응답 DTO")
public class StartWatchingVideoResponse {

    @Schema(name = "videoId", description = "동영상 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long videoId;

    @Schema(name = "watchingTime", description = "동영상 시청 시각. 동영상 시청한 기록이 없다면, 0. 있다면, 시청 시각 반환", example = "1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long watchingTime;

    @Schema(name = "commentListResponse", description = "댓글 목록", example = "...", requiredMode = Schema.RequiredMode.REQUIRED)
    private CommentListResponse commentListResponse;

    @Schema(name = "views", description = "동영상 시청 횟수", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long views;

    // 비디오 전용 스토리지
    private String videoLink;

    @Schema(name = "reactionResponse", description = "유저의 동영상에 대한 반응으로 로그인 상태가 아닐 시 null 값을 가진다.", example = "null", requiredMode = Schema.RequiredMode.REQUIRED)
    private ReactionResponse reactionResponse;
}
