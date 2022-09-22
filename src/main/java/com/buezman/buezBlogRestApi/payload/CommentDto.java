package com.buezman.buezBlogRestApi.payload;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    @NotEmpty(message = "name is required")
    private String name;
    @NotEmpty(message = "email is required")
    @Email
    private String email;
    @NotEmpty
    private String body;
}
