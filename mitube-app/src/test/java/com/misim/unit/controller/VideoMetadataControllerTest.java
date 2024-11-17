package com.misim.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.misim.controller.VideoMetadataController;
import com.misim.controller.model.Response.MetadataResponse;
import com.misim.entity.VideoMetadata;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoMetadataService;
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
    void getVideoMetadata() {

        when(videoMetadataService.read(videoMetadataId)).thenReturn(metadata);

        CommonResponse<MetadataResponse> response = videoMetadataController.getVideoMetadata(videoMetadataId);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(metadata.getViewCount(), response.getBody().viewCount());
        assertEquals(metadata.getLikeCount(), response.getBody().likeCount());
        assertEquals(metadata.getDislikeCount(), response.getBody().dislikeCount());

    }

    @Test
    void addVideoMetadataViewCount() {

        doNothing().when(videoMetadataService).updateViewCount(videoMetadataId);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataViewCount(videoMetadataId);

        assertNotNull(response);
        assertEquals(201, response.getCode());

    }

    @Test
    void addVideoMetadataLikeCount_up() {

        doNothing().when(videoMetadataService).updateLikeCount(videoMetadataId, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(videoMetadataId, true);

        assertNotNull(response);
        assertEquals(201, response.getCode());

    }

    @Test
    void addVideoMetadataLikeCount_down() {

        doNothing().when(videoMetadataService).updateLikeCount(videoMetadataId, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataLikeCount(videoMetadataId, false);

        assertNotNull(response);
        assertEquals(201, response.getCode());

    }

    @Test
    void addVideoMetadataDislikeCount_up() {

        doNothing().when(videoMetadataService).updateDislikeCount(videoMetadataId, true);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(videoMetadataId, true);

        assertNotNull(response);
        assertEquals(201, response.getCode());

    }

    @Test
    void addVideoMetadataDislikeCount_down() {

        doNothing().when(videoMetadataService).updateDislikeCount(videoMetadataId, false);

        CommonResponse<Void> response = videoMetadataController.addVideoMetadataDislikeCount(videoMetadataId, false);

        assertNotNull(response);
        assertEquals(201, response.getCode());

    }

    @Test
    void deleteVideoMetadata() {

        doNothing().when(videoMetadataService).delete(videoMetadataId);

        CommonResponse<Void> response = videoMetadataController.deleteVideoMetadata(videoMetadataId);

        assertNotNull(response);
        assertEquals(204, response.getCode());
    }
}
