package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    // 단방향 매핑
    @ManyToOne
    private Video video;

    private Long parentCommentId;

    @ManyToOne
    @Setter
    private User user;

    @Setter
    private Boolean isActive;

    @Builder
    public Comment(String content, Video video, Long parentCommentId, User user) {
        this.content = content;
        this.video = video;
        this.parentCommentId = parentCommentId;
        this.user = user;
        this.isActive = true;
    }
}
