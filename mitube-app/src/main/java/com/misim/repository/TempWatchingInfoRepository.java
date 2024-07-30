package com.misim.repository;

import com.misim.entity.TempWatchingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempWatchingInfoRepository extends JpaRepository<TempWatchingInfo, Long> {

    Boolean existsByUserIdAndVideoId(Long userId, Long videoId);

    TempWatchingInfo findByUserIdAndVideoId(Long userId, Long videoId);
}
