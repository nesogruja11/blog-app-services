package com.services.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Blog;
import com.services.blogapp.model.Comment;
import com.services.blogapp.projection.CommentDtoProjection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	void deleteByBlog(Blog blog);

	List<Comment> findByBlog(Blog blog);

	List<Comment> findByBlog_blogId(int blogId);

//	@Query(value = "SELECT comment_id, comment_content, user_id, created_at FROM comment WHERE blog_id=:blogId", nativeQuery = true)
//	List<CommentDtoProjection> findByBlogId(@Param("blogId") int blogId);

	@Query(value = "SELECT c.comment_id, c.comment_content, c.user_id, c.created_at, u.username "
			+ "FROM comment c JOIN application_user u ON c.user_id = u.user_id "
			+ "WHERE c.blog_id = :blogId", nativeQuery = true)
	List<CommentDtoProjection> findByBlogId(@Param("blogId") int blogId);

}
