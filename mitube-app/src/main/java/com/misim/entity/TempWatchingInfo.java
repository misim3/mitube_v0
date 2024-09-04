package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "temp_watching_infos")
@NoArgsConstructor
public class TempWatchingInfo extends BaseTimeEntity {

    @Id
    private Long id;

    private Long userId;

    private Long videoId;

    @Setter
    private Long watchingTime;

    private Boolean isWatchedToEnd;

    @Builder
    public TempWatchingInfo(Long id, Long userId, Long videoId, Long watchingTime) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
        this.watchingTime = watchingTime;
        this.isWatchedToEnd = false;
    }

    public WatchingInfo convertToWatchingInfo() {
        WatchingInfo watchingInfo = WatchingInfo.builder()
            .id(this.id)
            .userId(this.userId)
            .videoId(this.videoId)
            .watchingTime(this.watchingTime)
            .build();

        if (this.isWatchedToEnd) {
            watchingInfo.completeWatchingVideo();
        }
        return watchingInfo;
    }

    public void completeWatchingVideo() {
        this.isWatchedToEnd = true;
    }
}
