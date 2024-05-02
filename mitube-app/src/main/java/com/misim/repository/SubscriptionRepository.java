package com.misim.repository;

import com.misim.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findSubscriptionsBySubscriberId(Long subscriberId);

    Optional<Subscription> findByChannelIdAndSubscriberId(Long channelId, Long subscriberId);
}
