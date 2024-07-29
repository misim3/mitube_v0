package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "watching_infos")
@NoArgsConstructor
public class WatchingInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long videoId;

    private Long watchingTime;

    private Boolean isWatchedToEnd;

    @Builder
    public WatchingInfo(Long userId, Long videoId, Long watchingTime) {
        this.userId = userId;
        this.videoId = videoId;
        this.watchingTime = watchingTime;
        this.isWatchedToEnd = false;
    }

    public void addWatchingTime(Long watchingTime) {
        this.watchingTime += watchingTime;
    }

    public void completeWatchingVideo() {
        this.isWatchedToEnd = true;
    }
}
