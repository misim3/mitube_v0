//package com.misim.util;
//
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RedisKeyExpirationListener implements MessageListener {
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        System.out.println(
//            "######## onMessage pattern: " + new String(pattern) + '|' + message.toString());
//    }
//}
