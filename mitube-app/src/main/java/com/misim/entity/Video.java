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

@Entity
@Getter
@Table(name = "videos")
@NoArgsConstructor
public class Video extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    private VideoFile videoFile;

    private Long viewCount;

    private Integer categoryId;

    // 연관관계 설정 가능
    private String thumbnailUrl;

    @Builder
    public Video(String title, String description, User user, VideoFile videoFile, Long viewCount,
        Integer categoryId, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.videoFile = videoFile;
        this.viewCount = viewCount;
        this.categoryId = categoryId;
        this.thumbnailUrl = thumbnailUrl;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
