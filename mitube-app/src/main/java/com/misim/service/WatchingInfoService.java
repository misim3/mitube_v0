package com.misim.service;

import com.misim.entity.WatchingInfo;
import com.misim.repository.TempWatchingInfoRepository;
import com.misim.repository.WatchingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WatchingInfoService {

    private final WatchingInfoRepository watchingInfoRepository;
    private final TempWatchingInfoRepository tempWatchingInfoRepository;

    @Async
    public void updateWatchingInfo(Long videoId, Long userId, Long watchingTime) {

//        TempWatchingInfo temp;
//        if (tempWatchingInfoRepository.existsByUserIdAndVideoId(userId, videoId)) {
//
//            temp = tempWatchingInfoRepository.findByUserIdAndVideoId(userId, videoId);
//
//        } else if (watchingInfoRepository.existsByUserIdAndVideoId(userId, videoId)) {
//
//            WatchingInfo watchingInfo = watchingInfoRepository.findByUserIdAndVideoId(userId, videoId);
//
//            temp = watchingInfo.convertToTempWatchingInfo();
//        } else {
//
//            WatchingInfo watchingInfo = WatchingInfo.builder()
//                .userId(userId)
//                .videoId(videoId)
//                .watchingTime(watchingTime)
//                .build();
//
//            temp = watchingInfoRepository.save(watchingInfo).convertToTempWatchingInfo();
//        }
//
//        temp.setWatchingTime(watchingTime);
//
//        tempWatchingInfoRepository.save(temp);

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

//    @Transactional
//    public void scheduleUpdate() {
//
//        watchingInfoRepository.saveAll(tempWatchingInfoRepository.findAll()
//            .stream()
//            .map(TempWatchingInfo::convertToWatchingInfo).toList()
//        );
//
//        tempWatchingInfoRepository.deleteAll();
//    }
}
