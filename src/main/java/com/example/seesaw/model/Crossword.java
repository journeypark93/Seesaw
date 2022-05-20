package com.example.seesaw.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
    @JoinColumn(name = "QuizNum_ID", nullable = false)
    private QuizNum quizNum;

}
