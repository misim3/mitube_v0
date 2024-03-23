package com.misim.controller.model.Response;

import com.misim.entity.Video;
import com.misim.entity.VideoCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoResponse {

    private Long videoId;

    private String title;

    private String description;

    private String category;

    private Long userId;

    private String videoUrl;

    private String thumbnailUrl;

    // 시청 중인 유저 정보 - 유저 닉네임과 동영상 시청 시각

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
