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

        when(videoMetadataService.read(EXISTING_VIDEO_METADATA_ID)).thenReturn(metadata);

        CommonResponse<MetadataResponse> response = videoMetadataController.getVideoMetadata(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(metadata.getViewCount(), response.getBody().viewCount());
        assertEquals(metadata.getLikeCount(), response.getBody().likeCount());
        assertEquals(metadata.getDislikeCount(), response.getBody().dislikeCount());
        verify(videoMetadataService, times(1)).read(EXISTING_VIDEO_METADATA_ID);

    }

    @Test
    void getVideoMetadata_shouldThrowException_whenIdDoesNotExist() {

        when(videoMetadataService.read(NON_EXISTENT_VIDEO_METADATA_ID)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.getVideoMetadata(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void addVideoMetadataViewCount_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateViewCount(EXISTING_VIDEO_METADATA_ID);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataViewCount(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateViewCount(EXISTING_VIDEO_METADATA_ID);

    }

    @Test
    void addVideoMetadataViewCount_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateViewCount(NON_EXISTENT_VIDEO_METADATA_ID);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataViewCount(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void addVideoMetadataLikeCountUp_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateLikeCount(EXISTING_VIDEO_METADATA_ID, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(EXISTING_VIDEO_METADATA_ID, true);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateLikeCount(EXISTING_VIDEO_METADATA_ID, true);

    }

    @Test
    void addVideoMetadataLikeCountDown_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateLikeCount(EXISTING_VIDEO_METADATA_ID, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(EXISTING_VIDEO_METADATA_ID, false);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateLikeCount(EXISTING_VIDEO_METADATA_ID, false);

    }

    @Test
    void addVideoMetadataLikeCount_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateLikeCount(NON_EXISTENT_VIDEO_METADATA_ID, true);
        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateLikeCount(NON_EXISTENT_VIDEO_METADATA_ID, false);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataLikeCount(NON_EXISTENT_VIDEO_METADATA_ID, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataLikeCount(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void addVideoMetadataDislikeCountUp_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateDislikeCount(EXISTING_VIDEO_METADATA_ID, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(EXISTING_VIDEO_METADATA_ID, true);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateDislikeCount(EXISTING_VIDEO_METADATA_ID, true);

    }

    @Test
    void addVideoMetadataDislikeCountDown_shouldThrowException_whenIdDoesNotExist() {

        doNothing().when(videoMetadataService).updateDislikeCount(EXISTING_VIDEO_METADATA_ID, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(EXISTING_VIDEO_METADATA_ID, false);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateDislikeCount(EXISTING_VIDEO_METADATA_ID, false);

    }

    @Test
    void addVideoMetadataDislikeCount_shouldThrowException_whenIdExists() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateDislikeCount(NON_EXISTENT_VIDEO_METADATA_ID, true);
        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateDislikeCount(NON_EXISTENT_VIDEO_METADATA_ID, false);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataDislikeCount(NON_EXISTENT_VIDEO_METADATA_ID, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataDislikeCount(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void deleteVideoMetadata_shouldReturnResponse() {

        doNothing().when(videoMetadataService).delete(EXISTING_VIDEO_METADATA_ID);

        CommonResponse<Void> response = videoMetadataController.deleteVideoMetadata(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(response);
        assertEquals(204, response.getCode());
        verify(videoMetadataService, times(1)).delete(EXISTING_VIDEO_METADATA_ID);

    }
}
