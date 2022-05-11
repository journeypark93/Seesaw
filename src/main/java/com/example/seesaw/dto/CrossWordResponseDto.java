package com.example.seesaw.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrossWordResponseDto {
    private int x; // x좌표
    private int y; // y좌표
    private String oneWord; // 단어
    private String content;

    public CrossWordResponseDto(int x, int y, String oneWord, String content){
        this.x = x;
        this.y = y;
        this.oneWord = oneWord;
        this.content = content;
    }
}
