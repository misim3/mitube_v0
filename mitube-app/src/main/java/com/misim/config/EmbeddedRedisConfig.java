package com.misim.config;

import jakarta.annotation.PreDestroy;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;


@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @Bean
    public RedisServer redisServer() throws IOException {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
        return redisServer;
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
