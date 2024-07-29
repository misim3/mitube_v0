package com.misim.service;

import com.misim.entity.Video;
import com.misim.entity.ViewIncreaseRequest;
import com.misim.entity.WatchingInfo;
import com.misim.repository.VideoRepository;
import com.misim.repository.ViewIncreaseRequestRepository;
import com.misim.repository.WatchingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final VideoRepository videoRepository;
    private final WatchingInfoRepository watchingInfoRepository;
    private final ViewIncreaseRequestRepository viewIncreaseRequestRepository;

    @Async
    public void startWatchingVideo(Video video, WatchingInfo watchingInfo) {
        videoRepository.save(video);
        if (watchingInfo.getId() == null) {
            watchingInfoRepository.save(watchingInfo);
        }
        viewIncreaseRequestRepository.save(ViewIncreaseRequest.builder()
            .videoId(video.getId())
            .build()
        );
    }
}
