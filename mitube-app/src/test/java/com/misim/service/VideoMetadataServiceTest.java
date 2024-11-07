package com.misim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.misim.entity.VideoMetadata;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class VideoMetadataServiceTest {

    @Autowired
    private VideoMetadataService videoMetadataService;

    @Test
    void create() {

        VideoMetadata metadata = videoMetadataService.create();

        assertThat(metadata.getViewCount()).isEqualTo(0);
        assertThat(metadata.getLikeCount()).isEqualTo(0);
        assertThat(metadata.getDislikeCount()).isEqualTo(0);

    }

    @Test
    void read() {

        VideoMetadata metadata1 = videoMetadataService.create();

        VideoMetadata metadata2 = videoMetadataService.read(metadata1.getId());

        assertThat(metadata1.getId()).isEqualTo(metadata2.getId());
        assertThat(metadata1.getViewCount()).isEqualTo(metadata2.getViewCount());
        assertThat(metadata1.getLikeCount()).isEqualTo(metadata2.getLikeCount());
        assertThat(metadata1.getDislikeCount()).isEqualTo(metadata2.getDislikeCount());

    }

    @Test
    void updateViewCount() {

        VideoMetadata metadata1 = videoMetadataService.create();

        videoMetadataService.updateViewCount(metadata1.getId());

        VideoMetadata metadata2 = videoMetadataService.read(metadata1.getId());

        assertThat(metadata2.getViewCount()).isGreaterThan(metadata1.getViewCount());

    }

    @Test
    void updateLikeCount() {

        VideoMetadata metadata1 = videoMetadataService.create();

        videoMetadataService.updateLikeCount(metadata1.getId(), true);

        VideoMetadata metadata2 = videoMetadataService.read(metadata1.getId());

        assertThat(metadata2.getLikeCount()).isGreaterThan(metadata1.getLikeCount());

    }

    @Test
    void updateDislikeCount() {

        VideoMetadata metadata1 = videoMetadataService.create();

        videoMetadataService.updateDislikeCount(metadata1.getId(), true);

        VideoMetadata metadata2 = videoMetadataService.read(metadata1.getId());

        assertThat(metadata2.getDislikeCount()).isGreaterThan(metadata1.getDislikeCount());

    }

    @Test
    void delete() {

        VideoMetadata metadata1 = videoMetadataService.create();

        videoMetadataService.delete(metadata1.getId());

        assertThatThrownBy(() -> videoMetadataService.read(metadata1.getId()))
            .isInstanceOf(NoSuchElementException.class);

    }
}
