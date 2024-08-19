package com.services.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.User;
import com.services.blogapp.model.UserRole;
import com.services.blogapp.model.UserRoleKey;

import jakarta.transaction.Transactional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {

	List<UserRole> findByUser(User user);

	@Modifying
	@Query("DELETE FROM UserRole ur WHERE ur.user = :user")
	void deleteByUser(@Param("user") User user);

	@Modifying
	@Transactional
	@Query("DELETE FROM UserRole ur WHERE ur.user.userId = :userId")
	void deleteByUserId(@Param("userId") int userId);
}
