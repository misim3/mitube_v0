package com.misim.repository;

import com.misim.entity.Reaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Boolean existsReactionByUserIdAndVideoCatalogId(Long userId, Long videoCatalogId);

    Optional<Reaction> findByUserIdAndVideoCatalogId(Long userId, Long videoCatalogId);
}
