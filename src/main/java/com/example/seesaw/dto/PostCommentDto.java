package com.example.seesaw.dto;

import com.example.seesaw.model.PostComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentDto {

    private Long commentId;
    private boolean commentLikeStatus;
    private String nickname;
    private String comment;
    private String commentTime;
    private Long commentLikeCount;
    List<ProfileListDto> ProfileImages;


    public PostCommentDto(PostComment postComment) {
        this.commentId = postComment.getId();
        this.nickname = postComment.getNickname();
        this.comment = postComment.getComment();
    }

}
