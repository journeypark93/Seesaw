package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CrossWordResponseDto {

    private int x;                  // x좌표
    private int y;                  // y좌표
    private String word;            // 단어
    private String contents;         //단어 설명
    private int wordCount;          //단어 개수
    private boolean isOriental;     //가로면 true, 세로면 false
    private int quizId;          //문제번호
    private int overlappedX;  //겹치는 좌표
    private int overlappedY;  //겹치는 좌표


    public CrossWordResponseDto(int x, int y, String word, String contents){
        this.x = x;
        this.y = y;
        this.word = word;
        this.contents = contents;
    }

}
