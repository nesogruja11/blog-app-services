package com.services.blogapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.dto.CommentDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Blog;
import com.services.blogapp.model.Comment;
import com.services.blogapp.model.User;
import com.services.blogapp.projection.CommentDtoProjection;
import com.services.blogapp.repository.BlogRepository;
import com.services.blogapp.repository.CommentRepository;
import com.services.blogapp.utils.SecurityUtils;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	BlogRepository blogRepository;
	@Autowired
	UserService userService;
	@Autowired
	BlogService blogService;

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment findById(int id) throws NotFoundException {
		return commentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen komentar sa id-em:" + id));
	}

	public List<Comment> findByBlog(Blog blog) throws NotFoundException {
		if (blogRepository.existsById(blog.getBlogId())) {
			return commentRepository.findByBlog(blog);
		}
		throw new NotFoundException("Nije pronađen blog sa id-em:" + blog.getBlogId());
	}

	public Comment saveComment(CommentDto commentDto) throws NotFoundException {
		String userName = SecurityUtils.getUsername();
		User user = userService.findUserByUsername(userName).get();
		Blog blog = blogRepository.findById(commentDto.getBlogId())
				.orElseThrow(() -> new NotFoundException("Nije pronađen blog(blogId:" + commentDto.getBlogId()));

		Comment comment = new Comment();
		comment.setCommentContent(commentDto.getCommentContent());
		comment.setCreatedAt(LocalDateTime.now());
		comment.setBlog(blog);
		comment.setUser(user);
		blog.setCommentCount(blog.getCommentCount() + 1);
		blog.setBlogScore(blogService.calculateBlogScore(blog));
		blogRepository.save(blog);
		commentRepository.save(comment);
		recalculateBlogScore(blog);

		return comment;

	}

	private void recalculateBlogScore(Blog commentedBlog) {
		Optional<Blog> mostCommentBlogOpt = blogRepository.findTopByOrderByCommentCountDesc();
		if (mostCommentBlogOpt.isPresent()) {
			Blog mostCommentedBlog = mostCommentBlogOpt.get();
			if (commentedBlog.getBlogId() == mostCommentedBlog.getBlogId()) {
				List<Blog> blogs = blogRepository.findAll();
				blogs.forEach(blog -> {

					blog.setBlogScore(blogService.calculateBlogScore(blog));
					blogRepository.save(blog);
				});
			}
		}
	}

	public void deleteByBlog(Blog blog) {
		commentRepository.deleteByBlog(blog);

	}

	public List<CommentDtoProjection> findByBlogId(int blogId) throws NotFoundException {
		if (blogRepository.existsById(blogId)) {
			return commentRepository.findByBlogId(blogId);
		}
		throw new NotFoundException("Nije pronađen blog sa id-em:" + blogId);
	}

	public void delete(int commentId) throws NotFoundException {
		if (commentRepository.existsById(commentId)) {
			Comment comment = findById(commentId);
			Blog blog = comment.getBlog();
			blog.setCommentCount(blog.getCommentCount() - 1);
			blogRepository.save(blog);
			commentRepository.deleteById(commentId);
		} else {
			throw new NotFoundException("Nije pronađen komentar sa id-em:" + commentId);
		}
	}

}
