package com.services.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.dto.BlogStatusDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.BlogStatus;
import com.services.blogapp.repository.BlogStatusRepository;

@Service
public class BlogStatusService {
	@Autowired
	BlogStatusRepository blogStatusRepository;

	public BlogStatus findById(int id) throws NotFoundException {
		return blogStatusRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen blog status sa id-em:" + id));
	}

	public BlogStatus findByBlogStatusCode(String blogStatusCode) throws NotFoundException {
		return blogStatusRepository.findByBlogStatusCode(blogStatusCode).orElseThrow(
				() -> new NotFoundException("Nije pronađen blog status(blogStatusCode:" + blogStatusCode + ")!"));
	}

	public BlogStatus save(BlogStatusDto blogStatusDto) throws NotFoundException {
		BlogStatus blogStatus = new BlogStatus();
		blogStatus.setBlogStatusCode(blogStatusDto.getBlogStatusCode());
		blogStatus.setBlogStatusName(blogStatusDto.getBlogStatusName());
		return blogStatusRepository.save(blogStatus);
	}

	public List<BlogStatus> findAll() {
		return blogStatusRepository.findAll();
	}

	public BlogStatus update(BlogStatus blogStatus) throws NotFoundException {
		if (blogStatusRepository.existsById(blogStatus.getBlogStatusId())) {
			return blogStatusRepository.save(blogStatus);

		}
		throw new NotFoundException("Nije pronađen blog status sa id-em:" + blogStatus.getBlogStatusId());

	}

	public void delete(BlogStatus blogStatus) throws NotFoundException {
		if (blogStatusRepository.existsById(blogStatus.getBlogStatusId())) {
			blogStatusRepository.delete(blogStatus);
		} else {
			throw new NotFoundException("Nije pronađen blog status sa id-em:" + blogStatus.getBlogStatusId());
		}
	}
}
