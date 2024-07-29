package com.misim.entity;

import com.misim.util.TimeUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "view_increase_requests")
@NoArgsConstructor
public class ViewIncreaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long videoId;

    private LocalDateTime requestDateTime;

    @Builder
    public ViewIncreaseRequest(Long videoId) {
        this.videoId = videoId;
        this.requestDateTime = TimeUtil.getNow();
    }
}
