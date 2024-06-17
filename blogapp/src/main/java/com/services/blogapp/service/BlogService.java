package com.services.blogapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.repository.BlogRepository;

@Service
public class BlogService {

	@Autowired
	BlogRepository blogRepository;

}
