package com.misim.controller.model.Request;

import com.misim.controller.model.Checker;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import lombok.Data;

@Data
public class SubscribingRequest implements Checker {

    private Long ownerId;

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
