package com.misim.entity;

import com.misim.util.TimeUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@RedisHash(value = "watchingInfo")
public class WatchingInfo implements Serializable {

    @Id
    private String id;

    private Long userId;

    private Long videoId;

    private Long watchingTime;

    // 동영상을 시청한 시간에 대한 필드 필요.

    @Builder
    public WatchingInfo(Long userId, Long videoId, Long watchingTime) {
        this.userId = userId;
        this.videoId = videoId;
        this.watchingTime = watchingTime;
    }
}
