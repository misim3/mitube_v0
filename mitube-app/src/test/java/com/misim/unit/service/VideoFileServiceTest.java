package com.misim.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.misim.entity.VideoFile;
import com.misim.repository.VideoFileRepository;
import com.misim.service.VideoFileService;
import com.misim.util.Base64Convertor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class VideoFileServiceTest {

    @Mock
    private VideoFileRepository videoFileRepository;

    @InjectMocks
    private VideoFileService videoFileService;

    @Mock
    private VideoFile mockVideoFile;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private FileSystemResource mockFileSystemResource;

    @Mock
    private Path mockPath;

    @Mock
    private File mockFile;

    private static final Long EXISTING_VIDEO_METADATA_ID = 1L;
    private static final Long NON_EXISTENT_VIDEO_METADATA_ID = 99999L;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(videoFileService, "UPLOAD_PATH", "mock/upload/path");
    }

    @Test
    void uploadVideo_shouldSaveFileAndReturnEncodedId()
        throws IOException, NoSuchFieldException, IllegalAccessException {
        // Given
        String originalFilename = "test.mp4";
        String savedFilename = "mock/upload/path";
        byte[] content = "video content".getBytes();

        VideoFile newFile = new VideoFile();

        Mockito.when(multipartFile.getOriginalFilename()).thenReturn(originalFilename);
        Mockito.doNothing().when(multipartFile).transferTo(any(Path.class));
        Mockito.when(videoFileRepository.save(any(VideoFile.class))).thenReturn(mockVideoFile);
        Mockito.when(mockVideoFile.getId()).thenReturn(1L);
        Mockito.mockStatic(Base64Convertor.class);
        Mockito.when(Base64Convertor.encode(anyLong())).thenReturn(String.valueOf(1L));

        // When
        String encodedId = videoFileService.uploadVideo(multipartFile);

        // Then
        verify(multipartFile).transferTo(any(Path.class));
        verify(videoFileRepository).save(any(VideoFile.class));
        assertThat(encodedId).isEqualTo("1");
    }

    @Test
    void getFileResource() {
    }

    @Test
    void deleteVideoFileById() {
    }
}
