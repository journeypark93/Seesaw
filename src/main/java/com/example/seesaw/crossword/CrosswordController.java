package com.example.seesaw.crossword;

import com.example.seesaw.dto.CrossWordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrosswordController {

    private final CrosswordService crosswordService;

    // 게임판 단어까지 자동생성
    @GetMapping("/api/crossword")
    public ResponseEntity<List<CrossWordResponseDto>> test(){
        List<CrossWordResponseDto> crossWordResponseDtos = crosswordService.getWord();

        return ResponseEntity.ok()
                .body(crossWordResponseDtos);
    }

    // 단어 답 확인용 api
//    @PostMapping("api/crossword/check")
//    public ResponseEntity<Boolean> crosswordCheck(@RequestBody CrossWordRequestDto crossWordRequestDto){
//
//        boolean check = mockupService.crosswordCheck(crossWordRequestDto);
//
//        return ResponseEntity.ok()
//                .body(check);
//    }
}
