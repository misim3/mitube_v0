package com.misim.service;

import com.misim.entity.VideoMetadata;
import com.misim.repository.VideoMetadataRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoMetadataService {

    private final VideoMetadataRepository videoMetadataRepository;

    public VideoMetadata create() {

        VideoMetadata metadata = VideoMetadata.builder()
            .viewCount(0L)
            .likeCount(0L)
            .dislikeCount(0L)
            .build();

        return videoMetadataRepository.save(metadata);
    }

    public VideoMetadata read(Long id) {

        return videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
    }

    public Long readViewCount(Long id) {

        return videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new)
            .getViewCount();
    }

    public Long readLikeCount(Long id) {

        return videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new)
            .getLikeCount();
    }

    public Long readDislikeCount(Long id) {

        return videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new)
            .getDislikeCount();
    }

    public void updateViewCount(Long id) {

        VideoMetadata metadata = videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        metadata.incrementViewCount();

        videoMetadataRepository.save(metadata);
    }

    public void updateLikeCount(Long id, boolean isChecked) {

        VideoMetadata metadata = videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        if (isChecked) {
            metadata.incrementLikeCount();
        } else {
            metadata.decrementLikeCount();
        }

        videoMetadataRepository.save(metadata);
    }

    public void updateDislikeCount(Long id, boolean isChecked) {

        VideoMetadata metadata = videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        if (isChecked) {
            metadata.incrementDislikeCount();
        } else {
            metadata.decrementDislikeCount();
        }

        videoMetadataRepository.save(metadata);
    }

    public void delete(Long id) {

        videoMetadataRepository.deleteById(id);
    }
}
