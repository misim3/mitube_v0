package com.misim.entity;

import com.misim.util.TimeUtil;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class WatchingInfo implements Serializable {

    private Long userId;

    private Long videoId;

    @Setter
    private Long watchingTime;

    @Setter
    private LocalDateTime modifiedDate;

    @Builder
    public WatchingInfo(Long userId, Long videoId, Long watchingTime) {
        this.userId = userId;
        this.videoId = videoId;
        this.watchingTime = watchingTime;
        this.modifiedDate = TimeUtil.getNow();
    }
}
