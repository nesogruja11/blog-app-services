package com.services.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.services.blogapp.dto.BlogCommentDto;
import com.services.blogapp.dto.CommentDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Comment;
import com.services.blogapp.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	CommentService commentService;

	@PostMapping("/save")
	public Comment saveComment(@RequestBody CommentDto commentDto) throws NotFoundException {
		return commentService.saveComment(commentDto);

	}

	@GetMapping("/findByBlog")
	private List<BlogCommentDto> findByBlogId(@RequestParam int blogId) throws NotFoundException {
		return commentService.findByBlogId(blogId);
	}

}
