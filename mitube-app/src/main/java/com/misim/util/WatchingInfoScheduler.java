//package com.misim.util;
//
//import com.misim.service.WatchingInfoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class WatchingInfoScheduler {
//
//    private final WatchingInfoService watchingInfoService;
//
//    @Scheduled(fixedRate = 10000)  // 10초 간격
//    public void scheduledUpdateWatchingInfo() {
//        watchingInfoService.scheduleUpdate();
//    }
//}
