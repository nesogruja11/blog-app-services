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

import com.services.blogapp.dto.BlogStatusDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.BlogStatus;
import com.services.blogapp.service.BlogStatusService;

@RestController
@RequestMapping("/blogStatus")
public class BlogStatusController {
	@Autowired
	BlogStatusService blogStatusService;

	@GetMapping("/findById")
	public BlogStatus findById(@RequestParam int id) throws NotFoundException {
		return blogStatusService.findById(id);

	}

	@PostMapping("/save")
	public BlogStatus save(@RequestBody BlogStatusDto blogStatusDto) throws NotFoundException {
		return blogStatusService.save(blogStatusDto);
	}

	@PutMapping("/update")
	public BlogStatus update(@RequestBody BlogStatus blogStatus) throws NotFoundException {
		return blogStatusService.update(blogStatus);

	}

	@DeleteMapping("/delete")
	private void delete(@RequestBody BlogStatus blogStatus) throws NotFoundException {
		blogStatusService.delete(blogStatus);
	}

	@GetMapping("/findAll")
	private List<BlogStatus> findAll() {
		return blogStatusService.findAll();
	}

}
