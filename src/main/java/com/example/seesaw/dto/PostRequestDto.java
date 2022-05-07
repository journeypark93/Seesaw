package com.example.seesaw.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String contents;
    private String videoUrl;
    private List<String> tagNames;
    private List<String> postImages;

}