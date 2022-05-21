package com.example.seesaw.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Crossword {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private int x;                  // x좌표

    @Column
    private int y;                 // y좌표

    @Column
    private String word;         // 단어

    @Column
    private String contents;  // 내용

    @Column
    private int wordCount; // 글자수

    @Column
    private boolean isOriental; // 가로면 true, 세로면 false

    @ManyToOne
    @JoinColumn(name = "QuizNum_ID")
    private QuizNum quizNum;

    public Crossword(int x, int y, String word, String contents, int wordCount, boolean isOriental, QuizNum quizNum){
        this.x = x;
        this.y = y;
        this.word = word;
        this.contents = contents;
        this.wordCount = wordCount;
        this.isOriental = isOriental;
        this.quizNum = quizNum;
    }

}
