package com.misim.unit.service;

import static com.misim.util.TimeUtil.getNow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.misim.entity.VideoFile;
import com.misim.exception.MitubeException;
import com.misim.repository.VideoFileRepository;
import com.misim.service.VideoFileService;
import com.misim.util.Base64Convertor;
import com.misim.util.TimeUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
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
    private Path mockPath;

    @Mock
    private LocalDateTime mockDateTime;

    private static final Long EXISTING_VIDEO_FILE_ID = 1L;
    private static final Long NON_EXISTENT_VIDEO_FILE_ID = 99999L;

//    @BeforeEach
//    void setUp() {
//        ReflectionTestUtils.setField(videoFileService, "UPLOAD_PATH", "mock/upload/path");
//    }

    @Test
    void uploadVideo_shouldSaveFileAndReturnEncodedId() throws IOException {

        String originalFilename = "test.mp4";

        try (MockedStatic<TimeUtil> timeUtilMock = mockStatic(TimeUtil.class);
            MockedStatic<Paths> pathsMock = mockStatic(Paths.class);
            MockedStatic<Files> filesMock = mockStatic(Files.class);
            MockedStatic<Base64Convertor> base64ConvertorMock = mockStatic(Base64Convertor.class)
        ) {

            timeUtilMock.when(TimeUtil::getNow).thenReturn(mockDateTime);

            pathsMock.when(() -> Paths.get(anyString())).thenReturn(mockPath);

            filesMock.when(() -> Files.exists(mockPath)).thenReturn(false);
            filesMock.when(() -> Files.createDirectories(any(Path.class))).thenReturn(mockPath);

            when(mockMultipartFile.getOriginalFilename()).thenReturn(originalFilename);

            doNothing().when(mockMultipartFile).transferTo(any(Path.class));

            when(videoFileRepository.save(any(VideoFile.class))).thenReturn(mockVideoFile);

            when(mockVideoFile.getId()).thenReturn(1L);

            base64ConvertorMock.when(() -> Base64Convertor.encode(anyLong())).thenReturn(String.valueOf(1L));

            String encodedId = videoFileService.uploadVideo(mockMultipartFile);

            timeUtilMock.verify(TimeUtil::getNow, times(1));
            pathsMock.verify(() -> Paths.get(anyString()), times(2));
            filesMock.verify(() -> Files.exists(mockPath), times(1));
            filesMock.verify(() -> Files.createDirectories(mockPath), times(1));
            verify(mockMultipartFile, times(1)).getOriginalFilename();
            verify(mockMultipartFile, times(1)).transferTo(any(Path.class));
            verify(videoFileRepository, times(1)).save(any(VideoFile.class));
            verify(mockVideoFile, times(1)).getId();
            base64ConvertorMock.verify(() -> Base64Convertor.encode(anyLong()), times(1));
            assertThat(encodedId).isEqualTo(String.valueOf(1L));

        }

    }

    @Test
    void uploadVideo_shouldThrowException_whenFailToCreateDirectories() throws IOException {

        try (MockedStatic<TimeUtil> timeUtilMock = mockStatic(TimeUtil.class);
            MockedStatic<Paths> pathsMock = mockStatic(Paths.class);
            MockedStatic<Files> filesMock = mockStatic(Files.class)
        ) {

            timeUtilMock.when(TimeUtil::getNow).thenReturn(mockDateTime);
            pathsMock.when(() -> Paths.get(anyString())).thenReturn(mockPath);
            filesMock.when(() -> Files.exists(mockPath)).thenReturn(false);
            filesMock.when(() -> Files.createDirectories(any(Path.class))).thenThrow(IOException.class);

            assertThrows(MitubeException.class, () -> videoFileService.uploadVideo(mockMultipartFile));

            timeUtilMock.verify(TimeUtil::getNow, times(1));
            pathsMock.verify(() -> Paths.get(anyString()), times(1));
            filesMock.verify(() -> Files.exists(mockPath), times(1));
            filesMock.verify(() -> Files.createDirectories(mockPath), times(1));

        }

    }

    @Test
    void uploadVideo_shouldThrowException_whenFailToTransferFile() throws IOException {

        String originalFilename = "test.mp4";

        try (MockedStatic<TimeUtil> timeUtilMock = mockStatic(TimeUtil.class);
            MockedStatic<Paths> pathsMock = mockStatic(Paths.class);
            MockedStatic<Files> filesMock = mockStatic(Files.class)
        ) {

            timeUtilMock.when(TimeUtil::getNow).thenReturn(mockDateTime);
            pathsMock.when(() -> Paths.get(anyString())).thenReturn(mockPath);
            filesMock.when(() -> Files.exists(mockPath)).thenReturn(false);
            filesMock.when(() -> Files.createDirectories(any(Path.class))).thenReturn(mockPath);

            when(mockMultipartFile.getOriginalFilename()).thenReturn(originalFilename);

            doThrow(IOException.class).when(mockMultipartFile).transferTo(mockPath);

            assertThrows(MitubeException.class, () -> videoFileService.uploadVideo(mockMultipartFile));

            timeUtilMock.verify(TimeUtil::getNow, times(1));
            pathsMock.verify(() -> Paths.get(anyString()), times(2));
            filesMock.verify(() -> Files.exists(mockPath), times(1));
            filesMock.verify(() -> Files.createDirectories(mockPath), times(1));
            verify(mockMultipartFile, times(1)).getOriginalFilename();
            verify(mockMultipartFile, times(1)).transferTo(any(Path.class));

        }

    }

    @Test
    void getFileResource_shouldReturnResource() {

        String path = "test_path";

        Resource resource = videoFileService.getFileResource(path);

        assertThat(resource).isNotNull();

    }

    @Test
    void deleteVideoFileById_shouldDelete_whenIdExists() {

//        when(videoFileRepository.findById(EXISTING_VIDEO_FILE_ID)).thenReturn(Optional.of(mockVideoFile));


    }
}
