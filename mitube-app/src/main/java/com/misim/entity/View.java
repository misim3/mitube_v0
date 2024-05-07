package com.misim.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class View implements Serializable {

    private Long videoId;

    private Long views;

    @Builder
    public View(Long videoId, Long views) {
        this.videoId = videoId;
        this.views = views;
    }
}
