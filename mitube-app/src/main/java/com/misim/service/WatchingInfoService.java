package com.misim.service;

import com.misim.entity.WatchingInfo;
import com.misim.repository.WatchingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WatchingInfoService {

    private final WatchingInfoRepository watchingInfoRepository;

    @Async
    public void updateWatchingInfo(Long videoId, Long userId, Long watchingTime) {

        WatchingInfo watchingInfo;
        if (watchingInfoRepository.existsByUserIdAndVideoId(userId, videoId)) {
            watchingInfo = watchingInfoRepository.findByUserIdAndVideoId(userId, videoId);
            watchingInfo.setWatchingTime(watchingTime);
        } else {
            watchingInfo = WatchingInfo.builder()
                .userId(userId)
                .videoId(videoId)
                .watchingTime(watchingTime)
                .build();
        }
        watchingInfoRepository.save(watchingInfo);
    }

    @Async
    public void completeWatchingInfo(Long videoId, Long userId, Long watchingTime) {

        WatchingInfo watchingInfo = watchingInfoRepository.findByUserIdAndVideoId(userId, videoId);

        watchingInfo.setWatchingTime(watchingTime);
        watchingInfo.completeWatchingVideo();

        watchingInfoRepository.save(watchingInfo);
    }
}
