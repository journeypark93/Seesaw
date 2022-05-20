package com.example.seesaw.service;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.model.Crossword;
import com.example.seesaw.model.CrosswordDic;
import com.example.seesaw.model.QuizNum;
import com.example.seesaw.repository.CrosswordRepository;
import com.example.seesaw.repository.QuizNumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CrosswordService {

    private final CrosswordRepository crosswordRepository;
    private final QuizNumRepository quizNumRepository;


    public List<CrossWordResponseDto> getWord(){

        //quiz number 매기기 위해 마지막 quiz num 을 가져온다.
        List<QuizNum> quizNums = quizNumRepository.findAll();
        Long quizNumber = quizNums.get(quizNums.size()-1).getId() + 1;

        //(x,y) = (1,3)부터 시작
        int x = 1; int y = 3;
        int [][] puzzle = new int[10][10]; //10*10 0으로 초기화(검은색, 들어갈 수 있는 칸), 1은 흰색(값이 들어간 곳)


        //단어장의 단어를 가져와서 랜덤한 단어를 뽑는다.
        List<CrosswordDic> crosswordDics = crosswordRepository.findAll();
        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)
        // (0+1~299+1) 1부터 300까지 postId 난수 생성 (전체 등록되어있는 단어 수를 bound 로 잡으면 된다.)
        int postId = random.nextInt(crosswordDics.size()-1) + 1; // 1부터 300 까지
        CrosswordDic firstWord = crosswordDics.get(postId);

        // 가로인지 세로인지 방향 난수로 정하기  (가로가 true 세로가 false)
        boolean isOriental = random.nextBoolean(); // true false 랜덤으로 선택
        System.out.println(isOriental);

        //저장할 리스트 만들기
        List<Crossword> crosswords = new ArrayList<>();
        List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();

        // 가로일 때
        if(isOriental) {
            for (CrosswordDic crosswordDic : crosswordDics){

            }

        }


        //만들어진 crossword, crosswordNum 저장
        crosswordRepository.saveAll(crosswords);
        QuizNum quizNum = new QuizNum(quizNumber, crosswords);
        quizNumRepository.save(quizNum);
        return crossWordResponseDtos;
    }
}
