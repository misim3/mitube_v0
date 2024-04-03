package com.misim.service;

import com.misim.controller.model.Request.CreateCommentRequest;
import com.misim.controller.model.Response.CommentListResponse;
import com.misim.controller.model.Response.CommentResponse;
import com.misim.controller.model.Response.CreateCommentResponse;
import com.misim.entity.Comment;
import com.misim.entity.User;
import com.misim.entity.Video;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.CommentRepository;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public CommentListResponse getParentComments(Long videoId, Long idx, String scrollDirection) {

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        List<Comment> comments = new ArrayList<>();

        if (scrollDirection.equals("up")) {
            comments = commentRepository.findUpCommentByVideoIdAndId(videoId, idx);
        } else if (scrollDirection.equals("down")) {
            comments = commentRepository.findDownCommentByVideoIdAndId(videoId, idx);
        }

        return CommentListResponse.builder()
                .commentResponses(comments.stream()
                        .map(c -> CommentResponse.builder()
                                .content(c.getContent())
                                .writerNickname(c.getUser().getNickname())
                                .build())
                        .toList())
                .build();
    }

    public CommentListResponse getChildComments(Long videoId, Long parentCommentId, Long idx, String scrollDirection) {

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        List<Comment> comments = new ArrayList<>();

        if (scrollDirection.equals("up")) {
            comments = commentRepository.findUpCommentByVideoIdAndIdAndParentCommentId(parentCommentId, videoId, idx);
        } else if (scrollDirection.equals("down")) {
            comments = commentRepository.findDownCommentByVideoIdAndIdAndParentCommentId(parentCommentId, videoId, idx);
        }

        return CommentListResponse.builder()
                .commentResponses(comments.stream()
                        .map(c -> CommentResponse.builder()
                                .content(c.getContent())
                                .writerNickname(c.getUser().getNickname())
                                .build())
                        .toList())
                .build();
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

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_USER));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .video(video)
                .user(user)
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
