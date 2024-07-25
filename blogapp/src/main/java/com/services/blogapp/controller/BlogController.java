package com.services.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.services.blogapp.dto.PictureDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Blog;
import com.services.blogapp.model.FavouriteBlog;
import com.services.blogapp.service.BlogService;
import com.services.blogapp.service.CommentService;
import com.services.blogapp.service.FavouriteBlogService;
import com.services.blogapp.service.PictureService;

@RestController
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	BlogService blogService;
	@Autowired
	CommentService commentService;
	@Autowired
	FavouriteBlogService favouriteBlogService;
	@Autowired
	PictureService pictureService;

	@GetMapping("/findById")
	public Blog findById(@RequestParam int blogId) throws NotFoundException {
		return blogService.findById(blogId);

	}

	@PostMapping("/save")
	public Blog save(@RequestBody BlogDto blogDto) throws Exception {
		return blogService.save(blogDto);
	}

	@PostMapping("/savePictures")
	public ResponseEntity<String> savePictures(@RequestBody PictureDto pictureDto) {
		try {
			pictureService.savePictures(pictureDto);
			return ResponseEntity.ok("Slike su uspešno sačuvane.");
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Došlo je do greške prilikom čuvanja slika.");
		}
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

	@GetMapping("/approve")
	public Blog approveBlog(@RequestParam int blogId) throws NotFoundException {
		return blogService.approveBlog(blogId);
	}

	@GetMapping("/saveFavouriteBlog")
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

	@GetMapping("/allUnapprovedBlogs")
	public List<Blog> findAllUnapprovedBlogs() {
		return blogService.getUnapprovedBlogs();
	}

	@GetMapping("/allFavouritesBlogs")
	public List<FavouriteBlog> getAllFavouriteBlogs() {
		return favouriteBlogService.findAllFavouritesBlogs();
	}

}
