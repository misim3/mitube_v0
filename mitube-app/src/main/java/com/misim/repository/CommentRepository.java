package com.misim.repository;

import com.misim.controller.model.Response.CommentResponse;
import com.misim.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findTopTenByParentCommentId(Long parentCommentId);

    Slice<Comment> findAllCommentResponse(Pageable pageable);
}
