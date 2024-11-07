package com.misim.controller;

import com.misim.exception.CommonResponse;
import com.misim.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/views")
public class ViewController {

    private final VideoService videoService;

    @Operation(summary = "동영상 조회수 증가", description = "동영상 조회수를 증가합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "동영상 조회수 증가 요청 성공"),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/{videoId}/view")
    public void incrementView(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId) {

        videoService.incrementView(videoId);
    }
}
