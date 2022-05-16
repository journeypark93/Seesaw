package com.example.seesaw.dto;

import com.example.seesaw.model.Trouble;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TroubleDto {

    private String title;
    private String contents;
    private String question;
    private String answer;
    private List<String> tagNames;           // tagName -> tagNames
    private List<String> troubleImages;      // imageUrls -> troubleImages

    public TroubleDto(Trouble trouble, List<String> troubleTagList, List<String> troubleImages) {
        this.title = trouble.getTitle();
        this.contents = trouble.getContents();
        this.question = trouble.getQuestion();
        this.answer = trouble.getAnswer();
        this.tagNames = troubleTagList;
        this.troubleImages = troubleImages;
    }
}
