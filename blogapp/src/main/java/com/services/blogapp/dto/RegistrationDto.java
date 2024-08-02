package com.services.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
	private Integer userId;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private boolean active;

}
