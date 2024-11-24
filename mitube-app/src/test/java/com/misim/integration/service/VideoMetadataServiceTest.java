package com.misim.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.misim.entity.VideoMetadata;
import com.misim.service.VideoMetadataService;
import jakarta.persistence.EntityNotFoundException;
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

    private static final Long NON_EXISTENT_VIDEO_METADATA_ID = 99999L;

    @Test
    void createNewVideoMetadata_shouldReturnMetadata() {

        VideoMetadata metadata = videoMetadataService.createNewVideoMetadata();

        assertThat(metadata.getId()).isNotNull();
        assertThat(metadata.getViewCount()).isEqualTo(0);
        assertThat(metadata.getLikeCount()).isEqualTo(0);
        assertThat(metadata.getDislikeCount()).isEqualTo(0);

    }

    @Test
    void readVideoMetadataById_shouldReturnMetadata_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());

        assertThat(metadata1.getId()).isEqualTo(metadata2.getId());
        assertThat(metadata1.getViewCount()).isEqualTo(metadata2.getViewCount());
        assertThat(metadata1.getLikeCount()).isEqualTo(metadata2.getLikeCount());
        assertThat(metadata1.getDislikeCount()).isEqualTo(metadata2.getDislikeCount());

    }

    @Test
    void readVideoMetadataById_shouldThrowException_whenIdDoesNotExist() {

        assertThatThrownBy(() -> videoMetadataService.readVideoMetadataById(NON_EXISTENT_VIDEO_METADATA_ID)).isInstanceOf(
            EntityNotFoundException.class);
    }

    @Test
    void updateViewCountById_shouldIncrementViewCount_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateViewCountById(metadata1.getId());

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getId()).isEqualTo(metadata1.getId());
        assertThat(metadata2.getViewCount()).isGreaterThan(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void updateViewCountById_shouldThrowException_whenIdDoesNotExist() {

        assertThatThrownBy(() -> videoMetadataService.updateViewCountById(NON_EXISTENT_VIDEO_METADATA_ID)).isInstanceOf(
            EntityNotFoundException.class);

    }

    @Test
    void updateLikeCountById_shouldIncrementLikeCount_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateLikeCountById(metadata1.getId(), true);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getId()).isEqualTo(metadata1.getId());
        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isGreaterThan(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void updateLikeCountById_shouldDecrementLikeCount_whenIdExistsAndLikeCountIsZero() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateLikeCountById(metadata1.getId(), false);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getId()).isEqualTo(metadata1.getId());
        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void updateLikeCountById_shouldDecrementLikeCount_whenIdExistsAndLikeCountIsGreaterThanZero() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();
        videoMetadataService.updateLikeCountById(metadata1.getId(), true);
        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getId()).isEqualTo(metadata1.getId());
        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isGreaterThan(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

        videoMetadataService.updateLikeCountById(metadata1.getId(), false);

        VideoMetadata metadata3 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata3.getId()).isEqualTo(metadata2.getId());
        assertThat(metadata3.getViewCount()).isEqualTo(metadata2.getViewCount());
        assertThat(metadata3.getLikeCount()).isLessThan(metadata2.getLikeCount());
        assertThat(metadata3.getDislikeCount()).isEqualTo(metadata2.getDislikeCount());

    }

    @Test
    void updateLikeCountById_shouldThrowException_whenIdDoesNotExist() {

        assertThatThrownBy(() -> videoMetadataService.updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true)).isInstanceOf(EntityNotFoundException.class);
        assertThatThrownBy(() -> videoMetadataService.updateLikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false)).isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void updateDislikeCountById_shouldIncrementDislikeCount_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateDislikeCountById(metadata1.getId(), true);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getId()).isEqualTo(metadata1.getId());
        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isGreaterThan(metadata1.getDislikeCount());

    }

    @Test
    void updateLikeCountById_shouldDecrementDislikeCount_whenIdExistsAndDislikeCountIsZero() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.updateDislikeCountById(metadata1.getId(), false);

        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getId()).isEqualTo(metadata1.getId());
        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void updateLikeCountById_shouldDecrementDislikeCount_whenIdExistsAndDislikeCountIsGreaterThanZero() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();
        videoMetadataService.updateDislikeCountById(metadata1.getId(), true);
        VideoMetadata metadata2 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata2.getId()).isEqualTo(metadata1.getId());
        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isGreaterThan(metadata1.getDislikeCount());

        videoMetadataService.updateDislikeCountById(metadata1.getId(), false);

        VideoMetadata metadata3 = videoMetadataService.readVideoMetadataById(metadata1.getId());
        assertThat(metadata3.getId()).isEqualTo(metadata2.getId());
        assertThat(metadata3.getViewCount()).isEqualTo(metadata2.getViewCount());
        assertThat(metadata3.getLikeCount()).isEqualTo(metadata2.getLikeCount());
        assertThat(metadata3.getDislikeCount()).isLessThan(metadata2.getDislikeCount());

    }

    @Test
    void updateDislikeCountById_shouldThrowException_whenIdDoesNotExist() {

        assertThatThrownBy(() -> videoMetadataService.updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, true)).isInstanceOf(
            EntityNotFoundException.class);
        assertThatThrownBy(() -> videoMetadataService.updateDislikeCountById(NON_EXISTENT_VIDEO_METADATA_ID, false)).isInstanceOf(
            EntityNotFoundException.class);

    }

    @Test
    void deleteById_shouldDeleteMetadataMetadata_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        videoMetadataService.deleteMetadataById(metadata1.getId());

        assertThatThrownBy(() -> videoMetadataService.readVideoMetadataById(metadata1.getId()))
            .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void deleteById_shouldDeleteMetadataMetadata_whenIdDoesNotExist() {

        assertThatThrownBy(() -> videoMetadataService.readVideoMetadataById(NON_EXISTENT_VIDEO_METADATA_ID))
            .isInstanceOf(EntityNotFoundException.class);

        videoMetadataService.deleteMetadataById(NON_EXISTENT_VIDEO_METADATA_ID);

        assertThatThrownBy(() -> videoMetadataService.readVideoMetadataById(NON_EXISTENT_VIDEO_METADATA_ID))
            .isInstanceOf(EntityNotFoundException.class);

    }
}
