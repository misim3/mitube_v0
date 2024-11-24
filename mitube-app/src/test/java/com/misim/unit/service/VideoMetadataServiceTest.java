package com.misim.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.misim.entity.VideoMetadata;
import com.misim.repository.VideoMetadataRepository;
import com.misim.service.VideoMetadataService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VideoMetadataServiceTest {

    @Mock
    private VideoMetadataRepository videoMetadataRepository;

    @InjectMocks
    private VideoMetadataService videoMetadataService;

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
    void create_NewVideoMetadata_shouldSaveAndReturnMetadata() {

        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        VideoMetadata result = videoMetadataService.createNewVideoMetadata();

        assertNotNull(result);
        assertEquals(0L, result.getViewCount());
        assertEquals(0L, result.getLikeCount());
        assertEquals(0L, result.getDislikeCount());
        verify(videoMetadataRepository).save(any(VideoMetadata.class));

    }

    @Test
    void read_ById_shouldReturnMetadata_whenIdExists() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));

        VideoMetadata result = videoMetadataService.readById(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(result);
        verify(videoMetadataRepository).findById(EXISTING_VIDEO_METADATA_ID);

    }

    @Test
    void read_ById_shouldThrowException_whenIdNotExists() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.readById(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void updateViewCount_shouldIncrementViewCountById() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateViewCountById(EXISTING_VIDEO_METADATA_ID);

        assertEquals(1L, metadata.getViewCount());
        verify(videoMetadataRepository).save(metadata);

    }

    @Test
    void updateViewCount_shouldIncrementViewCount_ById_whenIdNotExists() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateViewCountById(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void updateLikeCount_shouldIncrementOrDecrementLikeCountById() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateLikeCountById(EXISTING_VIDEO_METADATA_ID, true);
        assertEquals(1L, metadata.getLikeCount());

        videoMetadataService.updateLikeCountById(EXISTING_VIDEO_METADATA_ID, false);
        assertEquals(0L, metadata.getLikeCount());

        verify(videoMetadataRepository, times(2)).save(metadata);

    }

    @Test
    void updateViewCount_shouldIncrementLikeCount_ById_whenIdNotExists() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void updateDislikeCount_shouldIncrementOrDecrementDislikeCountById() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, true);
        assertEquals(1L, metadata.getDislikeCount());

        videoMetadataService.updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, false);
        assertEquals(0L, metadata.getDislikeCount());

        verify(videoMetadataRepository, times(2)).save(metadata);

    }

    @Test
    void updateViewCount_shouldIncrementDislikeCount_ById_whenIdNotExists() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void delete_shouldDeleteByIdMetadataById() {

        doNothing().when(videoMetadataRepository).deleteById(EXISTING_VIDEO_METADATA_ID);

        videoMetadataRepository.deleteById(EXISTING_VIDEO_METADATA_ID);

        verify(videoMetadataRepository).deleteById(EXISTING_VIDEO_METADATA_ID);

    }
}
