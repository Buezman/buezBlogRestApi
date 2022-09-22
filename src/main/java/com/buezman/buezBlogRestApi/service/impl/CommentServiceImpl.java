package com.buezman.buezBlogRestApi.service.impl;

import com.buezman.buezBlogRestApi.entity.Comment;
import com.buezman.buezBlogRestApi.entity.Post;
import com.buezman.buezBlogRestApi.exception.BlogApiException;
import com.buezman.buezBlogRestApi.exception.ResourceNotFoundException;
import com.buezman.buezBlogRestApi.payload.CommentDto;
import com.buezman.buezBlogRestApi.repository.CommentRepository;
import com.buezman.buezBlogRestApi.repository.PostRepository;
import com.buezman.buezBlogRestApi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public final class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", "" + postId));
        Comment comment = mapCommentDtoToEntity(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);
        commentDto.setId(comment.getId());
        return commentDto;
    }

    @Override
    public List<CommentDto> findCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllCommentsByPostId(postId);
        return comments.stream().map(this::mapCommentToDto).toList();
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Comment comment = validateComment(postId, commentId);
        return mapCommentToDto(comment);
    }

    @Override
    public CommentDto editComment(Long postId, Long commentId, CommentDto commentDto) {
        Comment comment = validateComment(postId, commentId);
        if (commentDto.getBody() != null) comment.setBody(commentDto.getBody());
        commentRepository.save(comment);

        return mapCommentToDto(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = validateComment(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment mapCommentDtoToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    private CommentDto mapCommentToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment validateComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", "" + commentId));

        if (!comment.getPost().getId().equals(postId)) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post with id " + postId);
        }
        return comment;
    }

    public CommentRepository commentRepository() {
        return commentRepository;
    }

    public PostRepository postRepository() {
        return postRepository;
    }

    public ModelMapper modelMapper() {
        return modelMapper;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CommentServiceImpl) obj;
        return Objects.equals(this.commentRepository, that.commentRepository) &&
                Objects.equals(this.postRepository, that.postRepository) &&
                Objects.equals(this.modelMapper, that.modelMapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentRepository, postRepository, modelMapper);
    }

    @Override
    public String toString() {
        return "CommentServiceImpl[" +
                "commentRepository=" + commentRepository + ", " +
                "postRepository=" + postRepository + ", " +
                "modelMapper=" + modelMapper + ']';
    }

}
