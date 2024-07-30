package com.misim.service;

import com.misim.entity.TempWatchingInfo;
import com.misim.entity.WatchingInfo;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.TempWatchingInfoRepository;
import com.misim.repository.WatchingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WatchingInfoService {

    private final WatchingInfoRepository watchingInfoRepository;
    private final TempWatchingInfoRepository tempWatchingInfoRepository;

    @Async
    public void updateWatchingInfo(Long videoId, Long userId, Long watchingTime) {

        TempWatchingInfo temp;
        if (tempWatchingInfoRepository.existsByUserIdAndVideoId(userId, videoId)) {

            temp = tempWatchingInfoRepository.findByUserIdAndVideoId(userId, videoId);

        } else {

            WatchingInfo watchingInfo = watchingInfoRepository.findByUserIdAndVideoId(userId, videoId)
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_WATCHING_INFO));

            temp = watchingInfo.convertToTempWatchingInfo();
        }

        temp.addWatchingTime(watchingTime);

        tempWatchingInfoRepository.save(temp);
    }

    @Async
    public void completeWatchingInfo(Long videoId, Long userId, Long watchingTime) {

        WatchingInfo watchingInfo = watchingInfoRepository.findByUserIdAndVideoId(userId, videoId)
            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_WATCHING_INFO));

        watchingInfo.addWatchingTime(watchingTime);
        watchingInfo.completeWatchingVideo();

        watchingInfoRepository.save(watchingInfo);
    }

    @Transactional
    public void scheduleUpdate() {

        watchingInfoRepository.updateWatchingTime();

        tempWatchingInfoRepository.deleteAll();
    }
}
