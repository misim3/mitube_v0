package com.misim.repository;

import com.misim.entity.WatchingInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchingInfoRepository extends JpaRepository<WatchingInfo, Long> {

    WatchingInfo findByUserIdAndVideoId(Long userId, Long videoId);

    boolean existsByUserIdAndVideoId(Long userId, Long videoId);

    @Query("SELECT w FROM WatchingInfo w WHERE w.userId = :userId And w.isWatchedToEnd = FALSE ORDER BY w.modifiedDate DESC LIMIT 10")
    List<WatchingInfo> findLastTopTenByUserId(@Param("userId") Long userId);
}
