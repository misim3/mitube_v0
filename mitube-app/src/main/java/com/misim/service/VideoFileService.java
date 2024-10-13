package com.misim.service;

import com.misim.entity.VideoFile;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.VideoFileRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoFileService {

    private final VideoFileRepository videoFileRepository;

    public VideoFileService(VideoFileRepository videoFileRepository) {
        this.videoFileRepository = videoFileRepository;
    }

    public VideoFile getVideoFile(Long videoFileId) {
        return videoFileRepository.findById(videoFileId)
            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO_FILE));
    }
}
