package com.example.seesaw.repository;

import com.example.seesaw.model.Crossword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrosswordRepository extends JpaRepository<Crossword, Long> {
    //목업용 (지울예정)
//    List<Crossword> findAllByQuizNum(QuizNum quizNum);
//
    List<Crossword> findAllByQuizNumId(Long quizId);
}
