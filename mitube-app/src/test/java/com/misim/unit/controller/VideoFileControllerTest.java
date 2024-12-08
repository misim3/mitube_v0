package com.misim.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.misim.controller.VideoFileController;
import com.misim.controller.model.Response.UploadVideosResponse;
import com.misim.entity.VideoFile;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoFileService;
import com.misim.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class VideoFileControllerTest {

    @Mock
    private VideoFileService videoFileService;

    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoFileController videoFileController;

    @Mock
    private MultipartFile mockMultipartFile;

    @Mock
    private VideoFile mockVideoFile;

    private static final Long EXISTING_VIDEO_FILE_ID = 1L;
    private static final Long NON_EXISTENT_VIDEO_FILE_ID = 99999L;

    @Test
    void uploadVideo_shouldReturnResponse() {

        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(videoFileService.uploadVideo(mockMultipartFile)).thenReturn(String.valueOf(EXISTING_VIDEO_FILE_ID));

        CommonResponse<UploadVideosResponse> response = videoFileController.uploadVideo(mockMultipartFile);

        assertThat(response).isNotNull();
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.CREATED);
        verify(mockMultipartFile, times(1)).isEmpty();
        verify(videoFileService, times(1)).uploadVideo(mockMultipartFile);

    }

    @Test
    void uploadVideo_shouldThrowException_whenEmptyFile() {

        when(mockMultipartFile.isEmpty()).thenReturn(true);

        assertThrows(MitubeException.class, () -> videoFileController.uploadVideo(mockMultipartFile));
        verify(mockMultipartFile, times(1)).isEmpty();
        verify(videoFileService, times(0)).uploadVideo(mockMultipartFile);

    }

    @Test
    void uploadVideo_shouldThrowException_whenDirectoryDoesNotCreate() {

        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(videoFileService.uploadVideo(mockMultipartFile)).thenThrow(new MitubeException(MitubeErrorCode.NOT_CREATED_DIR));

        assertThrows(MitubeException.class, () -> videoFileController.uploadVideo(mockMultipartFile));
        verify(mockMultipartFile, times(1)).isEmpty();
        verify(videoFileService, times(1)).uploadVideo(mockMultipartFile);

    }

    @Test
    void uploadVideo_shouldThrowException_whenFileDoesNotCreate() {

        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(videoFileService.uploadVideo(mockMultipartFile)).thenThrow(new MitubeException(MitubeErrorCode.NOT_CREATED_FILE));

        assertThrows(MitubeException.class, () -> videoFileController.uploadVideo(mockMultipartFile));
        verify(mockMultipartFile, times(1)).isEmpty();
        verify(videoFileService, times(1)).uploadVideo(mockMultipartFile);

    }

    @Test
    void deleteVideo_shouldDeleteVideoFile_whenIdExists() {

        doNothing().when(videoFileService).deleteVideoFileById(EXISTING_VIDEO_FILE_ID);

        ResponseEntity<Void> response = videoFileController.deleteVideo(EXISTING_VIDEO_FILE_ID);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(videoFileService, times(1)).deleteVideoFileById(EXISTING_VIDEO_FILE_ID);

    }

    @Test
    void deleteVideo_shouldThrowException_whenIdDoesNotExist() {

        doThrow(new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO_FILE)).when(videoFileService).deleteVideoFileById(NON_EXISTENT_VIDEO_FILE_ID);

        assertThrows(MitubeException.class, () -> videoFileController.deleteVideo(NON_EXISTENT_VIDEO_FILE_ID));

        verify(videoFileService, times(1)).deleteVideoFileById(NON_EXISTENT_VIDEO_FILE_ID);

    }

    @Test
    void deleteVideo_shouldThrowException_whenFileDoesNotExist() {

        doThrow(new MitubeException(MitubeErrorCode.NOT_FOUND_FILE_PATH)).when(videoFileService).deleteVideoFileById(EXISTING_VIDEO_FILE_ID);

        assertThrows(MitubeException.class, () -> videoFileController.deleteVideo(EXISTING_VIDEO_FILE_ID));

        verify(videoFileService, times(1)).deleteVideoFileById(EXISTING_VIDEO_FILE_ID);

    }

    @Test
    void streamVideo_shouldReturnResponse() {

    }

    @Test
    void streamVideo_shouldThrowException_whenIdDoesNotExist() {

    }

}
