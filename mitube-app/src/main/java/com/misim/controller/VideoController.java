package com.misim.controller;

import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.controller.model.Response.WatchingVideoResponse;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "동영상 API", description = "동영상 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @Operation(summary = "동영상 생성", description = "새로운 동영상을 생성합니다.")
    @Parameter(name = "VideoDto", description = "Video 생성을 위한 데이터")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 생성 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/create")
    public void createVideos(@RequestBody CreateVideoRequest createVideoRequest) {

        // 파일 확인
        createVideoRequest.check();

        // 비디오 생성
        videoService.createVideos(createVideoRequest);
    }

    @Operation(summary = "동영상 시청", description = "동영상 시청을 시작합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 시청 요청 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/watch/{videoId}")
    public CommonResponse<WatchingVideoResponse> startWatchingVideo(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId) {

        WatchingVideoResponse response = videoService.startWatchingVideo(videoId);

        return CommonResponse
            .<WatchingVideoResponse>builder()
            .body(response)
            .build();
    }

    @Operation(summary = "동영상 시청 중", description = "유저가 동영상을 계속 시청 중인지 확인하여 동영상 시청 정보를 업데이트합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 시청 정보 업데이트 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/watch/{videoId}/update")
    public void watchingVideo(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId,
        @RequestParam @Parameter(name = "userId", description = "동영상을 시청할 유저의 식별 정보") Long userId,
        @RequestParam Long watchingTime) {

        videoService.updateWatchingVideo(videoId, userId, watchingTime);
    }

    @Operation(summary = "동영상 시청 완료", description = "동영상 시청 완료로 시청 정보를 업데이트합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 시청 정보 업데이트 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/watch/{videoId}/complete")
    public void completeWatchingVideo(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId,
        @RequestParam @Parameter(name = "userId", description = "동영상을 시청할 유저의 식별 정보") Long userId,
        @RequestParam Long watchingTime) {

        videoService.completeWatchingVideo(videoId, userId, watchingTime);
    }
}
