package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Setter
    private Long watchingTime;

    private Boolean isWatchedToEnd;

    @Builder
    public WatchingInfo(Long id, Long userId, Long videoId, Long watchingTime) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
        this.watchingTime = watchingTime;
        this.isWatchedToEnd = false;
    }

    public void completeWatchingVideo() {
        this.isWatchedToEnd = true;
    }
}
