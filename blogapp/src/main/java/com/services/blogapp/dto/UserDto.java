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
public class UserDto {
	private Integer userId;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private boolean active;
	// private List<Integer> roleIds;
	private List<String> roleNames;

}
