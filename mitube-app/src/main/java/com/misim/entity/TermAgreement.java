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

@Entity
@Getter
@Table(name = "term_agreements")
@NoArgsConstructor
public class TermAgreement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TERM_ID")
    private Term term;

    private boolean isAgree;

    @Builder
    public TermAgreement(Boolean isAgree, Term term) {
        this.isAgree = isAgree;
        this.term = term;
    }

    public void setUser(User user) {
        this.user = user;

        if (!user.getTermAgreements().contains(this)) {
            user.getTermAgreements().add(this);
        }
    }
}
