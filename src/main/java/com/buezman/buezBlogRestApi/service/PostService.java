package com.buezman.buezBlogRestApi.service;

import com.buezman.buezBlogRestApi.payload.PostDto;
import org.springframework.data.domain.Page;

public interface PostService {
    PostDto createPost(PostDto postDto);
    Page<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long postId);
    PostDto updatePost(Long postId, PostDto postDto);
    void deletePost(Long postId);
}
