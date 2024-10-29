package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class VideoMetadata extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long viewCount;

    private Long likeCount;

    private Long dislikeCount;

    @Builder
    public VideoMetadata(Long viewCount, Long likeCount, Long dislikeCount) {
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void incrementDislikeCount() {
        this.dislikeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }

    public void decrementDislikeCount() {
        this.dislikeCount--;
    }
}
