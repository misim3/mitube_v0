package com.misim.unit.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.misim.entity.VideoMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoMetadataTest {

    private VideoMetadata metadata;

    @BeforeEach
    void setUp() {
        metadata = VideoMetadata.builder()
            .viewCount(0L)
            .likeCount(0L)
            .dislikeCount(0L)
            .build();
    }

    @Test
    void incrementLikeCount() {

        metadata.incrementLikeCount();

        assertThat(metadata.getLikeCount()).isEqualTo(1L);

    }

    @Test
    void incrementDislikeCount() {

        metadata.incrementDislikeCount();

        assertThat(metadata.getDislikeCount()).isEqualTo(1L);

    }

    @Test
    void decrementLikeCount_whenLikeCountIsZero() {

        metadata.decrementLikeCount();

        assertThat(metadata.getLikeCount()).isEqualTo(0L);

    }

    @Test
    void decrementLikeCount_whenLikeCountIsGreaterThanZero() {

        assertThat(metadata.getLikeCount()).isEqualTo(0L);
        metadata.incrementLikeCount();
        assertThat(metadata.getLikeCount()).isEqualTo(1L);

        metadata.decrementLikeCount();

        assertThat(metadata.getLikeCount()).isEqualTo(0L);

    }

    @Test
    void decrementDislikeCount_whenLikeCountIsZero() {

        assertThat(metadata.getDislikeCount()).isEqualTo(0L);
        metadata.incrementDislikeCount();
        assertThat(metadata.getDislikeCount()).isEqualTo(1L);

        metadata.decrementDislikeCount();

        assertThat(metadata.getDislikeCount()).isEqualTo(0L);

    }
}
