package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "terms")
@NoArgsConstructor
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Boolean isRequired;

    private Integer version;

    private Integer termGroup;

    @Builder
    public Term(String title, String content, Boolean isRequired, Integer version,
        Integer termGroup) {
        this.title = title;
        this.content = content;
        this.isRequired = isRequired;
        this.version = version;
        this.termGroup = termGroup;
    }
}
