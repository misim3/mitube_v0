package com.misim.integration.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misim.controller.VideoFileController;
import com.misim.entity.VideoCatalog;
import com.misim.entity.VideoFile;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoFileService;
import com.misim.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(controllers = VideoFileController.class)
@WithMockUser
class VideoFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VideoFileService videoFileService;

    @MockBean
    private VideoService videoService;

    @Test
    void testUploadVideo_success() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("file", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "video content".getBytes());
        when(videoFileService.uploadVideo(any(MultipartFile.class))).thenReturn("123");

        mockMvc.perform(multipart(HttpMethod.POST, "/videofiles/upload").file(mockFile).with(csrf()))
            .andExpect(status().isOk());

        verify(videoFileService).uploadVideo(any(MultipartFile.class));
    }

    @Test
    void testUploadVideo_EmptyFile() throws Exception {

        MockMultipartFile emptyFile = new MockMultipartFile("file", "", MediaType.MULTIPART_FORM_DATA_VALUE, "".getBytes());

        mockMvc.perform(multipart("/videofiles/upload").file(emptyFile).with(csrf()))
            .andExpect(status().isBadRequest());

        verify(videoFileService, org.mockito.Mockito.never()).uploadVideo(any(MultipartFile.class));
    }

    @Test
    void testStreamVideoVideo_Success() throws Exception {
        // given
        Long videoId = 1L;
        VideoFile videoFile = new VideoFile("path/to/video.mp4");
        VideoCatalog videoCatalog = new VideoCatalog("title", "description", 0, videoFile, null);
        when(videoService.getVideo(videoId)).thenReturn(videoCatalog);

        byte[] videoContent = "test video content".getBytes();
        ByteArrayResource resource = new ByteArrayResource(videoContent);
        when(videoFileService.getFileResource("path/to/video.mp4")).thenReturn(resource);

        // when & then
        mockMvc.perform(get("/videofiles/{videoId}", videoId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.parseMediaType("video/mp4")))
            .andExpect(content().bytes(videoContent));

        // verify
        verify(videoService, times(1)).getVideo(videoId);
        verify(videoFileService, times(1)).getFileResource("path/to/video.mp4");
    }

    @Test
    void testStreamVideoVideo_VideoNotFound() throws Exception {
        // given
        doThrow(new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO)).when(videoService).getVideo(1L);

        // when & then
        mockMvc.perform(get("/videofiles/1"))
            .andExpect(status().isNotFound());
    }
}
