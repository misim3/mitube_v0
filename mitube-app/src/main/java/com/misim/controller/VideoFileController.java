package com.misim.controller;

import com.misim.controller.model.Response.UploadVideosResponse;
import com.misim.entity.VideoCatalog;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoFileService;
import com.misim.service.VideoService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videofiles")
public class VideoFileController {

    private final VideoService videoService;
    private final VideoFileService videoFileService;

    public VideoFileController(VideoService videoService, VideoFileService videoFileService) {
        this.videoService = videoService;
        this.videoFileService = videoFileService;
    }

    // videofiles/upload -> videos/create -> videofils/{videoId} 전체 과정 테스트 통과
    @PostMapping("/upload")
    public CommonResponse<UploadVideosResponse> uploadVideo(@RequestPart MultipartFile file) {

        // 파일 검사
        checkFile(file);

        // 비디오 업로드
        String id = videoFileService.uploadVideo(file);

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

    @DeleteMapping("/{videofileId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long videofileId) {

        videoFileService.deleteVideoFileById(videofileId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Resource> streamVideo(@PathVariable Long videoId) {

        VideoCatalog videoCatalog = videoService.getVideo(videoId);

        Resource resource = videoFileService.getFileResource(videoCatalog.getVideoFile().getPath());

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(resource);
    }
}
