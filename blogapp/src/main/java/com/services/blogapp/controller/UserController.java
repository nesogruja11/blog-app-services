package com.services.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.services.blogapp.dto.LoginDto;
import com.services.blogapp.dto.RegistrationDto;
import com.services.blogapp.dto.UserDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.exception.RegistrationException;
import com.services.blogapp.model.User;
import com.services.blogapp.model.UserRole;
import com.services.blogapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		return userService.login(loginDto);
	}

	@PostMapping("/save")
	public RegistrationDto save(@RequestBody UserDto userDto) throws NotFoundException, RegistrationException {
		return userService.save(userDto);
	}

	@PutMapping("/update")
	public User update(@RequestBody UserDto userDto) throws NotFoundException {
		return userService.update(userDto);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestBody User user) throws NotFoundException {
		userService.delete(user);
	}

	@GetMapping("/findAll")
	private List<User> findAll() throws NotFoundException {
		return userService.findAll();
	}

	@GetMapping("/findById")
	public User findById(@RequestParam int id) throws NotFoundException {
		return userService.findById(id);
	}

	@GetMapping("/findTop5")
	public List<User> findTop5() {
		return userService.findTop5ByOrderByBloggerScoreDesc();
	}

	@GetMapping("/role")
	public ResponseEntity<List<UserRole>> getUserRoles(@RequestParam int userId) {
		List<UserRole> roles = userService.getRolesByUserId(userId);
		return ResponseEntity.ok(roles);
	}

}
