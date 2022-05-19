package com.example.seesaw.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void redisString() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("test", "test");
        String redis = (String)operations.get("test");
        log.info(redis);
    }

    // 키-벨류 설정
    public void setValues(String email, String refresh){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        values.set(name, age);
        values.set(email, refresh,Duration.ofDays(7));
    }

    // 키값으로 벨류 가져오기
    public String getValues(String email){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(email);
    }

    // 키-벨류 삭제
    public void delValues(String email) {
        redisTemplate.delete(email);
    }

}
