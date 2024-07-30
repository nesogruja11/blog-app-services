package com.services.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.dto.PictureDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Blog;
import com.services.blogapp.model.Picture;
import com.services.blogapp.projection.PictureDtoProjection;
import com.services.blogapp.repository.BlogRepository;
import com.services.blogapp.repository.PictureRepository;

import jakarta.transaction.Transactional;

@Service
public class PictureService {

	@Autowired
	BlogRepository blogRepository;
	@Autowired
	PictureRepository pictureRepository;

	@Transactional
	public void savePictures(PictureDto pictureDto) throws NotFoundException {
		Blog blog = blogRepository.findById(pictureDto.getBlogId())
				.orElseThrow(() -> new NotFoundException("Nije pronađen blog(blogId:" + pictureDto.getBlogId()));

		try {
			for (String url : pictureDto.getPictureURLs()) {
				Picture picture = new Picture();
				picture.setPictureUrl(url);
				picture.setBlog(blog);
				pictureRepository.save(picture);
			}
		} catch (Exception e) {
			System.out.println("Došlo je do greške prilikom čuvanja slika(blogId:" + blog.getBlogId() + ")");
		}

	}

	public List<PictureDtoProjection> findByBlogId(int blogId) throws NotFoundException {
		if (blogRepository.existsById(blogId)) {
			return pictureRepository.findByBlogId(blogId);
		}
		throw new NotFoundException("Nije pronađen blog sa id-em:" + blogId);
	}

	public List<Picture> findAll() {
		return pictureRepository.findAll();
	}

	public void deleteByBlog(Blog blog) {
		pictureRepository.deleteByBlog(blog);
	}

	public Picture findById(int id) throws NotFoundException {
		return pictureRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađena slika sa id-em:" + id));
	}

	public void delete(int pictureId) throws NotFoundException {
		if (pictureRepository.existsById(pictureId)) {
			Picture picture = findById(pictureId);
			Blog blog = picture.getBlog();
			blogRepository.save(blog);
			pictureRepository.deleteById(pictureId);
		} else {
			throw new NotFoundException("Nije pronađena slika sa id-em:" + pictureId);
		}
	}

}
