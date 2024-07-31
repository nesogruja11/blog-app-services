package com.services.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.model.User;
import com.services.blogapp.model.UserRole;
import com.services.blogapp.repository.UserRoleRepository;

@Service
public class UserRoleService {

	@Autowired
	UserRoleRepository userRoleRepository;

	public List<UserRole> findByUser(User user) {
		return userRoleRepository.findByUser(user);

	}

}
