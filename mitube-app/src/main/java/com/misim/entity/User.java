package com.misim.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "users", indexes = {@Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_nickname", columnList = "nickname", unique = true)})
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String email;

    @Column(length = 60)
    @Setter
    private String password;

    @Column(length = 20)
    private String nickname;

    private String phoneNumber;

    private boolean isEnabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Setter
    private List<TermAgreement> termAgreements = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Setter
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Setter
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Setter
    private List<Channel> channels = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.isEnabled = true;
    }
}
