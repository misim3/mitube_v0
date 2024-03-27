package com.misim.controller.model.Response;

import com.misim.entity.Video;
import com.misim.entity.VideoCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoResponse {

    @Schema(name = "videoId", description = "동영상 식별 정보", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long videoId;

    @Schema(name = "title", description = "동영상 제목", example = "천만 영화 <파묘> 해석", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String title;

    @Schema(name = "description", description = "동영상 설명", example = "영화 <파묘> 리뷰", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String description;

    @Schema(name = "category", description = "동영상의 카테고리 이름", example = "영화", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String category;

    @Schema(name = "userId", description = "동영상 소유주 식별 정보", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long userId;

    @Schema(name = "videoUrl", description = "동영상 파일 경로", example = "", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String videoUrl;

    @Schema(name = "thumbnailUrl", description = "썸네일 파일 경로", example = "", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String thumbnailUrl;

    @Schema(name = "views", description = "동영상 총 시청 횟수", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long views;

    public static List<VideoResponse> convertVideos(List<Video> videos) {
        return videos.stream()
            .map(video -> VideoResponse.builder()
                    .videoId(video.getId())
                    .title(video.getTitle())
                    .description(video.getDescription())
                    .userId(video.getUser().getId())
                    .category(VideoCategory.getNameByCode(video.getCategoryId()))
                    .videoUrl(video.getVideoFile().getPath())
                    .views(video.getViews())
                    .thumbnailUrl(video.getThumbnailUrl())
                    .build())
            .toList();
    }
}
