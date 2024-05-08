package com.misim.repository;

import com.misim.entity.View;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ViewRepository {

    private static final String REDIS_KEY = "views";

    private final HashOperations<String, String, View> hashOperations;

    public ViewRepository(RedisTemplate<String, View> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(View view) {
        hashOperations.put(REDIS_KEY, view.getVideoId().toString(), view);
    }

    public Boolean exists(String videoId) {
        return hashOperations.hasKey(REDIS_KEY, videoId);
    }

    public View findViewByVideoId(String videoId) {
        return hashOperations.get(REDIS_KEY, videoId);
    }

    public void delete(String videoId) {
        hashOperations.delete(REDIS_KEY, videoId);
    }

    public void incrementViewCount(String videoId) {
        hashOperations.increment(REDIS_KEY, videoId, 1);
    }
}
