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

    private boolean commentLikeStatus; // 좋아요 눌렀는지 상태
    private String commentTime; // 댓글 등록한 시간
    private String nickname; // 닉네임
    private String comment;
    private Long likeCount;
    List<ProfileListDto> ProfileImages;

    public TroubleCommentRequestDto(TroubleComment troubleComment) {
        this.nickname = troubleComment.getNickname();
        this.comment = troubleComment.getComment();
    }

}
