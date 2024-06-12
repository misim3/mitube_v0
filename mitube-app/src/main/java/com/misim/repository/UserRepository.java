package com.misim.repository;

import com.misim.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    User findByNickname(String nickname);
}
