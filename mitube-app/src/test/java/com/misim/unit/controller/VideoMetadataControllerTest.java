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

    private final Long videoMetadataId = 1L;

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

        when(videoMetadataService.read(videoMetadataId)).thenReturn(metadata);

        CommonResponse<MetadataResponse> response = videoMetadataController.getVideoMetadata(videoMetadataId);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(metadata.getViewCount(), response.getBody().viewCount());
        assertEquals(metadata.getLikeCount(), response.getBody().likeCount());
        assertEquals(metadata.getDislikeCount(), response.getBody().dislikeCount());
        verify(videoMetadataService, times(1)).read(videoMetadataId);

    }

    @Test
    void getVideoMetadata_shouldThrowException_whenIdDoesNotExist() {

        when(videoMetadataService.read(99999L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.getVideoMetadata(99999L));

    }

    @Test
    void addVideoMetadataViewCount_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateViewCount(videoMetadataId);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataViewCount(videoMetadataId);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateViewCount(videoMetadataId);

    }

    @Test
    void addVideoMetadataViewCount_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateViewCount(99999L);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataViewCount(99999L));

    }

    @Test
    void addVideoMetadataLikeCountUp_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateLikeCount(videoMetadataId, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(videoMetadataId, true);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateLikeCount(videoMetadataId, true);

    }

    @Test
    void addVideoMetadataLikeCountDown_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateLikeCount(videoMetadataId, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(videoMetadataId, false);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateLikeCount(videoMetadataId, false);

    }

    @Test
    void addVideoMetadataLikeCount_shouldThrowException_whenIdDoesNotExist() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateLikeCount(99999L, true);
        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateLikeCount(99999L, false);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataLikeCount(99999L, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataLikeCount(99999L, false));

    }

    @Test
    void addVideoMetadataDislikeCountUp_shouldReturnResponse_whenIdExists() {

        doNothing().when(videoMetadataService).updateDislikeCount(videoMetadataId, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(videoMetadataId, true);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateDislikeCount(videoMetadataId, true);

    }

    @Test
    void addVideoMetadataDislikeCountDown_shouldThrowException_whenIdDoesNotExist() {

        doNothing().when(videoMetadataService).updateDislikeCount(videoMetadataId, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(videoMetadataId, false);

        assertNotNull(response);
        assertEquals(201, response.getCode());
        verify(videoMetadataService, times(1)).updateDislikeCount(videoMetadataId, false);

    }

    @Test
    void addVideoMetadataDislikeCount_shouldThrowException_whenIdExists() {

        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateDislikeCount(99999L, true);
        doThrow(EntityNotFoundException.class).when(videoMetadataService).updateDislikeCount(99999L, false);

        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataDislikeCount(99999L, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataController.addVideoMetadataDislikeCount(99999L, false));

    }

    @Test
    void deleteVideoMetadata_shouldReturnResponse() {

        doNothing().when(videoMetadataService).delete(videoMetadataId);

        CommonResponse<Void> response = videoMetadataController.deleteVideoMetadata(videoMetadataId);

        assertNotNull(response);
        assertEquals(204, response.getCode());
        verify(videoMetadataService, times(1)).delete(videoMetadataId);

    }
}
