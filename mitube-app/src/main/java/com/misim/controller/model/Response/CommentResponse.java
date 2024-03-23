package com.misim.controller.model.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentResponse {

    private String content;

    private String writerNickname;

    private List<CommentResponse> childComments;
}
