package com.misim.controller.model.Response;

import com.misim.entity.VideoCatalog;
import com.misim.entity.VideoCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "동영상 정보 응답 DTO")
public class VideoResponse {

    @Schema(name = "videoId", description = "동영상 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long videoId;

    @Schema(name = "title", description = "동영상 제목", example = "천만 영화 <파묘> 해석", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(name = "description", description = "동영상 설명", example = "영화 <파묘> 리뷰", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(name = "category", description = "동영상의 카테고리 이름", example = "영화", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @Schema(name = "videoUrl", description = "동영상 파일 경로", requiredMode = Schema.RequiredMode.REQUIRED)
    private String videoUrl;

    public static List<VideoResponse> convertVideos(List<VideoCatalog> videoCatalogs) {
        return videoCatalogs.stream()
            .map(video -> VideoResponse.builder()
                .videoId(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .category(VideoCategory.getNameByCode(video.getCategoryId()))
                .videoUrl(video.getVideoFile().getPath())
                .build())
            .toList();
    }
}
