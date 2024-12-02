package com.misim.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.misim.entity.VideoFile;
import com.misim.repository.VideoFileRepository;
import com.misim.service.VideoFileService;
import com.misim.util.Base64Convertor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private MultipartFile mockMultipartFile;

    @Mock
    private FileSystemResource mockFileSystemResource;

    @Mock
    private Path mockPath;


    private static final Long EXISTING_VIDEO_FILE_ID = 1L;
    private static final Long NON_EXISTENT_VIDEO_FILE_ID = 99999L;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(videoFileService, "UPLOAD_PATH", "mock/upload/path");
    }

    @Test
    void uploadVideo_shouldSaveFileAndReturnEncodedId() throws IOException, NoSuchFieldException, IllegalAccessException {

        String originalFilename = "test.mp4";

        Mockito.mockStatic(Paths.class);
        Mockito.when(Paths.get(anyString())).thenReturn(mockPath);
        Mockito.mockStatic(Files.class);
        Mockito.when(Files.exists(mockPath)).thenReturn(false);
        Mockito.when(Files.createDirectories(any(Path.class))).thenReturn(mockPath);

        Mockito.when(mockMultipartFile.getOriginalFilename()).thenReturn(originalFilename);

        Mockito.doNothing().when(mockMultipartFile).transferTo(any(Path.class));

        Mockito.when(videoFileRepository.save(any(VideoFile.class))).thenReturn(mockVideoFile);

        Mockito.when(mockVideoFile.getId()).thenReturn(1L);
        Mockito.mockStatic(Base64Convertor.class);
        Mockito.when(Base64Convertor.encode(anyLong())).thenReturn(String.valueOf(1L));

        String encodedId = videoFileService.uploadVideo(mockMultipartFile);

        verify(mockMultipartFile, times(1)).getOriginalFilename();
        verify(mockMultipartFile, times(1)).transferTo(any(Path.class));
        verify(videoFileRepository, times(1)).save(any(VideoFile.class));
        verify(mockVideoFile, times(1)).getId();
        assertThat(encodedId).isEqualTo(String.valueOf(1L));

    }

    @Test
    void getFileResource() {
    }

    @Test
    void deleteVideoFileById() {
    }
}
