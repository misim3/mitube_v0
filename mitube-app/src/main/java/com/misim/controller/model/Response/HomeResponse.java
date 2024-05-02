package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(name = "메인 페이지 정보 응답 DTO")
public class HomeResponse {

    @Schema(name = "categoryList", description = "전체 동영상 카테고리 목록", example = "영화, 음악, ...", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> categoryList;

    @Schema(name = "newVideoList", description = "최신 동영상 목록 10개", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VideoResponse> newVideoList;

    @Schema(name = "hotVideoList", description = "인기 동영상 목록 10개", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VideoResponse> hotVideoList;

    @Schema(name = "watchingVideoList", description = "로그인한 유저가 시청 중인 동영상 목록 10개. 시청 기록이 없는 유저의 경우 빈 리스트 반환. 로그인하지 않은 유저는 null를 반환받는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    private List<VideoResponse> watchingVideoList;

    @Schema(name = "subscribingChannelNewVideoList", description = "로그인한 유저가 구독 중인 채널의 최신 동영상 목록 10개. 로그인하지 않은 유저는 빈 리스트를 반환받는다.", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
    private List<VideoResponse> subscribingChannelNewVideoList;
}
