package com.example.seesaw.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RedisController {


    private final RedisService redisService;

    @GetMapping("/")
    public String test() {
        redisService.redisString();
        return "test";
    }
}
