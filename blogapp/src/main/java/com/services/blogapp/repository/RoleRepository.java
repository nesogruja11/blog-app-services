package com.services.blogapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(String name);

	Optional<Role> findByPreviewName(String previewName);

}
