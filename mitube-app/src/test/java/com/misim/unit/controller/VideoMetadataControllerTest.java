package com.misim.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        when(videoMetadataService.read(1L)).thenReturn(metadata);

        CommonResponse<MetadataResponse> response = videoMetadataController.getVideoMetadata(1L);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(metadata.getViewCount(), response.getBody().viewCount());
        assertEquals(metadata.getLikeCount(), response.getBody().likeCount());
        assertEquals(metadata.getDislikeCount(), response.getBody().dislikeCount());

    }
}
