package com.example.seesaw.repository;


import com.example.seesaw.model.CrosswordDic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CrosswordDicRepository extends JpaRepository<CrosswordDic, Long> {

    //게임용
    List<CrosswordDic> findAllById(Long postId);

}
