package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "구독 요청 DTO")
public class SubscribingRequest implements Checker {

    @Schema(name = "channelId", description = "채널 식별 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long channelId;

    @Schema(name = "subscriberId", description = "채널 구독자의 유저 식별 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long subscriberId;

    @Override
    public void check() {

        if (channelId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_SUBSCRIPTION_CHANNEL);
        }

        if (subscriberId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_SUBSCRIPTION_SUBSCRIBER);
        }
    }
}
