package com.example.seesaw.dto;

import com.example.seesaw.model.Crossword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrossWordResponseDto {
    private Long id;                 // 단어 id
    private int x;                  // x좌표 (시작 좌표)
    private int y;                 // y좌표  (시작 좌표)
    private String word;         // 단어
    private String contents;  // 내용
    private int wordCount; // 글자수
    private boolean isOriental; // 가로면 true, 세로면 false
    private Long quizId; // 퀴즈 번호
//    private int overlappedX; // 겹치는 x 좌표
//    private int overlappedY; // 겹치는 y 좌쵸

    public CrossWordResponseDto(Crossword crossword){
        this.id = crossword.getId();
        this.x = crossword.getX();
        this.y = crossword.getY();
        this.word = crossword.getWord();
        this.contents = crossword.getContents();
        this.wordCount = crossword.getWordCount();
        this.isOriental = crossword.isOriental();
        this.quizId = crossword.getQuizNum().getId();
//        this.overlappedX = crossword.getOverlappedX();
//        this.overlappedY = crossword.getOverlappedY();
    }

}
