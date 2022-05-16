package com.example.seesaw.dto;

import com.example.seesaw.model.TroubleComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

@AllArgsConstructor
@NoArgsConstructor
public class TroubleCommentRequestDto {

    private String nickname;
    private String comment;
    private Long likeCount;
    List<ProfileListDto> ProfileImages;

    public TroubleCommentRequestDto(TroubleComment troubleComment) {
    this.nickname = troubleComment.getNickname();
    this.comment = troubleComment.getComment();
    this.likeCount = troubleComment.getLikeCount();
    }

}
