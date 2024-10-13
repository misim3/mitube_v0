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

    private Long videoCatalogId;

    private Long parentCommentId;

    @ManyToOne
    @Setter
    private User user;

    @Setter
    private Boolean isActive;

    @Builder
    public Comment(String content, Long videoCatalogId, Long parentCommentId, User user) {
        this.content = content;
        this.videoCatalogId = videoCatalogId;
        this.parentCommentId = parentCommentId;
        this.user = user;
        this.isActive = true;
    }
}
