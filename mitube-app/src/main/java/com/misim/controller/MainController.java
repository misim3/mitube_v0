package com.misim.controller;

import com.misim.controller.model.Response.VideoResponse;
import com.misim.entity.VideoCategory;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class MainController {

    private final VideoService videoService;

    @Operation(summary = "카테고리 목록 전송", description = "메인 화면에 필요한 카테고리 목록 전송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "카테고리 목록 전송 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/category")
    public CommonResponse<List<String>> getCategory() {

        List<String> response = VideoCategory.getCategoryList();

        return CommonResponse
            .<List<String>>builder()
            .body(response)
            .build();
    }

    @Operation(summary = "인기 영상 목록 전송", description = "메인 화면에 필요한 인기 영상 목록 전송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "인기 영상 목록 전송 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/hot")
    public CommonResponse<List<VideoResponse>> getHotVideo() {

        List<VideoResponse> response = videoService.getHotVideos();

        return CommonResponse
            .<List<VideoResponse>>builder()
            .body(response)
            .build();
    }

    @Operation(summary = "새 영상 목록 전송", description = "메인 화면에 필요한 새 영상 목록 전송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "새 영상 목록 전송 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/new")
    public CommonResponse<List<VideoResponse>> getNewVideo() {

        List<VideoResponse> response = videoService.getNewVideos();

        return CommonResponse
            .<List<VideoResponse>>builder()
            .body(response)
            .build();
    }

    @Operation(summary = "시청 중인 영상 목록 전송", description = "메인 화면에 필요한 시청 중인 영상 목록 전송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "시청 중인 영상 목록 전송 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/watching")
    public CommonResponse<List<VideoResponse>> getWatchingVideo(
        @RequestParam @Parameter(name = "userId", description = "Mitube에 접속한 유저 식별 정보로, 비로그인 사용자의 경우 null로 요청된다.") Long userId) {

        List<VideoResponse> response = videoService.getWatchingVideos(userId);

        return CommonResponse
            .<List<VideoResponse>>builder()
            .body(response)
            .build();
    }

    @Operation(summary = "구독 채널의 신규 영상 목록 전송", description = "메인 화면에 필요한 구독 채널의 신규 영상 목록 전송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "구독 채널의 신규 영상 목록 전송 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/subscribing")
    public CommonResponse<List<VideoResponse>> getChannelVideo(
        @RequestParam @Parameter(name = "userId", description = "Mitube에 접속한 유저 식별 정보로, 비로그인 사용자의 경우 null로 요청된다.") Long userId) {

        List<VideoResponse> response = videoService.getSubscribingChannelNewVideos(userId);

        return CommonResponse
            .<List<VideoResponse>>builder()
            .body(response)
            .build();
    }
}
