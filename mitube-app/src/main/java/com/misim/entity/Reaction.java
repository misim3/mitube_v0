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

    private Long userId;

    private Long videoId;

    @Builder
    public Reaction(String type, Boolean isActive, Long userId, Long videoId) {
        this.type = type;
        this.isActive = isActive;
        this.userId = userId;
        this.videoId = videoId;
    }
}
