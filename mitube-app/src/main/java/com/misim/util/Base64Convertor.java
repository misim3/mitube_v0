package com.misim.util;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import java.util.Base64;

public class Base64Convertor {

    public static String encode(Long key) {

        byte[] originalBytes = key.toString().getBytes();

        return Base64.getEncoder().encodeToString(originalBytes);
    }

    public static Long decode(String token) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedString = new String(decodedBytes);
            return Long.valueOf(decodedString);
        } catch (IllegalArgumentException e) {
            throw new MitubeException(MitubeErrorCode.INVALID_SMS_TOKEN);
        }
    }
}
