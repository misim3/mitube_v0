package com.misim.controller;

import com.misim.controller.model.Request.CreateCommentRequest;
import com.misim.controller.model.Response.CommentListResponse;
import com.misim.controller.model.Response.CommentResponse;
import com.misim.controller.model.Response.CreateCommentResponse;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Tag(name = "댓글 API", description = "댓글 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private final List<String> scroll = Arrays.asList("up", "down");

    // idx, sort, scroll direction
    // 1th way - idx and scroll direction -> 'idx'
    // 2th way - scroll direction ~ idx -> 'idx' : scroll down lowest idx or scroll up highest idx
    // scroll down
    // select * from comments where parentCommentId is null and videoId = 'videoId' and idx < 'idx' order by sort DESC limit 'pageSize';
    // scroll up
    // select * from comments where parentCommentId is null and videoId = 'videoId' and idx > 'idx' order by sort ACS limit 'pageSize';
    // 1 / '2' 3 4 / '5' 6 7 / '8' 9 10
    // commentService 메소드 호출 전, scrollDirection 데이터 검사
    @GetMapping("/{videoId}")
    public CommonResponse<CommentListResponse> getParentComments(@PathVariable Long videoId, @RequestParam Long idx, @RequestParam String scrollDirection) {

        checkRequests(idx, scrollDirection);

        CommentListResponse comments = commentService.getParentComments(videoId, idx, scrollDirection);

        return CommonResponse.<CommentListResponse>builder()
                .body(comments)
                .build();
    }

    @GetMapping("/{videoId}/{parentCommentId}")
    public CommonResponse<CommentListResponse> getChildComments(@PathVariable Long videoId, @PathVariable Long parentCommentId, @RequestParam Long idx, @RequestParam String scrollDirection) {

        checkRequests(idx, scrollDirection);

        CommentListResponse comments = commentService.getChildComments(videoId, parentCommentId, idx, scrollDirection);

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

    private void checkRequests(Long idx, String scrollDirection) {

        if (idx < 1) {
            throw  new MitubeException(MitubeErrorCode.INVALID_COMMENT_INDEX);
        }

        if (scrollDirection.isBlank() || !scroll.contains(scrollDirection)) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_SCROLL_DIRECTION);
        }
    }
}
