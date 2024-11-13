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
            .code(200)
            .body(response)
            .build();
    }

    @PostMapping("/{videoMetadataId}/view")
    public CommonResponse<Void> addVideoMetadataViewCount(@PathVariable Long videoMetadataId) {

        videoMetadataService.updateViewCount(videoMetadataId);

        return CommonResponse.<Void>builder()
            .code(201)
            .build();
    }

    @PostMapping("/{videoMetadataId}/like")
    public CommonResponse<Void> addVideoMetadataLikeCount(@PathVariable Long videoMetadataId, @RequestParam Boolean isChecked) {

        videoMetadataService.updateLikeCount(videoMetadataId, isChecked);

        return CommonResponse.<Void>builder()
            .code(201)
            .build();
    }

    @PostMapping("/{videoMetadataId}/dislike")
    public CommonResponse<Void> addVideoMetadataDislikeCount(@PathVariable Long videoMetadataId, @RequestParam Boolean isChecked) {

        videoMetadataService.updateDislikeCount(videoMetadataId, isChecked);

        return CommonResponse.<Void>builder()
            .code(201)
            .build();
    }

    @DeleteMapping("/{videoMetadataId}")
    public CommonResponse<Void> deleteVideoMetadata(@PathVariable Long videoMetadataId) {

        videoMetadataService.delete(videoMetadataId);

        return CommonResponse.<Void>builder()
            .code(204)
            .build();
    }
}
