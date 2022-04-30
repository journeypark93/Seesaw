package com.sparta.odict.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.odict.dto.KakaoGenerationDto;
import com.sparta.odict.security.UserDetailsImpl;
import com.sparta.odict.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class KakaoUserController {
    private final KakaoUserService kakaoUserService;
    private final String ACCESS_TOKEN = "AccessToken";
    private final String REFRESH_TOKEN = "RefreshToken";
    private final String TOKEN_TYPE = "BEARER";

    //    @ApiOperation("카카오 로그인")
    @GetMapping("/user/kakao/callback")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        System.out.println("code : " + code);

        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        List<String> tokens = kakaoUserService.kakaoLogin(code);

        System.out.println("kakao accesstoken : " + tokens.get(0));
        System.out.println("kakao refreshtoken : " + tokens.get(1));

        response.addHeader(ACCESS_TOKEN, TOKEN_TYPE + "  " + tokens.get(0));
        response.addHeader(REFRESH_TOKEN, TOKEN_TYPE + "  " + tokens.get(1));
    }

    @PostMapping("/kakao/generation")
    public ResponseEntity<String> registerUser(@RequestBody KakaoGenerationDto kakaoGenerationDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        kakaoUserService.userGeneration(kakaoGenerationDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body("카카오유저 세대저장 완료");
    }
}