package com.misim.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "reactions")
@NoArgsConstructor
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String type;

    @Setter
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "VIDEO_ID")
    private Video video;

    @Builder
    public Reaction(String type, Boolean isActive, User user, Video video) {
        this.type = type;
        this.isActive = isActive;
        this.user = user;
        this.video = video;
    }
}
