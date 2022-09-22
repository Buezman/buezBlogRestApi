package com.buezman.buezBlogRestApi.controller;

import com.buezman.buezBlogRestApi.payload.PostDto;
import com.buezman.buezBlogRestApi.service.PostService;
import com.buezman.buezBlogRestApi.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto responseBody = postService.createPost(postDto);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<Page<PostDto>> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long postId, @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePost(@PathVariable(name = "id") Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.ok(String.format("Post with id : '%s' deleted successfully", postId));
    }
}
