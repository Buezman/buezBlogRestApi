package com.buezman.buezBlogRestApi.payload;

import com.buezman.buezBlogRestApi.entity.Comment;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments = new HashSet<>();
}
