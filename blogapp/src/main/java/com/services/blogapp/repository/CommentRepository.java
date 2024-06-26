package com.services.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Blog;
import com.services.blogapp.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	void deleteByBlog(Blog blog);

	List<Comment> findByBlog(Blog blog);

	List<Comment> findByBlog_blogId(int blogId);

}
