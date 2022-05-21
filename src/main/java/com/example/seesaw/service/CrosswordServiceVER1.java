package com.example.seesaw.service;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.model.Crossword;
import com.example.seesaw.model.CrosswordDic;
import com.example.seesaw.model.QuizNum;
import com.example.seesaw.repository.CrosswordDicRepository;
import com.example.seesaw.repository.CrosswordRepository;
import com.example.seesaw.repository.QuizNumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CrosswordServiceVER1 {

    private final CrosswordRepository crosswordRepository;
    private final CrosswordDicRepository crosswordDicRepository;
    private final QuizNumRepository quizNumRepository;


    public List<CrossWordResponseDto> getWord(){

        //quiz number 매기기 위해 마지막 quiz num 을 가져온다.
        //List<QuizNum> quizNums = quizNumRepository.findAll();
        //Long quizNumber = quizNums.get(quizNums.size()-1).getId() + 1;
        Long quizNumber = 1L;
        QuizNum quizNum = new QuizNum(quizNumber);
        quizNumRepository.save(quizNum);

        //(x,y) = (1,3)부터 시작
        int x = 1; int y = 3;
        int [][] puzzle = new int[10][10]; //10*10 0으로 초기화(검은색, 들어갈 수 있는 칸), 1은 흰색(값이 들어간 곳)


        Random random = new Random(); // 랜덤 객체 생성
        CrosswordDic firstWord = new CrosswordDic();
        //단어장의 단어를 가져와서 랜덤한 단어를 뽑는다.
        List<CrosswordDic> crosswordDics = crosswordDicRepository.findAll();
        for(int i=0; i<crosswordDics.size(); i++) {
            random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)
            // (0+1~299+1) 1부터 300까지 postId 난수 생성 (전체 등록되어있는 단어 수를 bound 로 잡으면 된다.)
            int postId = random.nextInt(crosswordDics.size()-1) + 1; // 1부터 300 까지
            CrosswordDic word = crosswordDics.get(postId);
            if(word.getTitle().length() >1 && getType(word.getTitle())) { //2글자 이상, 한글인 경우에만
                firstWord = word;
                break;
            }
        }


        // 가로인지 세로인지 방향 난수로 정하기  (가로가 true 세로가 false)
        boolean isOriental = random.nextBoolean(); // true false 랜덤으로 선택
        System.out.println(isOriental);

        //저장할 리스트 만들기
        List<Crossword> crosswords = new ArrayList<>();

        // 가로일 때
        if(isOriental) {
            loop :
            for (CrosswordDic secondWord : crosswordDics){
                //두번째 단어는 첫번째 단어와 중복되면 안되며, 2글자 이상 10자 이하, 한글이어야 한다.
                if(!secondWord.getTitle().equals(firstWord.getTitle()) && secondWord.getTitle().length()>1 && secondWord.getTitle().length() <11 && getType(secondWord.getTitle())){

                    //검사
                    for(int i=0;i<firstWord.getTitle().length();i++) {
                        for(int j=0;j<secondWord.getTitle().length();j++) {
                            if(secondWord.getTitle().charAt(j) == firstWord.getTitle().charAt(i)) {
                                if(j<4 && x+firstWord.getTitle().length()<10) {  //겹치는 두번째 글자가 x 좌표가 0을 벗어나지 않고, 첫번째 글자의 y 좌표가 9를 벗어나지 않는다.
                                    Crossword crossword1 = new Crossword(x, y, firstWord.getTitle(), firstWord.getContents(), firstWord.getTitle().length(), true, quizNum);
                                    crosswords.add(crossword1);
                                    for(int k=0; k<firstWord.getTitle().length(); k++){
                                        puzzle[x][y]  = 1;
                                        x++;
                                    }
                                    int y1 = y-j;
                                    int x1 = i+1;
                                    Crossword crossword2 = new Crossword(x1, y1, secondWord.getTitle(), secondWord.getContents(), secondWord.getTitle().length(), false, quizNum);
                                    crosswords.add(crossword2);
                                    for(int h=0; h<secondWord.getTitle().length(); h++){
                                        puzzle[x1][y1] = 1;
                                        y1 += 1;
                                    }
                                    break loop;
                                }
                            }
                        }
                    }

                }
            }

            //세번째 단어어

        }

        //만들어진 crossword, crosswordNum 저장
        crosswordRepository.saveAll(crosswords);
        List<Crossword> crosswordList = crosswordRepository.findAllByQuizNum(quizNum);
        List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();
        for(Crossword crossword:crosswordList){
            crossWordResponseDtos.add(new CrossWordResponseDto(crossword));
        }
        return crossWordResponseDtos;
    }




    public boolean getType(String word) {
        boolean result = true;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i);

            if (index >= 48 && index <= 57) {
                result = false;
            } else if (index >= 65 && index <= 122) {
                result = false;
            }
        }
        return result;
    }
}
