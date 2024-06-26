package com.services.blogapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.blogapp.dto.BlogDto;
import com.services.blogapp.dto.BlogUpdateDto;
import com.services.blogapp.dto.PictureDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Blog;
import com.services.blogapp.model.BlogStatus;
import com.services.blogapp.model.Country;
import com.services.blogapp.model.User;
import com.services.blogapp.repository.BlogRepository;
import com.services.blogapp.utils.SecurityUtils;

@Service
public class BlogService {

	@Autowired
	BlogRepository blogRepository;
	@Autowired
	CountryService countryService;
	@Autowired
	BlogStatusService blogStatusService;
	@Autowired
	PictureService pictureService;
	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;

	final String NEODOBREN = "neodobren";
	final String ODOBREN = "odobren";

	public Blog findById(int id) throws NotFoundException {
		return blogRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen blog sa id-em:" + id));
	}

	public List<Blog> findAll() {
		return blogRepository.findAll();
	}

	@Transactional
	public Blog save(BlogDto blogDto) throws NotFoundException {
		String userName = SecurityUtils.getUsername();
		User user = userService.findUserByUsername(userName).get();
		Blog blog = new Blog();
		blog.setBlogTitle(blogDto.getBlogTitle());
		blog.setTravelDate(blogDto.getTravelDate());
		blog.setCoverImageUrl(blogDto.getCoverImageUrl());
		blog.setBlogContent(blogDto.getBlogContent());
		blog.setCreatedAt(LocalDateTime.now());
		Country country = countryService.findById(blogDto.getCountryId());
		blog.setCountry(country);
		BlogStatus blogStatus = blogStatusService.findByBlogStatusCode(NEODOBREN);
		blog.setBlogStatus(blogStatus);
		blog.setUser(user);
		blog = blogRepository.save(blog);
		// save pictures
		pictureService.savePictures(new PictureDto(blog.getBlogId(), blogDto.getPictureURLs()));

		return blog;

	}

	@Transactional
	public Blog update(BlogUpdateDto blogUpdateDto) throws NotFoundException {
		Blog blog = findById(blogUpdateDto.getBlogId());
		blog.setBlogTitle(blogUpdateDto.getBlogTitle());
		blog.setTravelDate(blogUpdateDto.getTravelDate());
		blog.setCoverImageUrl(blogUpdateDto.getCoverImageUrl());
		blog.setBlogContent(blogUpdateDto.getBlogContent());
		blog.setCreatedAt(LocalDateTime.now());
		Country country = countryService.findById(blogUpdateDto.getCountryId());
		blog.setCountry(country);
		BlogStatus blogStatus = blogStatusService.findByBlogStatusCode(NEODOBREN);
		blog.setBlogStatus(blogStatus);

		blog = blogRepository.save(blog);
		// delete old pictures
		pictureService.deleteByBlog(blog);
		// save pictures
		pictureService.savePictures(new PictureDto(blog.getBlogId(), blogUpdateDto.getPictureURLs()));
		// delete old comments
		commentService.deleteByBlog(blog);

		return blog;

	}

	public void delete(Blog blog) throws NotFoundException {
		if (blogRepository.existsById(blog.getBlogId())) {
			blogRepository.delete(blog);
		} else {
			throw new NotFoundException("Nije pronađen blog sa id-em:" + blog.getBlogId());
		}
	}

	public boolean existById(int blogId) {
		if (blogRepository.existsById(blogId))
			return true;
		else
			return false;
	}

	public Blog approveBlog(int blogId) throws NotFoundException {
		Blog blog = findById(blogId);
		blog.setApproved(true);
		BlogStatus blogStatus = blogStatusService.findByBlogStatusCode(ODOBREN);
		blog.setBlogStatus(blogStatus);
		return blogRepository.save(blog);
	}

}
