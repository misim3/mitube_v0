package com.misim.service;

import com.misim.entity.Channel;
import com.misim.entity.Subscription;
import com.misim.entity.User;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.ChannelRepository;
import com.misim.repository.SubscriptionRepository;
import com.misim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public void subscribing(Long channelId, Long subscriberId) {

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_CHANNEL));

        User subscriber = userRepository.findById(subscriberId)
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_SUBSCRIBER));

        Subscription subscription = Subscription.builder()
                .channel(channel)
                .user(subscriber)
                .build();

        subscriptionRepository.save(subscription);
    }

    public void unsubscribing(Long channelId, Long subscriberId) {

        if (!channelRepository.existsById(channelId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_CHANNEL);
        }

        if (!userRepository.existsById(subscriberId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_SUBSCRIBER);
        }

        Subscription subscription = subscriptionRepository.findByChannelIdAndUserId(channelId, subscriberId)
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_SUBSCRIPTION));

        subscriptionRepository.delete(subscription);
    }
}
