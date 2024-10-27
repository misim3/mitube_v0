package com.misim.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "video_catalog")
@NoArgsConstructor
public class VideoCatalog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer categoryId;

    @OneToOne
    private VideoFile videoFile;

    @OneToOne
    private VideoMetadata videoMetadata;

    @Builder
    public VideoCatalog(String title, String description, Integer categoryId, VideoFile videoFile, VideoMetadata videoMetadata) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.videoFile = videoFile;
        this.videoMetadata = videoMetadata;
    }
}
