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

import com.services.blogapp.dto.RoleDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Role;
import com.services.blogapp.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	RoleService roleService;

	@GetMapping("/findById")
	public Role findById(@RequestParam int id) throws NotFoundException {
		return roleService.findById(id);
	}

	@GetMapping("/findByName")
	public Role findByName(@RequestParam String name) throws NotFoundException {
		return roleService.findByName(name);
	}

	@PostMapping("/save")
	public Role save(@RequestBody RoleDto roleDto) throws NotFoundException {
		return roleService.save(roleDto);
	}

	@GetMapping("/findAll")
	private List<Role> findAll() {
		return roleService.findAll();
	}

	@PutMapping("/update")
	private Role update(@RequestBody Role role) throws NotFoundException {
		return roleService.update(role);
	}

	@DeleteMapping("/delete")
	private void delete(@RequestBody Role role) throws NotFoundException {
		roleService.delete(role);
	}

}
