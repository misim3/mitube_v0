package com.misim.controller;

import com.misim.controller.model.Request.ReactionRequest;
import com.misim.controller.model.Response.CommentListResponse;
import com.misim.controller.model.Response.StartWatchingVideoResponse;
import com.misim.controller.model.Response.UploadVideosResponse;
import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.CommentService;
import com.misim.service.ReactionService;
import com.misim.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "동영상 API", description = "동영상 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final CommentService commentService;
    private final ReactionService reactionService;

    @Operation(summary = "동영상 업로드", description = "새로운 동영상을 업로드합니다.")
    @Parameter(name = "MultipartFile", description = "MultipartFile 형식의 동영상 데이터")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 업로드 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/upload")
    public CommonResponse<UploadVideosResponse> uploadVideos(@RequestPart MultipartFile file) {

        // 파일 검사
        checkFile(file);

        // 비디오 업로드
        String id = videoService.uploadVideos(file);

        UploadVideosResponse uploadVideosResponse = new UploadVideosResponse();
        uploadVideosResponse.setId(id);

        return CommonResponse
            .<UploadVideosResponse>builder()
            .body(uploadVideosResponse)
            .build();
    }

    private void checkFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new MitubeException(MitubeErrorCode.EMPTY_FILE);
        }
    }

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

    @Operation(summary = "동영상 시청 시작", description = "동영상 시청을 시작합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 시청 시작 요청 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/watch/{videoId}")
    public CommonResponse<StartWatchingVideoResponse> startWatchingVideo(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId,
        @RequestParam @Parameter(name = "userId", description = "동영상을 시청할 유저의 식별 정보") Long userId) {

        StartWatchingVideoResponse response = videoService.startWatchingVideo(videoId, userId);

        CommentListResponse commentListResponse = commentService.getParentComments(videoId, null,
            "down");

        response.setCommentListResponse(commentListResponse);

        return CommonResponse
            .<StartWatchingVideoResponse>builder()
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

        videoService.updateWatchingVideoInfo(videoId, userId, watchingTime);
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

        videoService.updateWatchingVideoInfo(videoId, userId, watchingTime);
    }

    @Operation(summary = "동영상에 대한 유저의 반응 선택", description = "동영상에 대한 유저의 반응. 좋아요, 싫어요 선택 정보를 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 반응 정보 업데이트 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/check/reaction")
    public void checkVideo(@RequestBody ReactionRequest request) {

        request.check();

        reactionService.checking(request.getType(), request.getUserId(), request.getVideoId());
    }

    @Operation(summary = "동영상에 대한 유저의 반응 선택 취소", description = "동영상에 대한 유저의 반응. 좋아요, 싫어요 선택 취소 정보를 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 반응 정보 업데이트 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/uncheck/reaction")
    public void uncheckVideo(@RequestBody ReactionRequest request) {

        request.check();

        reactionService.unchecking(request.getType(), request.getUserId(), request.getVideoId());
    }
}
