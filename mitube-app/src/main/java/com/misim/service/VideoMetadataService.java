package com.misim.service;

import com.misim.entity.VideoMetadata;
import com.misim.repository.VideoMetadataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoMetadataService {

    private final VideoMetadataRepository videoMetadataRepository;

    public VideoMetadata createNewVideoMetadata() {

        VideoMetadata metadata = VideoMetadata.builder()
            .viewCount(0L)
            .likeCount(0L)
            .dislikeCount(0L)
            .build();

        return videoMetadataRepository.save(metadata);
    }

    public VideoMetadata readById(Long id) {

        return videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
    }

    public void updateViewCountById(Long id) {

        VideoMetadata metadata = videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        metadata.incrementViewCount();

        videoMetadataRepository.save(metadata);
    }

    public void updateLikeCountById(Long id, boolean isChecked) {

        VideoMetadata metadata = videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        if (isChecked) {
            metadata.incrementLikeCount();
        } else {
            metadata.decrementLikeCount();
        }

        videoMetadataRepository.save(metadata);
    }

    public void updateDislikeCountById(Long id, boolean isChecked) {

        VideoMetadata metadata = videoMetadataRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);

        if (isChecked) {
            metadata.incrementDislikeCount();
        } else {
            metadata.decrementDislikeCount();
        }

        videoMetadataRepository.save(metadata);
    }

    public void deleteById(Long id) {

        videoMetadataRepository.deleteById(id);
    }
}
