package com.buezman.buezBlogRestApi.repository;

import com.buezman.buezBlogRestApi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
