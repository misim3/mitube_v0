package com.misim.repository;

import com.misim.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Slice<Comment> findByVideoIdAndParentCommentIdIsNull(Long videoId, Pageable pageable);

    Slice<Comment> findCommentsByVideoIdAndParentCommentId(Long videoId, Long parentCommentId,
        Pageable pageable);

    @Query("SELECT c1 from Comment c1 WHERE c1.parentCommentId IS NULL AND c1.video.id = :videoId AND c1.id < :id ORDER BY c1.createdDate DESC LIMIT 11")
    List<Comment> findDownCommentByVideoIdAndId(@Param("videoId") Long videoId,
        @Param("id") Long id);

    @Query("SELECT c1 from Comment c1 WHERE c1.parentCommentId IS NULL AND c1.video.id = :videoId AND c1.id > :id ORDER BY c1.createdDate ASC LIMIT 10")
    List<Comment> findUpCommentByVideoIdAndId(@Param("videoId") Long videoId, @Param("id") Long id);

    @Query("SELECT c1 from Comment c1 WHERE c1.parentCommentId = :parentCommentId AND c1.video.id = :videoId AND c1.id < :id ORDER BY c1.createdDate DESC LIMIT 11")
    List<Comment> findDownCommentByVideoIdAndIdAndParentCommentId(
        @Param("parentCommentId") Long parentCommentId, @Param("videoId") Long videoId,
        @Param("id") Long id);
}
