package com.misim.controller.model.Response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Slice;

@Data
@Builder
public class StartWatchingVideoResponse {

    private Long watchingTime;

    private CommentListResponse commentListResponse;
}
