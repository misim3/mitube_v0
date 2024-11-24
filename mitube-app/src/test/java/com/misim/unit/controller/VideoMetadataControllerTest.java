package com.misim.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.misim.controller.VideoMetadataController;
import com.misim.controller.model.Response.MetadataResponse;
import com.misim.entity.VideoMetadata;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoMetadataService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class VideoMetadataControllerTest {

    @Mock
    private VideoMetadataService videoMetadataService;

    @InjectMocks
    private VideoMetadataController videoMetadataController;

    private VideoMetadata metadata;

    private static final Long EXISTING_VIDEO_METADATA_ID = 1L;
    private static final Long NON_EXISTENT_VIDEO_METADATA_ID = 99999L;

    @BeforeEach
    void setUp() {
        metadata = VideoMetadata.builder()
            .viewCount(0L)
            .likeCount(0L)
            .dislikeCount(0L)
            .build();
    }

    @Test
    void getVideoMetadata_shouldReturnResponse_whenIdExists() {

        when(videoMetadataService.readVideoMetadataById(EXISTING_VIDEO_METADATA_ID)).thenReturn(metadata);

        CommonResponse<MetadataResponse> response = videoMetadataController.getVideoMetadata(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals(metadata.getViewCount(), response.getBody().viewCount());
        assertEquals(metadata.getLikeCount(), response.getBody().likeCount());
        assertEquals(metadata.getDislikeCount(), response.getBody().dislikeCount());
        verify(videoMetadataService, times(1)).readVideoMetadataById(EXISTING_VIDEO_METADATA_ID);

    }

    @Test
    void getVideoMetadata_shouldThrowException_whenIdDoesNotExist() {

        when(videoMetadataService.readVideoMetadataById(NON_EXISTENT_VIDEO_METADATA_ID)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.getVideoMetadata(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void addVideoMetadataViewCount_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateViewCountById(EXISTING_VIDEO_METADATA_ID);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataViewCount(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        verify(videoMetadataService, times(1)).updateViewCountById(EXISTING_VIDEO_METADATA_ID);

    }

    @Test
    void addVideoMetadataViewCount_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateViewCountById(NON_EXISTENT_VIDEO_METADATA_ID);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataViewCount(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void addVideoMetadataLikeCountUp_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateLikeCountById(EXISTING_VIDEO_METADATA_ID, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(EXISTING_VIDEO_METADATA_ID, true);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        verify(videoMetadataService, times(1)).updateLikeCountById(EXISTING_VIDEO_METADATA_ID, true);

    }

    @Test
    void addVideoMetadataLikeCountDown_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateLikeCountById(EXISTING_VIDEO_METADATA_ID, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(EXISTING_VIDEO_METADATA_ID, false);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        verify(videoMetadataService, times(1)).updateLikeCountById(EXISTING_VIDEO_METADATA_ID, false);

    }

    @Test
    void addVideoMetadataLikeCountUp_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataLikeCount(NON_EXISTENT_VIDEO_METADATA_ID, true));

    }

    @Test
    void addVideoMetadataLikeCountDown_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataLikeCount(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void addVideoMetadataDislikeCountUp_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(EXISTING_VIDEO_METADATA_ID, true);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        verify(videoMetadataService, times(1)).updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, true);

    }

    @Test
    void addVideoMetadataDislikeCountDown_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(EXISTING_VIDEO_METADATA_ID, false);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        verify(videoMetadataService, times(1)).updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, false);

    }

    @Test
    void addVideoMetadataDislikeCountUp_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataDislikeCount(NON_EXISTENT_VIDEO_METADATA_ID, true));

    }

    @Test
    void addVideoMetadataDislikeCountDown_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataDislikeCount(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void deleteVideoMetadata_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).deleteById(EXISTING_VIDEO_METADATA_ID);

        CommonResponse<Void> response = videoMetadataController.deleteVideoMetadata(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getHttpStatus());
        verify(videoMetadataService, times(1)).deleteById(EXISTING_VIDEO_METADATA_ID);

    }

    @Test
    void deleteVideoMetadata_shouldReturnResponse_whenIdDoesNotExist() {

        doNothing().when(videoMetadataService).deleteById(NON_EXISTENT_VIDEO_METADATA_ID);

        CommonResponse<Void> response = videoMetadataController.deleteVideoMetadata(NON_EXISTENT_VIDEO_METADATA_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getHttpStatus());
        verify(videoMetadataService, times(1)).deleteById(NON_EXISTENT_VIDEO_METADATA_ID);

    }
}
