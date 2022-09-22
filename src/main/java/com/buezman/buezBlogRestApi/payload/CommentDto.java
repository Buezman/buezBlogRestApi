package com.buezman.buezBlogRestApi.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
