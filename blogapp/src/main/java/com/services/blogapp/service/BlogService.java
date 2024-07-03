package com.services.blogapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.services.blogapp.model.FavouriteBlog;
import com.services.blogapp.model.FavouriteBlogKey;
import com.services.blogapp.model.User;
import com.services.blogapp.repository.BlogRepository;
import com.services.blogapp.repository.CommentRepository;
import com.services.blogapp.repository.FavouriteBlogRepository;
import com.services.blogapp.repository.PictureRepository;
import com.services.blogapp.repository.UserRepository;
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
	CommentRepository commentRepository;
	@Autowired
	FavouriteBlogRepository favouriteBlogRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PictureRepository pictureRepository;

	final String NEODOBREN = "neodobren";
	final String ODOBREN = "odobren";

	public Blog findById(int id) throws NotFoundException {
		return blogRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen blog sa id-em:" + id));
	}

	public void delete(Blog blog) throws NotFoundException {
		if (blogRepository.existsById(blog.getBlogId())) {
			blogRepository.delete(blog);

		} else {
			throw new NotFoundException("Nije pronađen blog sa id-em:" + blog.getBlogId());
		}
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
		user.setTotalCreatedBlogs(user.getTotalCreatedBlogs() + 1);
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
		commentRepository.deleteByBlog(blog);

		return blog;

	}

	@Transactional
	public void deleteBlog(int blogId) throws NotFoundException {
		if (blogRepository.existsById(blogId)) {
			Blog blog = findById(blogId);
			pictureRepository.deleteByBlog(blog);

			User user = blog.getUser();
			user.setTotalCreatedBlogs(user.getTotalCreatedBlogs() - 1);
			if (blog.isApproved())
				user.setBlogApproveCount(user.getBlogApproveCount() - 1);
			userRepository.save(user);
			blogRepository.deleteById(blogId);
		} else {
			throw new NotFoundException("Nije pronađen blog sa id-em:" + blogId);
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
		User user = blog.getUser();
		user.setBlogApproveCount(user.getBlogApproveCount() + 1);
		BlogStatus blogStatus = blogStatusService.findByBlogStatusCode(ODOBREN);
		blog.setBlogStatus(blogStatus);
		blogRepository.save(blog);
		user.setBloggerScore(calculateBloggerScore(user));
		userRepository.save(user);

		return blog;
	}

	public FavouriteBlog saveFavouriteBlog(int blogId) throws NotFoundException {
		String userName = SecurityUtils.getUsername();
		User user = userService.findUserByUsername(userName).get();
		Blog blog = findById(blogId);
		if (!favouriteBlogRepository.existsByBlogAndUser(blog, user)) {
			FavouriteBlog favouriteBlog = new FavouriteBlog();
			FavouriteBlogKey key = new FavouriteBlogKey(user.getUserId(), blog.getBlogId());
			favouriteBlog.setFavouriteBlogKey(key);
			favouriteBlog.setUser(user);
			favouriteBlog.setBlog(blog);
			blog.setFavouriteCount(blog.getFavouriteCount() + 1);
			blog.setBlogScore(calculateBlogScore(blog));
			blogRepository.save(blog);
			favouriteBlogRepository.save(favouriteBlog);
			recalculateBlogScore(blog);
			return favouriteBlog;
		}
		return null;

	}

	private void recalculateBlogScore(Blog favouriteBlog) {
		Optional<Blog> mostFavouriteBlogOpt = blogRepository.findTopByOrderByFavouriteCountDesc();
		if (mostFavouriteBlogOpt.isPresent()) {
			Blog mostFavouriteBlog = mostFavouriteBlogOpt.get();
			if (favouriteBlog.getBlogId() == mostFavouriteBlog.getBlogId()) {
				List<Blog> blogs = blogRepository.findAll();
				blogs.forEach(blog -> {
					blog.setBlogScore(calculateBlogScore(blog));
					blogRepository.save(blog);
				});
			}
		}

	}

	@Transactional
	public boolean deleteFavouriteBlog(int blogId) throws NotFoundException {
		Blog blog = findById(blogId);
		String userName = SecurityUtils.getUsername();
		User user = userService.findUserByUsername(userName).get();
		if (favouriteBlogRepository.deleteByUserAndBlog(user, blog) > 0) {
			blog.setFavouriteCount(blog.getFavouriteCount() - 1);
			blogRepository.save(blog);
			return true;
		}

		return false;

	}

	public int findTopByOrderByFavouriteCountDesc() {
		Optional<Blog> blogOpt = blogRepository.findTopByOrderByFavouriteCountDesc();
		if (blogOpt.isPresent())
			return blogOpt.get().getFavouriteCount();
		return 0;

	}

	public int findTopByOrderByCommentCountDesc() {
		Optional<Blog> blogOpt = blogRepository.findTopByOrderByCommentCountDesc();
		if (blogOpt.isPresent())
			return blogOpt.get().getCommentCount();
		return 0;

	}

	public float calculateBlogScore(Blog blog) {
		int favouriteCount = blog.getFavouriteCount();
		int favBlogFavCount = findTopByOrderByFavouriteCountDesc();
		int commentCount = blog.getCommentCount();
		int favBlogCommentCount = findTopByOrderByCommentCountDesc();
		float blogScore = 100f * (2f * favouriteCount / (favBlogFavCount + commentCount) / favBlogCommentCount) / 3f;
		return blogScore;

	}

	public float findAverageApprovedBlogScore(int userId) {
		return blogRepository.findAverageApprovedBlogScore(userId);
	}

	public float calculateBloggerScore(User user) {
		int totalCreatedBlogs = user.getTotalCreatedBlogs();
		int blogApproveCount = user.getBlogApproveCount();
		float averageApprovedBlogScore = findAverageApprovedBlogScore(user.getUserId());
		float bloggerScore = 100f * ((blogApproveCount / totalCreatedBlogs) + averageApprovedBlogScore) / 2f;
		return bloggerScore;
	}

	public List<Blog> findTop5ByOrderByBlogScoreDesc() {
		return blogRepository.findTop5ByOrderByBlogScoreDesc();
	}

}
