package com.mi.event_api_service.service;

import com.mi.event_api_service.model.MediaEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventMediaService {

    private final StringRedisTemplate redisTemplate;

    public Boolean sentMediaEvent(
            MediaEventRequest mediaEventRequest,
            String idempotencyKey
    ) {
        return isUniqueRequest(idempotencyKey);
    }

    private boolean isUniqueRequest(String idempotencyKey) {
        String redisKey = "idp" + idempotencyKey;

        /*
        * setIfAbsent- In a high-traffic media service, two identical
        * requests might hit different server instances at the exact same millisecond.
        *  Redis handles this "race condition" for you.*/

        /*
        * opsForValue() stands for "Operations for (Simple) Values.
        * */

        /*
        * "PROCESSED" is the value and the content of the value doesn't actually matter.
        * */

        // SET if Not Exists
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(
                redisKey,
                "PROCESSED",
                Duration.ofHours(3)
        );
        return Boolean.TRUE.equals(isNew);
    }

    /**
     * Fetches all idempotency keys and their statuses from Redis
     */
    public Map<String, String> getAllCachedKeys() {
        Map<String, String> results = new HashMap<>();

        // Find all keys that start with "idp"
        Set<String> keys = redisTemplate.keys("idp*");

        if (keys != null) {
            for (String key : keys) {
                String value = redisTemplate.opsForValue().get(key);
                results.put(key, value);
            }
        }
        return results;
    }
}