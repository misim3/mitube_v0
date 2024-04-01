package com.misim.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class View implements Serializable {

    private Long videoId;

    @Builder
    public View(Long videoId) {
        this.videoId = videoId;
    }
}
