package com.misim.controller;

import com.misim.controller.model.Response.MetadataResponse;
import com.misim.entity.VideoMetadata;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoMetadataService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videoMetadata")
public class VideoMetadataController {

    private final VideoMetadataService videoMetadataService;

    public VideoMetadataController(VideoMetadataService videoMetadataService) {
        this.videoMetadataService = videoMetadataService;
    }

    @GetMapping("/{videoMetadataId}")
    public CommonResponse<MetadataResponse> getVideoMetadata(@PathVariable Long videoMetadataId) {

        VideoMetadata metadata = videoMetadataService.read(videoMetadataId);

        MetadataResponse response = new MetadataResponse(metadata.getViewCount(), metadata.getLikeCount(), metadata.getDislikeCount());

        return CommonResponse
            .<MetadataResponse>builder()
            .body(response)
            .build();
    }

    @PostMapping("/{videoMetadataId}/view")
    public void addVideoMetadataViewCount(@PathVariable Long videoMetadataId) {

        videoMetadataService.updateViewCount(videoMetadataId);
    }

    @PostMapping("/{videoMetadataId}/like")
    public void addVideoMetadataLikeCount(@PathVariable Long videoMetadataId, @RequestParam Boolean isChecked) {

        videoMetadataService.updateLikeCount(videoMetadataId, isChecked);
    }

    @PostMapping("/{videoMetadataId}/dislike")
    public void addVideoMetadataDislikeCount(@PathVariable Long videoMetadataId, @RequestParam Boolean isChecked) {

        videoMetadataService.updateDislikeCount(videoMetadataId, isChecked);
    }

    @DeleteMapping("/{videoMetadataId}")
    public void deleteVideoMetadata(@PathVariable Long videoMetadataId) {

        videoMetadataService.delete(videoMetadataId);
    }
}
