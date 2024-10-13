package com.misim.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    private Long videoCatalogId;

    @Builder
    public Reaction(String type, Boolean isActive, User user, Long videoCatalogId) {
        this.type = type;
        this.isActive = isActive;
        this.user = user;
        this.videoCatalogId = videoCatalogId;
    }
}
