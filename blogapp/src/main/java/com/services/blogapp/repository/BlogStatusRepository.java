package com.services.blogapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.BlogStatus;

@Repository
public interface BlogStatusRepository extends JpaRepository<BlogStatus, Integer> {

	Optional<BlogStatus> findByBlogStatusCode(String blogStatusCode);

}
