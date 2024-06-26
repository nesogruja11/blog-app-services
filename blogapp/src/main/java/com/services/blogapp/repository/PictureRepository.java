package com.services.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Blog;
import com.services.blogapp.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

	void deleteByBlog(Blog blog);

}
