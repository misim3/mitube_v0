package com.misim.repository;

import com.misim.entity.VideoCatalog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoCatalog, Long> {

    VideoCatalog findTopByUserId(Long userId);

    @Query("SELECT v FROM VideoCatalog v ORDER BY v.createdDate DESC LIMIT 10")
    List<VideoCatalog> findTopTen();

    VideoCatalog findByTitle(String title);
}
