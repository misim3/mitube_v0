package com.misim.controller;

import com.misim.controller.model.Request.CreateCommentRequest;
import com.misim.controller.model.Response.CommentListResponse;
import com.misim.controller.model.Response.CommentResponse;
import com.misim.controller.model.Response.CreateCommentResponse;
import com.misim.exception.CommonResponse;
import com.misim.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;


@Tag(name = "댓글 API", description = "댓글 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/{videoId}")
    public CommonResponse<CommentListResponse> getParentComments(@PathVariable Long videoId, @RequestParam int page) {

        CommentListResponse comments = commentService.getParentComments(videoId, page);

        return CommonResponse.<CommentListResponse>builder()
                .body(comments)
                .build();
    }

    @GetMapping("/{videoId}/{parentCommentId}")
    public CommonResponse<CommentListResponse> getChildComments(@PathVariable Long videoId, @PathVariable Long parentCommentId, @RequestParam int page) {

        CommentListResponse comments = commentService.getChildComments(videoId, parentCommentId, page);

        return CommonResponse.<CommentListResponse>builder()
                .body(comments)
                .build();
    }

    @PostMapping("/create")
    public CommonResponse<CreateCommentResponse> createComments(@RequestBody CreateCommentRequest request) {
        request.check();

        CreateCommentResponse response = commentService.createComments(request);

        return CommonResponse.<CreateCommentResponse>builder().body(response).build();
    }

    @PostMapping("/{commentId}")
    public void updateComments(@PathVariable Long commentId, @RequestParam String content) {
        commentService.updateComments(commentId, content);
    }

    // 댓글 삭제 요청을 post, delete 중에 선택
    @DeleteMapping("/{commentId}")
    public void deleteComments(@PathVariable Long commentId) {
        commentService.deleteComments(commentId);
    }

}
