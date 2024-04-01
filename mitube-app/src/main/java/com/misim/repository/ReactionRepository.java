package com.misim.repository;

import com.misim.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Boolean existsReactionByUserIdAndVideoId(Long userId, Long videoId);

    Reaction findByUserIdAndVideoId(Long userId, Long videoId);
}
