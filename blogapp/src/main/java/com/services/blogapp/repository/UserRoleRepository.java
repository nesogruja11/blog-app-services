package com.services.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.User;
import com.services.blogapp.model.UserRole;
import com.services.blogapp.model.UserRoleKey;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {

	List<UserRole> findByUser(User user);

}
