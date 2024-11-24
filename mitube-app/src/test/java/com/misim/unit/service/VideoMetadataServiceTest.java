package com.misim.unit.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.misim.entity.VideoMetadata;
import com.misim.repository.VideoMetadataRepository;
import com.misim.service.VideoMetadataService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
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

    @Mock
    private VideoMetadata metadata;

    private static final Long EXISTING_VIDEO_METADATA_ID = 1L;
    private static final Long NON_EXISTENT_VIDEO_METADATA_ID = 99999L;

    @Test
    void createNewVideoMetadata_shouldSaveAndReturnMetadata() {

        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        VideoMetadata result = videoMetadataService.createNewVideoMetadata();

        assertNotNull(result);
        verify(videoMetadataRepository).save(any(VideoMetadata.class));

    }

    @Test
    void readVideoMetadataById_shouldReturnMetadata_whenIdExists() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));

        VideoMetadata result = videoMetadataService.readVideoMetadataById(EXISTING_VIDEO_METADATA_ID);

        assertNotNull(result);
        verify(videoMetadataRepository).findById(EXISTING_VIDEO_METADATA_ID);

    }

    @Test
    void readVideoMetadataById_shouldThrowException_whenIdDoesNotExist() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.readVideoMetadataById(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void updateViewCountById_shouldIncrementViewCount_whenIdExists() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateViewCountById(EXISTING_VIDEO_METADATA_ID);

        verify(metadata).incrementViewCount();
        verify(videoMetadataRepository).save(metadata);

    }

    @Test
    void updateViewCountById_shouldThrowException_whenIdDoesNotExist() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateViewCountById(NON_EXISTENT_VIDEO_METADATA_ID));

    }

    @Test
    void updateLikeCountById_shouldIncrementLikeCount_whenIdExists() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateLikeCountById(EXISTING_VIDEO_METADATA_ID, true);

        verify(metadata).incrementLikeCount();
        verify(videoMetadataRepository).save(metadata);

    }

    @Test
    void updateLikeCountById_shouldDecrementLikeCount_whenIdExists() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateLikeCountById(EXISTING_VIDEO_METADATA_ID, false);

        verify(metadata).decrementLikeCount();
        verify(videoMetadataRepository).save(metadata);

    }

    @Test
    void updateLikeCountById_shouldThrowException_whenIdDoesNotExist() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void updateDislikeCountById_shouldIncrementDislikeCount_whenIdExists() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, true);

        verify(metadata).incrementDislikeCount();
        verify(videoMetadataRepository).save(metadata);

    }

    @Test
    void updateDislikeCountById_shouldDecrementDislikeCount_whenIdExists() {

        when(videoMetadataRepository.findById(EXISTING_VIDEO_METADATA_ID)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateDislikeCountById(EXISTING_VIDEO_METADATA_ID, false);

        verify(metadata).decrementDislikeCount();
        verify(videoMetadataRepository).save(metadata);

    }

    @Test
    void updateDislikeCountById_shouldThrowException_whenIdDoesNotExist() {

        when(videoMetadataRepository.findById(NON_EXISTENT_VIDEO_METADATA_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true));
        assertThrows(EntityNotFoundException.class, () -> videoMetadataService.updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false));

    }

    @Test
    void deleteMetadataById_shouldDeleteMetadataMetadata_whenIdExists() {

        doNothing().when(videoMetadataRepository).deleteById(EXISTING_VIDEO_METADATA_ID);

        videoMetadataRepository.deleteById(EXISTING_VIDEO_METADATA_ID);

        verify(videoMetadataRepository).deleteById(EXISTING_VIDEO_METADATA_ID);

    }
}
