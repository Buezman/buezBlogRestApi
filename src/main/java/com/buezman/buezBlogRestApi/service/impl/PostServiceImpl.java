package com.buezman.buezBlogRestApi.service.impl;

import com.buezman.buezBlogRestApi.entity.Post;
import com.buezman.buezBlogRestApi.exception.ResourceNotFoundException;
import com.buezman.buezBlogRestApi.repository.PostRepository;
import com.buezman.buezBlogRestApi.payload.PostDto;
import com.buezman.buezBlogRestApi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapPostDtoToEntity(postDto);
        postRepository.save(post);
        postDto.setId(post.getId());
        return postDto;
    }

    @Override
    public Page<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> pagedPosts = postRepository.findAll(pageable);
        List<PostDto> posts = pagedPosts.stream().map(this::mapPostToDto).toList();

        return new PageImpl<>(posts, pageable, pagedPosts.getTotalElements());
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("post", "id", ""+postId));
        return mapPostToDto(post);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", ""+postId));

        post.setTitle(postDto.getTitle() != null ? postDto.getTitle() : post.getTitle());
        post.setDescription(postDto.getDescription() != null ? postDto.getDescription() : post.getDescription());
        post.setContent(postDto.getContent() != null ? postDto.getContent() : post.getContent());

        postRepository.save(post);
        return mapPostToDto(post);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", ""+postId));
        postRepository.delete(post);
    }

    private Post mapPostDtoToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    private PostDto mapPostToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
