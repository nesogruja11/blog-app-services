package com.services.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.blogapp.dto.RoleDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.model.Role;
import com.services.blogapp.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	RoleRepository roleRepository;

	public Role findById(int id) throws NotFoundException {
		return roleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(("Nije pronađena rola sa id-em:" + id)));
	}

	public Role findByName(String name) throws NotFoundException {
		return roleRepository.findByName(name)
				.orElseThrow(() -> new NotFoundException("Nije pronađena rola sa imenom:" + name));
	}

	public Role findByPreviewName(String previewName) throws NotFoundException {
		return roleRepository.findByPreviewName(previewName)
				.orElseThrow(() -> new NotFoundException("Nije pronađena rola sa imenom:" + previewName));
	}

	public Role save(RoleDto roleDto) throws NotFoundException {
		Role role = new Role();
		role.setName(roleDto.getName());
		return roleRepository.save(role);
	}

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	public Role update(Role role) throws NotFoundException {
		if (roleRepository.existsById(role.getRoleId())) {
			return roleRepository.save(role);
		}
		throw new NotFoundException("Nije pronađena rola sa id-em:" + role.getRoleId());
	}

	public void delete(Role role) throws NotFoundException {
		if (roleRepository.existsById(role.getRoleId())) {
			roleRepository.delete(role);
		} else {
			throw new NotFoundException("Nije pronađena rola sa id-em:" + role.getRoleId());
		}
	}

}
