package com.misim.controller.model.Response;

public record MetadataResponse (
    Long viewCount, Long likeCount, Long dislikeCount
) {
}
