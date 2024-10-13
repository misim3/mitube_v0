package com.misim.repository;

import com.misim.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c1 from Comment c1 WHERE c1.parentCommentId IS NULL AND c1.videoCatalogId = :videoCatalogId AND c1.id > :id ORDER BY c1.id LIMIT 11")
    List<Comment> findDownCommentByVideoIdAndId(@Param("videoCatalogId") Long videoCatalogId,
        @Param("id") Long id);

    @Query("SELECT c1 from Comment c1 WHERE c1.parentCommentId IS NULL AND c1.videoCatalogId = :videoCatalogId AND c1.id < :id ORDER BY c1.id DESC LIMIT 10")
    List<Comment> findUpCommentByVideoIdAndId(@Param("videoCatalogId") Long videoCatalogId, @Param("id") Long id);

    @Query("SELECT c1 from Comment c1 WHERE c1.parentCommentId = :parentCommentId AND c1.videoCatalogId = :videoCatalogId AND c1.id > :id ORDER BY c1.id LIMIT 11")
    List<Comment> findDownCommentByVideoIdAndIdAndParentCommentId(
        @Param("parentCommentId") Long parentCommentId, @Param("videoCatalogId") Long videoCatalogId,
        @Param("id") Long id);
}
