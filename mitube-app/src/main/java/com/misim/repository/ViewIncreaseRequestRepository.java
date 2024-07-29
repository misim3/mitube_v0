package com.misim.repository;

import com.misim.entity.ViewIncreaseRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewIncreaseRequestRepository extends JpaRepository<ViewIncreaseRequest, Long> {

    @Query("SELECT v.videoId, COUNT(*) as requestCount FROM ViewIncreaseRequest v where v.requestDateTime > :oneWeekAgo GROUP BY v.videoId ORDER BY requestCount DESC LIMIT 10")
    List<Object[]> findTopIncreaseAmountVideoIds(@Param("oneWeekAgo") LocalDateTime oneWeekAgo);

    void deleteByRequestDateTimeBefore(LocalDateTime oneWeekAgo);
}
