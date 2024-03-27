package com.misim.repository;

import com.misim.entity.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ViewRepository {

    private static final String REDIS_KEY = "views";

    private final ZSetOperations<String, View> zSetOperations;

    public ViewRepository(RedisTemplate<String, View> redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public void save(View view, Long score) {
        zSetOperations.add(REDIS_KEY, view, score);
    }

    public Set<View> findHotTen() {
        return zSetOperations.reverseRange(REDIS_KEY, 0 ,9);
    }
}
