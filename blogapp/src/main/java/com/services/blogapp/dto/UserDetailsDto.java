package com.services.blogapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
	private int userId;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private int totalCreatedBlogs;
	private int blogApproveCount;
	private float bloggerScore;
	private boolean active;
	private List<String> roleNames;
}
