package com.misim.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.controller.model.Response.CommentListResponse;
import com.misim.controller.model.Response.CommentResponse;
import com.misim.controller.model.Response.StartWatchingVideoResponse;
import com.misim.service.CommentService;
import com.misim.service.ReactionService;
import com.misim.service.VideoService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(VideoController.class)
@WithMockUser
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VideoService videoService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private ReactionService reactionService;

    @Test
    void uploadVideos_correctly_byMocking() throws Exception {

        // mock 객체
        MockMultipartFile file = new MockMultipartFile("file", "test file".getBytes());

        String mockResponse = "1";

        given(videoService.uploadVideos(file)).willReturn(mockResponse);

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(multipart(HttpMethod.POST,"/videos/upload")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.id").value("1"));
    }

    @Test
    void createVideos_correctly_byMocking() throws Exception {

        // mock 객체
        CreateVideoRequest mockCreateVideoRequest = new CreateVideoRequest();
        mockCreateVideoRequest.setTitle("test");
        mockCreateVideoRequest.setDescription("test video");
        mockCreateVideoRequest.setNickname("hongkildong");
        mockCreateVideoRequest.setVideo_token("MQ==");
        mockCreateVideoRequest.setCategoryId(1);

        doNothing().when(videoService).createVideos(mockCreateVideoRequest);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreateVideoRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void startWatchingVideo_correctly_byMocking() throws Exception {

        Long videoId = 1L;
        Long userId = 1L;

        // Mock videoService의 응답
        StartWatchingVideoResponse startWatchingVideoResponse = StartWatchingVideoResponse.builder()
            .videoId(videoId)
            .watchingTime(1234L)
            .views(10000L)
            .videoLink("http://example.com/video")
            .reactionResponse(null)
            .build();

        CommentResponse commentResponse = CommentResponse.builder()
            .commentId(1L)
            .content("mock video")
            .writerNickname("user1")
            .build();

        CommentListResponse commentListResponse = CommentListResponse.builder()
            .commentResponses(List.of(commentResponse))
            .hasNext(false)
            .build();

        given(videoService.startWatchingVideo(videoId, userId)).willReturn(startWatchingVideoResponse);
        given(commentService.getParentComments(videoId, null, "down")).willReturn(commentListResponse);

        mockMvc.perform(get("/videos/watch/{videoId}", videoId)
                .param("userId", userId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.videoId").value(videoId))
            .andExpect(jsonPath("$.body.watchingTime").value(1234))
            .andExpect(jsonPath("$.body.views").value(10000))
            .andExpect(jsonPath("$.body.videoLink").value("http://example.com/video"))
            .andExpect(jsonPath("$.body.commentListResponse.commentResponses[0].commentId").value(1))
            .andExpect(jsonPath("$.body.commentListResponse.commentResponses[0].content").value("mock video"))
            .andExpect(jsonPath("$.body.commentListResponse.commentResponses[0].writerNickname").value("user1"))
            .andExpect(jsonPath("$.body.commentListResponse.hasNext").value(false));
    }

    @Test
    void watcingVideo_correctly_byMocking() throws Exception {

        Long videoId = 1L;
        Long userId = 1L;
        Long watchingTime = 1234L;

        mockMvc.perform(post("/videos/watch/{videoId}/update", 1L)
            .param("userId", userId.toString())
            .param("watchingTime", watchingTime.toString()))
            .andExpect(status().isOk());
    }

    @Test
    void completeWatcingVideo_correctly_byMocking() throws Exception {

        Long videoId = 1L;
        Long userId = 1L;
        Long watchingTime = 1234L;

        mockMvc.perform(post("/videos/watch/{videoId}/update", 1L)
                .param("userId", userId.toString())
                .param("watchingTime", watchingTime.toString()))
            .andExpect(status().isOk());
    }

    @Test
    void checkVideo_correctly_byMocking() throws Exception {
        
    }
}