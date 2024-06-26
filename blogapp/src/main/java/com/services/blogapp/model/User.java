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
@Table(name = "application_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_user_seq")
	@SequenceGenerator(name = "gen_user_seq", allocationSize = 1, sequenceName = "gen_user_seq")
	private int userId;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "total_created_blogs", nullable = false)
	private int totalCreatedBlogs = 0;

	@Column(name = "blogger_score", nullable = false)
	private int bloggerScore = 0;

	@Column(name = "active", nullable = false)
	private boolean active = true;

}
