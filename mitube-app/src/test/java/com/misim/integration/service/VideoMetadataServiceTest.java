package com.misim.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.misim.entity.VideoMetadata;
import com.misim.service.VideoMetadataService;
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
    void createNewVideoMetadata() {

        VideoMetadata metadata = videoMetadataService.createNewVideoMetadata();

        assertThat(metadata.getViewCount()).isEqualTo(0);
        assertThat(metadata.getLikeCount()).isEqualTo(0);
        assertThat(metadata.getDislikeCount()).isEqualTo(0);

    }

    @Test
    void readVideoMetadataById() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());

        assertThat(metadata1.getId()).isEqualTo(metadata2.getId());
        assertThat(metadata1.getViewCount()).isEqualTo(metadata2.getViewCount());
        assertThat(metadata1.getLikeCount()).isEqualTo(metadata2.getLikeCount());
        assertThat(metadata1.getDislikeCount()).isEqualTo(metadata2.getDislikeCount());

    }

    @Test
    void read_ById_throw_exception() {

        Long id = 9999L;

        assertThatThrownBy(() -> videoMetadataService.readVideoMetadataById(id))
            .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    void updateViewCountById() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateViewCountById(metadata1.getId());

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getViewCount()).isGreaterThan(metadata1.getViewCount());

    }

    @Test
    void updateViewCount_ById_throw_exception() {

        Long id = 9999L;

        assertThatThrownBy(() -> videoMetadataService.updateViewCountById(id))
            .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    void updateLikeCount_ById_up() {

        boolean isChecked = true;
        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateLikeCountById(metadata1.getId(), isChecked);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getLikeCount()).isGreaterThan(metadata1.getLikeCount());

    }

    @Test
    void updateLikeCount_ById_down() {

        boolean isChecked = false;
        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateLikeCountById(metadata1.getId(), isChecked);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getLikeCount()).isLessThanOrEqualTo(metadata1.getLikeCount());

    }

    @Test
    void updateLikeCount_ById_throw_exception() {

        Long id = 9999L;
        boolean isChecked = true;

        assertThatThrownBy(() -> videoMetadataService.updateLikeCountById(id, isChecked))
            .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    void updateDislikeCount_ById_up() {

        boolean isChecked = true;
        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateDislikeCountById(metadata1.getId(), isChecked);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getDislikeCount()).isGreaterThan(metadata1.getDislikeCount());

    }

    @Test
    void updateDislikeCount_ById_down() {

        boolean isChecked = false;
        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateDislikeCountById(metadata1.getId(), isChecked);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getDislikeCount()).isLessThanOrEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void updateDislikeCount_ById_throw_exception() {

        Long id = 9999L;
        boolean isChecked = true;

        assertThatThrownBy(() -> videoMetadataService.updateDislikeCountById(id, isChecked))
            .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    void deleteById() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.deleteById(metadata1.getId());

        assertThatThrownBy(() -> videoMetadataService.readVideoMetadataById(metadata1.getId()))
            .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    void delete_ById_noSuchElement() {

        Long id = 9999L;

        assertThatThrownBy(() -> videoMetadataService.readVideoMetadataById(id))
            .isInstanceOf(NoSuchElementException.class);

        videoMetadataService.deleteById(id);

    }
}
