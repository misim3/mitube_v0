package com.misim.controller;

import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.controller.model.Response.WatchingVideoResponse;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/create")
    public void createVideos(@RequestBody CreateVideoRequest createVideoRequest) {

        // 파일 확인
        createVideoRequest.check();

        // 비디오 생성
        videoService.createVideos(createVideoRequest);
    }

    @GetMapping("/{videoId}")
    public CommonResponse<WatchingVideoResponse> startWatchingVideo(@PathVariable Long videoId) {

        WatchingVideoResponse response = videoService.startWatchingVideo(videoId);

        return CommonResponse
            .<WatchingVideoResponse>builder()
            .body(response)
            .build();
    }

    @PostMapping("/{videoId}/update")
    public void watchingVideo(
        @PathVariable Long videoId,
        @RequestParam Long userId,
        @RequestParam Long watchingTime
    ) {

        videoService.updateWatchingVideo(videoId, userId, watchingTime);
    }

    @PostMapping("/{videoId}/complete")
    public void completeWatchingVideo(
        @PathVariable Long videoId,
        @RequestParam Long userId,
        @RequestParam Long watchingTime
    ) {

        videoService.completeWatchingVideo(videoId, userId, watchingTime);
    }
}
