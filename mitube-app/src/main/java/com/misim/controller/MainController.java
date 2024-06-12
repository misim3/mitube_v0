package com.misim.controller;

import com.misim.controller.model.Response.HomeResponse;
import com.misim.entity.VideoCategory;
import com.misim.exception.CommonResponse;
import com.misim.service.HomeService;
import com.misim.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final HomeService homeService;

    @Operation(summary = "메인 화면 데이터 전송", description = "메인 화면에 필요한 데이터 전송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "메인 화면 데이터 전송 성공"),
        @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/home")
    public CommonResponse<HomeResponse> home(
        @RequestParam @Parameter(name = "userId", description = "Mitube에 접속한 유저 식별 정보로, 비로그인 사용자의 경우 null로 요청된다.") Long userId) {

        HomeResponse response = homeService.getHome(userId);

        return CommonResponse
            .<HomeResponse>builder()
            .body(response)
            .build();
    }
}
