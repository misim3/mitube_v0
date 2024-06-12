package com.misim.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class View implements Serializable {

    private Long videoId;

    @Setter
    private Long count;

    @Builder
    public View(Long videoId, Long count) {
        this.videoId = videoId;
        this.count = count;
    }
}
