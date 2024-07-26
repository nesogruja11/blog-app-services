package com.services.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Blog;
import com.services.blogapp.model.Picture;
import com.services.blogapp.projection.PictureDtoProjection;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

	void deleteByBlog(Blog blog);

	@Query(value = "SELECT picture_id, picture_url FROM picture WHERE blog_id=:blogId", nativeQuery = true)
	List<PictureDtoProjection> findByBlogId(@Param("blogId") int blogId);

}
