package com.example.seesaw.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Crossword {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int wordCount;          //단어 개수

    @Column(nullable = false)
    private boolean isOriental;     //가로면 true, 세로면 false

    @Column(nullable = false)
    private int overlappedX;  //겹치는 좌표

    @Column(nullable = false)
    private int overlappedY;  //겹치는 좌표

    @ManyToOne
    @JoinColumn(name = "QUIZNUM_ID", nullable = false)
    private QuizNum quizNum;
}
