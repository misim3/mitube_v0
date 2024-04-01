package com.misim.repository;

import com.misim.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Slice<Comment> findByVideoIdAndParentCommentIdIsNull(Long videoId, Pageable pageable);

    Slice<Comment> findCommentsByVideoIdAndParentCommentId(Long videoId, Long parentCommentId, Pageable pageable);
}
