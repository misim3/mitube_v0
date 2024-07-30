package com.misim.repository;

import com.misim.entity.WatchingInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchingInfoRepository extends JpaRepository<WatchingInfo, Long> {

    Optional<WatchingInfo> findByUserIdAndVideoId(Long userId, Long videoId);

    @Query("SELECT w FROM WatchingInfo w WHERE w.userId = :userId And w.isWatchedToEnd = FALSE ORDER BY w.modifiedDate DESC LIMIT 10")
    List<WatchingInfo> findLastTopTenByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE WatchingInfo w " +
        "SET w.watchingTime = (SELECT t.watchingTime " +
                            "FROM TempWatchingInfo t " +
                            "WHERE w.userId = t.userId " +
                            "AND w.videoId = t.videoId) " +
        "WHERE EXISTS (SELECT 1 " +
                        "FROM TempWatchingInfo t " +
                        "WHERE t.userId = w.userId " +
                        "AND t.videoId = w.videoId)")
    void updateWatchingTime();
}
