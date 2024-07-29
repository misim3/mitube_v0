package com.misim.service;

import com.misim.repository.ViewIncreaseRequestRepository;
import com.misim.util.TimeUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ViewIncreaseRequestService {

    private final ViewIncreaseRequestRepository viewIncreaseRequestRepository;

    public List<Long> getTopIncreasesForLastWeek() {
        LocalDateTime oneWeekAgo = TimeUtil.getNow().minusDays(7);
        List<Object[]> result = viewIncreaseRequestRepository.findTopIncreaseAmountVideoIds(oneWeekAgo);
        return result.stream()
            .map(row -> ((Number) row[0]).longValue())
            .toList();
    }

    @Scheduled(fixedRate = 3600000) // 1시간 간격으로 실행
    @Transactional
    public void cleanOldRecords() {
        LocalDateTime oneWeekAgo = TimeUtil.getNow().minusDays(7).plusMinutes(1);
        viewIncreaseRequestRepository.deleteByRequestDateTimeBefore(oneWeekAgo);
    }
}
