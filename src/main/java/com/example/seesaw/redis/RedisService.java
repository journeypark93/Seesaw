package com.example.seesaw.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 키-벨류 설정
    public void setValues(String id, String pwd){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(id, pwd, Duration.ofDays(1));
    }

    // 키값으로 벨류 가져오기
    public String getValues(String id){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(id);
    }

    // 키-벨류 삭제
    public void delValues(String username) {
        redisTemplate.delete(username);
    }
}
