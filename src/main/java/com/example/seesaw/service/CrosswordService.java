package com.example.seesaw.service;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.model.CrosswordDic;
import com.example.seesaw.repository.CrosswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CrosswordService {

    private final CrosswordRepository crosswordRepository;

    public List<CrossWordResponseDto> getWord(){
        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)
        // (0+1~299+1) 1부터 300까지 postId 난수 생성 (전체 등록되어있는 단어 수를 bound 로 잡으면 된다.)
        Long postId = (long) (random.nextInt(300) + 1); // 1부터 300 까지
        //(x,y) = (1,3)부터 시작
        int x = 1; int y = 3;
        //findById 는 optional 로 가져와야합니다.
        List<CrosswordDic> crosswordDics = crosswordRepository.findAllById(postId);

        // 가로인지 세로인지 방향 난수로 정하기  (가로가 true 세로가 false)
        boolean isOriental = random.nextBoolean(); // true false 랜덤으로 선택
        System.out.println(isOriental);
        // 가로일 때
        List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();
        for (CrosswordDic crosswordDic : crosswordDics){


        }

        return crossWordResponseDtos;
    }
}
