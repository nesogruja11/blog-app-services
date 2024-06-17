package com.services.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.FavouriteBlog;
import com.services.blogapp.model.FavouriteBlogKey;

@Repository
public interface FavouriteBlogRepository extends JpaRepository<FavouriteBlog, FavouriteBlogKey> {

}
