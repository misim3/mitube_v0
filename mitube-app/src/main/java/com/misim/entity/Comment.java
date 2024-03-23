package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String content;

    private Long videoId;

    private Long parentCommentId;

    private Long userId;

    @Builder
    public Comment(String content, Long videoId, Long parentCommentId, Long userId) {
        this.content = content;
        this.videoId = videoId;
        this.parentCommentId = parentCommentId;
        this.userId = userId;
    }
}
