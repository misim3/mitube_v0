package com.misim.repository;

import com.misim.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Video findTopByUserId(Long userId);

    @Query("SELECT v FROM Video v ORDER BY v.createdDate DESC LIMIT 10")
    List<Video> findTopTen();

    Video findByTitle(String title);
}
