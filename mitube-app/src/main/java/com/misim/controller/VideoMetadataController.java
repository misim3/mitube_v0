package com.misim.controller;

import com.misim.controller.model.Response.MetadataResponse;
import com.misim.entity.VideoMetadata;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoMetadataService;
import org.springframework.http.HttpStatus;
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

        VideoMetadata metadata = videoMetadataService.readVideoMetadataById(videoMetadataId);

        MetadataResponse response = new MetadataResponse(metadata.getViewCount(), metadata.getLikeCount(), metadata.getDislikeCount());

        return CommonResponse
            .<MetadataResponse>builder()
            .httpStatus(HttpStatus.OK)
            .body(response)
            .build();
    }

    @PostMapping("/{videoMetadataId}/view")
    public CommonResponse<Void> addVideoMetadataViewCount(@PathVariable Long videoMetadataId) {

        videoMetadataService.updateViewCountById(videoMetadataId);

        return CommonResponse.<Void>builder()
            .httpStatus(HttpStatus.CREATED)
            .build();
    }

    @PostMapping("/{videoMetadataId}/like")
    public CommonResponse<Void> addVideoMetadataLikeCount(@PathVariable Long videoMetadataId, @RequestParam Boolean checked) {

        videoMetadataService.updateLikeCountById(videoMetadataId, checked);

        return CommonResponse.<Void>builder()
            .httpStatus(HttpStatus.CREATED)
            .build();
    }

    @PostMapping("/{videoMetadataId}/dislike")
    public CommonResponse<Void> addVideoMetadataDislikeCount(@PathVariable Long videoMetadataId, @RequestParam Boolean checked) {

        videoMetadataService.updateDislikeCountById(videoMetadataId, checked);

        return CommonResponse.<Void>builder()
            .httpStatus(HttpStatus.CREATED)
            .build();
    }

    @DeleteMapping("/{videoMetadataId}")
    public CommonResponse<Void> deleteVideoMetadata(@PathVariable Long videoMetadataId) {

        videoMetadataService.deleteMetadataById(videoMetadataId);

        return CommonResponse.<Void>builder()
            .httpStatus(HttpStatus.NO_CONTENT)
            .build();
    }
}
