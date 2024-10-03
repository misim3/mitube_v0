package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "동영상 정보 응답 DTO")
public class WatchingVideoResponse {

    @Schema(name = "title", description = "영상 제목", example = "hello, world!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(name = "description", description = "영상 설명", example = "this video is ~.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(name = "views", description = "영상 시청 횟수", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long views;

    @Schema(name = "modifiedDate", description = "영상 생성 날짜", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createdDate;

    // 비디오 전용 스토리지
    private String videoLink;

}
