package com.misim.controller;

import com.misim.controller.model.Request.ReactionRequest;
import com.misim.controller.model.Response.ReactionResponse;
import com.misim.exception.CommonResponse;
import com.misim.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactions")
public class ReactionController {

    private final ReactionService reactionService;


    @Operation(summary = "동영상 반응", description = "동영상 반응을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 반응 조회 요청 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/{videoId}")
    public CommonResponse<ReactionResponse> getReaction(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId,
        @RequestParam @Parameter(name = "userId", description = "동영상을 시청할 유저의 식별 정보") Long userId) {

        ReactionResponse response = reactionService.getReaction(userId, videoId);

        return CommonResponse
            .<ReactionResponse>builder()
            .body(response)
            .build();
    }

    @Operation(summary = "동영상에 대한 유저의 반응 선택", description = "동영상에 대한 유저의 반응. 좋아요, 싫어요 선택 정보를 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 반응 정보 업데이트 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/{videoId}/check")
    public void checkVideo(@RequestBody ReactionRequest request) {

        request.check();

        reactionService.checking(request.getType(), request.getUserId(), request.getVideoId());
    }

    @Operation(summary = "동영상에 대한 유저의 반응 선택 취소", description = "동영상에 대한 유저의 반응. 좋아요, 싫어요 선택 취소 정보를 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 반응 정보 업데이트 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/{videoId}/uncheck")
    public void uncheckVideo(@RequestBody ReactionRequest request) {

        request.check();

        reactionService.unchecking(request.getType(), request.getUserId(), request.getVideoId());
    }
}
