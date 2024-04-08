package com.misim.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "channels")
@NoArgsConstructor
public class Channel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    @Setter
    @OneToMany
    private List<Subscription> subscriptionList = new ArrayList<>();

    public Channel(String title, String description, User owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }
}
