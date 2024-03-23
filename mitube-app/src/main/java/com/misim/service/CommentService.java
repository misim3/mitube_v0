package com.misim.service;

import com.misim.controller.model.Request.CreateCommentRequest;
import com.misim.controller.model.Response.CommentListResponse;
import com.misim.controller.model.Response.CommentResponse;
import com.misim.controller.model.Response.CreateCommentResponse;
import com.misim.entity.Comment;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.CommentRepository;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public Slice<CommentResponse> getComments(Long videoId, int page) {

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        // 댓글 검색
        Pageable pageable = PageRequest.of(page, 10);

        Slice<Comment> pages = commentRepository.findAll(pageable);

        // 댓글 Id로 대댓글 검색
//        List<CommentResponse> commentResponses = pages.map(p -> CommentResponse.builder()
//                .childComments(commentRepository.findTopTenByParentCommentId(p.getId()))
//                .writerNickname(userRepository.findById(p.getUserId()))
//                .content(p.getContent())
//                .build());
        
        // 댓글과 대댓글 합치기
//        List<CommentResponse> list = null;

        return pages.map(p -> CommentResponse.builder()
                        .content(p.getContent())
                        .writerNickname(String.valueOf(p.getUserId()))
                        .build());
    }

    public CreateCommentResponse createComments(CreateCommentRequest request) {

        if (!userRepository.existsById(request.getUserId())) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        if (!videoRepository.existsById(request.getVideoId())) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        if (request.getParentCommentId() != null && !commentRepository.existsById(request.getParentCommentId())) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_COMMENT);
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .videoId(request.getVideoId())
                .userId(request.getUserId())
                .parentCommentId(request.getParentCommentId())
                .build();

        commentRepository.save(comment);

        return CreateCommentResponse.builder()
                .commentId(comment.getId())
                .build();
    }

    public void updateComments(Long commentId, String content) {

        if (commentId == null) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_COMMENT);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_COMMENT));

        comment.setContent(content);

        commentRepository.save(comment);
    }

    public void deleteComments(Long commentId) {

        if (commentId == null || !commentRepository.existsById(commentId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_COMMENT);
        }

        commentRepository.deleteById(commentId);
    }
}
