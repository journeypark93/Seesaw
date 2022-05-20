package com.example.seesaw.repository;


import com.example.seesaw.model.Crossword;
import com.example.seesaw.model.QuizNum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuizNumRepository extends JpaRepository<QuizNum, Long> {

    //게임용
    List<QuizNum> findAllById(Long postId);

}
