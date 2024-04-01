package com.misim.service;

import com.misim.entity.Subscription;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.SubscriptionRepository;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public void subscribing(Long ownerId, Long subscriberId) {

        if (!userRepository.existsById(ownerId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        if (!userRepository.existsById(subscriberId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        Subscription subscription = Subscription.builder()
                .ownerId(ownerId)
                .subscriberId(subscriberId)
                .build();

        subscriptionRepository.save(subscription);
    }

    public void unsubscribing(Long ownerId, Long subscriberId) {

        if (!userRepository.existsById(ownerId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        if (!userRepository.existsById(subscriberId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        subscriptionRepository.deleteByOwnerIdAndSubscriberId(ownerId, subscriberId);
    }
}
