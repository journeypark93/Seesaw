package com.example.seesaw.service;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.model.Crossword;
import com.example.seesaw.model.CrosswordDic;
import com.example.seesaw.model.QuizNum;
import com.example.seesaw.repository.CrosswordDicRepository;
import com.example.seesaw.repository.CrosswordRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CrosswordService {

    private final CrosswordDicRepository crosswordDicRepository;
    private final CrosswordRepository crosswordRepository;

    String name = "";
    @Override
    public String toString(){
        return name;
    }
    public List<CrossWordResponseDto> getWord() {
        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)
        // (0+1~299+1) 1부터 300까지 postId 난수 생성 (전체 등록되어있는 단어 수를 bound 로 잡으면 된다.)
        Long postId = (long) (random.nextInt(350) + 1); // 1부터 300 까지
        //(x,y) = (1,1)부터 시작
        int x = 1;
        int y = 1;
        //findById 는 optional 로 가져와야합니다.  첫 단어만 메서드 밖에 생성
        List<CrosswordDic> crosswordDics = crosswordDicRepository.findAllById(postId);

        // 가로인지 세로인지 방향 난수로 정하기  (가로가 true 세로가 false)
        boolean isOriental = random.nextBoolean(); // true false 랜덤으로 선택
//        String name = "";
        name = crosswordDics.get(0).toString();
        System.out.println(name);
        System.out.println(isOriental); // 가로세로 방향 잘 정해졌는지 체크

        // 메서드 호출 (시작 x좌표, 시작 y좌표, 첫단어, 첫 방향)
        return autoWord(x, y, crosswordDics.get(0).getTitle(), isOriental);
    }

    // 자동으로 생성할 단어 메소드
    public List<CrossWordResponseDto> autoWord(int x, int y, String word, boolean isOriental) {
        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)

        // 단어 시작 첫 좌표, 방향, 글자 수, 정답들 담을 리스트
        List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();

        // 들어오는 단어 글자 수 파악하고 다음 단어 찾기
        String[] str = word.split("");
        int wordCount = str.length; // 글자 수
        // 마지막 글자가 포함되어있는 단어를 DB에서 찾는다.
        List<CrosswordDic> crosswordDicList = crosswordDicRepository.findByTitleContaining(str[wordCount-1]);
        int index = random.nextInt(crosswordDicList.size()); // 랜덤값 저장.
        // 종료 조건
        if(index == 0){
            return crossWordResponseDtos;
        }
        // 단어를 찾았다.
        CrosswordDic crosswordDic = crosswordDicList.get(index);
        String crossword = crosswordDic.getTitle();
        // 가로일때
        if (isOriental) {
            x += wordCount;
            isOriental = false;
        } else { // 세로일때
            y += wordCount;
            isOriental = true;
        }


        Crossword crossword1 = new Crossword(x,y,crossword,crosswordDic.getContents(),wordCount,isOriental,x,y);
        crosswordRepository.save(crossword1);
        List<Crossword> crosswords = new ArrayList<>();
        crosswords.add(crossword1);

        crossWordResponseDtos.add(new CrossWordResponseDto(crossword1));
        // 다음 단어 찾으러 출발.
        autoWord(x, y, crossword, isOriental);

        return crossWordResponseDtos;
    }
}
