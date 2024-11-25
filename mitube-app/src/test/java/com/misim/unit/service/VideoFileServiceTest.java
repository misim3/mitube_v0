package com.misim.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.misim.entity.VideoFile;
import com.misim.repository.VideoFileRepository;
import com.misim.service.VideoFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VideoFileServiceTest {

    @Mock
    private VideoFileRepository videoFileRepository;

    @InjectMocks
    private VideoFileService videoFileService;

    @Mock
    private VideoFile videoFile;

    private static final Long EXISTING_VIDEO_METADATA_ID = 1L;
    private static final Long NON_EXISTENT_VIDEO_METADATA_ID = 99999L;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadVideo_shouldSaveAndReturnString() {

        when(videoFileRepository.save(any(VideoFile.class))).thenReturn(videoFile);

    }

    @Test
    void getFileResource() {
    }

    @Test
    void deleteVideoFileById() {
    }
}
