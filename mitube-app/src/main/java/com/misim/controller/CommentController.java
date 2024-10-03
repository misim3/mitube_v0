package com.misim.controller;

import com.misim.controller.model.CommentDto;
import com.misim.controller.model.Request.CreateCommentRequest;
import com.misim.controller.model.Request.UpdateCommentRequest;
import com.misim.controller.model.Response.CommentListResponse;
import com.misim.controller.model.Response.CreateCommentResponse;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @Operation(summary = "댓글 목록 요청", description = "동영상의 댓글 목록 10개 전달")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 목록 요청 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/{videoId}")
    public CommonResponse<CommentListResponse> getParentComments(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId,
        @RequestParam @Parameter(name = "idx", description = "댓글의 인덱스 정보") Long idx,
        @RequestParam @Parameter(name = "scrollDirection", description = "댓글 목록 스크롤 방향으로 up, down만 가능하다.") String scrollDirection) {

        checkRequests(idx, scrollDirection);

        CommentListResponse comments = commentService.getParentComments(videoId, idx,
            scrollDirection);

        return CommonResponse.<CommentListResponse>builder()
            .body(comments)
            .build();
    }

    @Operation(summary = "대댓글 목록 요청", description = "동영상 댓글의 대댓글 목록 10개 전달")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "대댓글 목록 요청 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/{videoId}/{parentCommentId}")
    public CommonResponse<CommentListResponse> getChildComments(
        @PathVariable @Parameter(name = "videoId", description = "시청할 동영상 식별 정보", required = true) Long videoId,
        @PathVariable @Parameter(name = "parentCommentId", description = "대댓글이 달린 댓글의 식별 정보") Long parentCommentId,
        @RequestParam @Parameter(name = "idx", description = "대댓글의 인덱스 정보") Long idx) {

        CommentListResponse comments = commentService.getChildComments(videoId, parentCommentId,
            idx);

        return CommonResponse.<CommentListResponse>builder()
            .body(comments)
            .build();
    }

    @Operation(summary = "댓글 생성", description = "동영상 댓글을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 생성 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/create")
    public CommonResponse<CreateCommentResponse> createComments(
        @RequestBody CreateCommentRequest request) {

        request.check();

        CreateCommentResponse response = commentService.createComments(request);

        return CommonResponse.<CreateCommentResponse>builder().body(response).build();
    }

    @Operation(summary = "댓글 수정", description = "동영상 댓글을 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/update")
    public void updateComments(@RequestBody UpdateCommentRequest request) {

        request.check();

        commentService.updateComments(request.getCommentId(), request.getContent());
    }

    @Operation(summary = "댓글 삭제", description = "동영상 댓글을 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/delete")
    public void deleteComments(@RequestBody CommentDto request) {

        request.check();

        commentService.deleteComments(request.getCommentId());
    }

    private void checkRequests(Long idx, String scrollDirection) {

        if (idx < 0) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_INDEX);
        }

        if (scrollDirection.isBlank() || !scroll.contains(scrollDirection)) {
            throw new MitubeException(MitubeErrorCode.INVALID_COMMENT_SCROLL_DIRECTION);
        }
    }
}
