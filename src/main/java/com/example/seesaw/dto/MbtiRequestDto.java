package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiRequestDto {

    private String id;
    private String energy;
    private String insight;
    private String judgement;
    private String lifePattern;

    public MbtiRequestDto(String energy, String insight, String judgement, String lifePattern){
        this.energy = energy;
        this.insight = insight;
        this.judgement = judgement;
        this.lifePattern = lifePattern;
    }
}
