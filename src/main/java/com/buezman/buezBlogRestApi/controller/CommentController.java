package com.buezman.buezBlogRestApi.controller;

import com.buezman.buezBlogRestApi.entity.Comment;
import com.buezman.buezBlogRestApi.exception.BlogApiException;
import com.buezman.buezBlogRestApi.payload.CommentDto;
import com.buezman.buezBlogRestApi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable Long postId) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> findCommentInPost(@PathVariable Long  postId, @PathVariable Long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> editComment (
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto
    ) {
        try{
            return ResponseEntity.ok(commentService.editComment(postId, commentId, commentDto));
        } catch (BlogApiException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        try{
            commentService.deleteComment(postId, commentId);
        } catch (BlogApiException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
        return ResponseEntity.ok(String.format("Comment with id '%s' deleted successfully", commentId));
    }
}
