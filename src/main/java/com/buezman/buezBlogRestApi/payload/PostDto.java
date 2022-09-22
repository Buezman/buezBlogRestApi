package com.buezman.buezBlogRestApi.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 50, message = "title should have between 2 - 50 characters")
    private String title;

    @NotEmpty
    @Size(min = 5, max = 150, message = "description should have between 5 - 150 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments = new HashSet<>();
}
