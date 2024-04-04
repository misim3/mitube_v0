package com.misim.controller;

import com.misim.controller.model.Request.SubscribingRequest;
import com.misim.exception.CommonResponse;
import com.misim.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "채널 API", description = "채널 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/channels")
public class ChannelController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "채널 구독", description = "채널을 구독합니다.")
    @Parameter(name = "SubscribingRequest", description = "채널 구독 요청을 위한 정보", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채널 구독 성공"),
            @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/subscribe")
    public void subscribing(@RequestBody SubscribingRequest request) {

        request.check();

        subscriptionService.subscribing(request.getOwnerId(), request.getSubscriberId());
    }

    @Operation(summary = "채널 구독 취소", description = "채널 구독을 취소합니다.")
    @Parameter(name = "SubscribingRequest", description = "채널 구독 취소 요청을 위한 정보", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채널 구독 취소 성공"),
            @ApiResponse(responseCode = "400", description = "요청이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/unsubscribe")
    public void unsubscribing(@RequestBody SubscribingRequest request) {

        request.check();

        subscriptionService.unsubscribing(request.getOwnerId(), request.getSubscriberId());
    }
}
