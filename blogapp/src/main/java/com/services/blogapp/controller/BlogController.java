package com.services.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.services.blogapp.dto.BlogDto;
import com.services.blogapp.dto.BlogUpdateDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Blog;
import com.services.blogapp.model.FavouriteBlog;
import com.services.blogapp.service.BlogService;
import com.services.blogapp.service.CommentService;

@RestController
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	BlogService blogService;
	@Autowired
	CommentService commentService;

	@GetMapping("/findById")
	public Blog findById(@RequestParam int id) throws NotFoundException {
		return blogService.findById(id);

	}

	@PostMapping("/save")
	public Blog save(@RequestBody BlogDto blogDto) throws Exception {
		return blogService.save(blogDto);
	}

	@GetMapping("/findAll")
	private List<Blog> findAll() {
		return blogService.findAll();
	}

	@PutMapping("/update")
	public Blog update(@RequestBody BlogUpdateDto blogUpdateDto) throws NotFoundException {
		return blogService.update(blogUpdateDto);

	}

	@DeleteMapping("/deleteById")
	private void deleteByBlogId(@RequestParam int blogId) throws NotFoundException {
		blogService.deleteBlog(blogId);
	}

	@PostMapping("/approve")
	public Blog approveBlog(@RequestParam int blogId) throws NotFoundException {
		return blogService.approveBlog(blogId);
	}

	@PostMapping("/saveFavouriteBlog")
	public FavouriteBlog saveFavouriteBlog(@RequestParam int blogId) throws NotFoundException {
		return blogService.saveFavouriteBlog(blogId);
	}

	@DeleteMapping("/deleteFavouriteBlog")
	public boolean deleteFavouriteBlog(@RequestParam int blogId) throws NotFoundException {
		return blogService.deleteFavouriteBlog(blogId);
	}

	@GetMapping("/findTop5")
	public List<Blog> findTop5() {
		return blogService.findTop5ByOrderByBlogScoreDesc();
	}

}
