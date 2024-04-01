package com.misim.controller;

import com.misim.controller.model.Request.SubscribingRequest;
import com.misim.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channels")
public class ChannelController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public void subscribing(@RequestBody SubscribingRequest request) {

        request.check();

        subscriptionService.subscribing(request.getOwnerId(), request.getSubscriberId());
    }

    @PostMapping("/unsubscribe")
    public void unsubscribing(@RequestBody SubscribingRequest request) {

        request.check();

        subscriptionService.unsubscribing(request.getOwnerId(), request.getSubscriberId());
    }
}
