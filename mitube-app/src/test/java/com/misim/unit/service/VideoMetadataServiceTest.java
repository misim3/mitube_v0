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
import java.util.NoSuchElementException;
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

    @BeforeEach
    void setUp() {
        metadata = VideoMetadata.builder()
            .viewCount(0L)
            .likeCount(0L)
            .dislikeCount(0L)
            .build();
    }

    @Test
    void create_shouldSaveAndReturnMetadata() {

        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        VideoMetadata result = videoMetadataService.create();

        assertNotNull(result);
        assertEquals(0L, result.getViewCount());
        assertEquals(0L, result.getLikeCount());
        assertEquals(0L, result.getDislikeCount());
        verify(videoMetadataRepository).save(any(VideoMetadata.class));

    }

    @Test
    void read_shouldReturnMetadata_whenIdExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.of(metadata));

        VideoMetadata result = videoMetadataService.read(1L);

        assertNotNull(result);
        verify(videoMetadataRepository).findById(1L);

    }

    @Test
    void read_shouldThrowException_whenIdNotExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoMetadataService.read(1L));

    }

    @Test
    void readViewCount_shouldReturnViewCount_whenIdExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.of(metadata));

        Long viewCount = videoMetadataService.readViewCount(1L);

        assertEquals(0L, viewCount);
        verify(videoMetadataRepository).findById(1L);

    }

    @Test
    void readViewCount_shouldReturnViewCount_whenIdNotExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoMetadataService.readViewCount(1L));

    }

    @Test
    void readLikeCount_shouldReturnLikeCount_whenIdExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.of(metadata));

        Long likeCount = videoMetadataService.readLikeCount(1L);

        assertEquals(0L, likeCount);
        verify(videoMetadataRepository).findById(1L);

    }

    @Test
    void readViewCount_shouldReturnLikeCount_whenIdNotExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoMetadataService.readLikeCount(1L));

    }

    @Test
    void readDislikeCount_shouldReturnDislikeCount_whenIdExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.of(metadata));

        Long dislikeCount = videoMetadataService.readDislikeCount(1L);

        assertEquals(0L, dislikeCount);
        verify(videoMetadataRepository).findById(1L);

    }

    @Test
    void readViewCount_shouldReturnDislikeCount_whenIdNotExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoMetadataService.readDislikeCount(1L));

    }

    @Test
    void updateViewCount_shouldIncrementViewCount() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateViewCount(1L);

        assertEquals(1L, metadata.getViewCount());
        verify(videoMetadataRepository).save(metadata);

    }

    @Test
    void updateViewCount_shouldIncrementViewCount_whenIdNotExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoMetadataService.updateViewCount(1L));

    }

    @Test
    void updateLikeCount_shouldIncrementOrDecrementLikeCount() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateLikeCount(1L, true);
        assertEquals(1L, metadata.getLikeCount());

        videoMetadataService.updateLikeCount(1L, false);
        assertEquals(0L, metadata.getLikeCount());

        verify(videoMetadataRepository, times(2)).save(metadata);

    }

    @Test
    void updateViewCount_shouldIncrementLikeCount_whenIdNotExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoMetadataService.updateLikeCount(1L, true));
        assertThrows(NoSuchElementException.class, () -> videoMetadataService.updateLikeCount(1L, false));

    }

    @Test
    void updateDislikeCount_shouldIncrementOrDecrementDislikeCount() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.of(metadata));
        when(videoMetadataRepository.save(any(VideoMetadata.class))).thenReturn(metadata);

        videoMetadataService.updateDislikeCount(1L, true);
        assertEquals(1L, metadata.getDislikeCount());

        videoMetadataService.updateDislikeCount(1L, false);
        assertEquals(0L, metadata.getDislikeCount());

        verify(videoMetadataRepository, times(2)).save(metadata);

    }

    @Test
    void updateViewCount_shouldIncrementDislikeCount_whenIdNotExists() {

        when(videoMetadataRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoMetadataService.updateDislikeCount(1L, true));
        assertThrows(NoSuchElementException.class, () -> videoMetadataService.updateDislikeCount(1L, false));

    }

    @Test
    void delete_shouldDeleteMetadataById() {

        doNothing().when(videoMetadataRepository).deleteById(1L);

        videoMetadataRepository.deleteById(1L);

        verify(videoMetadataRepository).deleteById(1L);

    }
}
