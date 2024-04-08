package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "subscriptions")
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "CHANNEL_ID")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User subscriber;

    @Builder
    public Subscription(Channel channel, User subscriber) {
        this.channel = channel;
        this.subscriber = subscriber;
    }
}
