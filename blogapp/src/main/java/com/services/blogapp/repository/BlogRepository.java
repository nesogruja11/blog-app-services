package com.services.blogapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
	Optional<Blog> findTopByOrderByFavouriteCountDesc();

	Optional<Blog> findTopByOrderByCommentCountDesc();

}
