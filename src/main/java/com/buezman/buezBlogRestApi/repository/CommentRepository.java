package com.buezman.buezBlogRestApi.repository;

import com.buezman.buezBlogRestApi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllCommentsByPostId(Long postId);
}
