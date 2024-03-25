package com.misim.controller.model.Response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@Builder
public class CommentListResponse {

    private Slice<CommentResponse> commentResponses;
}
