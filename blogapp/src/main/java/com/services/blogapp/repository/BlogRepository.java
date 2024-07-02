package com.services.blogapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
	Optional<Blog> findTopByOrderByFavouriteCountDesc();

	Optional<Blog> findTopByOrderByCommentCountDesc();

	@Query("SELECT AVG(b.blogScore) FROM Blog b WHERE b.approved=true AND b.user.userId=:userId")
	float findAverageApprovedBlogScore(@Param("userId") int userId);

}
