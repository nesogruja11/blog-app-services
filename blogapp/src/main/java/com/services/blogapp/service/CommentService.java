package com.services.blogapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.dto.BlogCommentDto;
import com.services.blogapp.dto.CommentDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Blog;
import com.services.blogapp.model.Comment;
import com.services.blogapp.repository.BlogRepository;
import com.services.blogapp.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	BlogRepository blogRepository;

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public List<Comment> findByBlog(Blog blog) throws NotFoundException {
		if (blogRepository.existsById(blog.getBlogId())) {
			return commentRepository.findByBlog(blog);
		}
		throw new NotFoundException("Nije pronađen blog sa id-em:" + blog.getBlogId());
	}

	public Comment saveComment(CommentDto commentDto) throws NotFoundException {
		Blog blog = blogRepository.findById(commentDto.getBlogId())
				.orElseThrow(() -> new NotFoundException("Nije pronađen blog(blogId:" + commentDto.getBlogId()));

		Comment comment = new Comment();
		comment.setCommentContent(commentDto.getCommentContent());
		comment.setCreatedAt(LocalDateTime.now());
		comment.setBlog(blog);
		return commentRepository.save(comment);

	}

	public void deleteByBlog(Blog blog) {
		commentRepository.deleteByBlog(blog);
	}

	// prebaciti na database projekciju radi performansi izvršenja servisa
	public List<BlogCommentDto> findByBlogId(int blogId) throws NotFoundException {
		if (blogRepository.existsById(blogId)) {
			List<Comment> comments = commentRepository.findByBlog_blogId(blogId);
			List<BlogCommentDto> blogCommentDtos = new ArrayList<BlogCommentDto>();
			comments.forEach(comment -> {
				blogCommentDtos.add(new BlogCommentDto(blogId, comment.getCommentContent()));
			});
			return blogCommentDtos;
		}
		throw new NotFoundException("Nije pronađen blog sa id-em:" + blogId);
	}

}
