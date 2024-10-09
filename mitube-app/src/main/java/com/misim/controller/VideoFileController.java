package com.misim.controller;

import com.misim.controller.model.Response.UploadVideosResponse;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videofiles")
public class VideoFileController {

    private final VideoService videoService;

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
}
