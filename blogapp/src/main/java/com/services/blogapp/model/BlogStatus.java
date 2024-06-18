package com.services.blogapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog_status")
public class BlogStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_blog_status_seq")
	@SequenceGenerator(name = "gen_blog_status_seq", allocationSize = 1, sequenceName = "gen_blog_status_seq")
	private int blogStatusId;

	@Column(name = "blog_status_code", nullable = false, unique = true)
	private String blogStatusCode;

	@Column(name = "blog_status_name", nullable = false)
	private String blogStatusName;
}
