package com.services.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.model.FavouriteBlog;
import com.services.blogapp.model.User;
import com.services.blogapp.repository.FavouriteBlogRepository;
import com.services.blogapp.utils.SecurityUtils;

@Service
public class FavouriteBlogService {
	@Autowired
	FavouriteBlogRepository favouriteBlogRepository;

	@Autowired
	UserService userService;

	public List<FavouriteBlog> findAllFavouritesBlogs() {
		String userName = SecurityUtils.getUsername();
		User user = userService.findUserByUsername(userName).get();
		return favouriteBlogRepository.findAllByUser(user);

	}

}
