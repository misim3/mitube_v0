package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.netty.channel.ChannelHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "구독 요청 DTO")
public class SubscribingRequest implements Checker {

    @Schema(name = "ownerId", description = "채널 소유주의 유저 식별 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long ownerId;

    @Schema(name = "subscriberId", description = "채널 구독자의 유저 식별 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long subscriberId;

    @Override
    public void check() {

        if (ownerId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_SUBSCRIPTION_OWNER);
        }

        if (subscriberId == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_SUBSCRIPTION_SUBSCRIBER);
        }
    }
}
