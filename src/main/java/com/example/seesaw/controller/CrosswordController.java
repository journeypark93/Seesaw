package com.example.seesaw.controller;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.service.Board;
import com.example.seesaw.service.CrosswordServiceTest;
import com.example.seesaw.service.CrosswordServiceVER1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrosswordController {

    // 게임판 단어까지 자동생성
    @GetMapping("/api/crossword")
    public ResponseEntity<String> test(){
        new Board();

        return ResponseEntity.ok()
                .body("");
    }
}
