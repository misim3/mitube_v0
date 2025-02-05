package com.misim.repository;

import com.misim.entity.Term;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("SELECT t1 FROM Term t1 Where (t1.version) IN (SELECT MAX(t2.version) AS max_version FROM Term t2 GROUP BY t2.termGroup) ORDER BY t1.termGroup")
    List<Term> findTermGroupByTermGroupAndMaxVersion();

    @Query("SELECT t1 FROM Term t1 WHERE t1.title = :title AND t1.version = (SELECT MAX(t2.version) FROM Term t2 WHERE t2.title = :title)")
    Optional<Term> findTermByTitleAndMaxVersion(@Param("title") String title);

}