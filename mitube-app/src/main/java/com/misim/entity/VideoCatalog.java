package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "videos")
@NoArgsConstructor
public class VideoCatalog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private VideoFile videoFile;

    private Integer categoryId;

    @Builder
    public VideoCatalog(String title, String description, VideoFile videoFile, Integer categoryId) {
        this.title = title;
        this.description = description;
        this.videoFile = videoFile;
        this.categoryId = categoryId;
    }
}
