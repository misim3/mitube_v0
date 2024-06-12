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

    public Boolean exists(Long videoId) {
        return hashOperations.hasKey(REDIS_KEY, videoId.toString());
    }

    public View findViewByVideoId(Long videoId) {
        return hashOperations.get(REDIS_KEY, videoId.toString());
    }

    public void delete(Long videoId) {
        hashOperations.delete(REDIS_KEY, videoId.toString());
    }

    public View incrementViewCount(Long videoId) {
        View view = hashOperations.get(REDIS_KEY, videoId.toString());
        if (view != null) {
            view.setCount(view.getCount() + 1);
            hashOperations.put(REDIS_KEY, videoId.toString(), view);
        }
        return view;
    }
}
