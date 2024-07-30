package com.services.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.services.blogapp.dto.PictureDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.projection.PictureDtoProjection;
import com.services.blogapp.service.PictureService;

@RestController
@RequestMapping("/picture")
public class PictureController {

	@Autowired
	PictureService pictureService;

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

	@GetMapping("/findByBlog")
	private List<PictureDtoProjection> findByBlogId(@RequestParam int blogId) throws NotFoundException {
		return pictureService.findByBlogId(blogId);
	}

	@DeleteMapping("/deleteById")
	private void deleteByPictureId(@RequestParam int pictureId) throws NotFoundException {
		pictureService.delete(pictureId);
	}

}
