package com.services.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Blog;
import com.services.blogapp.model.FavouriteBlog;
import com.services.blogapp.model.FavouriteBlogKey;
import com.services.blogapp.model.User;

@Repository
public interface FavouriteBlogRepository extends JpaRepository<FavouriteBlog, FavouriteBlogKey> {
	int deleteByUserAndBlog(User user, Blog blog);

	boolean existsByBlogAndUser(Blog blog, User user);

}
