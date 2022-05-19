package com.example.seesaw.dto;

import com.example.seesaw.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String generation;
    private String postImage;
    private Long views;
    private Long scrapCount;
    private boolean scrapStatus;

    @Builder
    public PostSearchResponseDto(Long id, String title, String contents, String generation, Long views, Long scrapCount, boolean scrapStatus) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.generation = generation;
        this.views = views;
        this.scrapCount = scrapCount;
        this.scrapStatus = scrapStatus;
    }

    public Post toEntity() {
        Post build = Post.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .generation(generation)
                .build();
        return build;

    }

}
