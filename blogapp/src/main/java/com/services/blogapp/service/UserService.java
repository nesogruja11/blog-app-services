package com.services.blogapp.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.services.blogapp.config.JwtUtils;
import com.services.blogapp.dto.JwtResponse;
import com.services.blogapp.dto.LoginDto;
import com.services.blogapp.dto.RegistrationDto;
import com.services.blogapp.dto.UserDto;
import com.services.blogapp.exception.NotFoundException;
import com.services.blogapp.exception.RegistrationException;
import com.services.blogapp.model.Role;
import com.services.blogapp.model.User;
import com.services.blogapp.model.UserRole;
import com.services.blogapp.model.UserRoleKey;
import com.services.blogapp.repository.RoleRepository;
import com.services.blogapp.repository.UserRepository;
import com.services.blogapp.repository.UserRoleRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleService roleService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	private final String ROLE_USER = "ROLE_USER";

	public ResponseEntity<?> login(LoginDto loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	private User buildUserFromDto(UserDto userDto) {
		User user = new User();
		if (userDto.getUserId() != null)
			user.setUserId(userDto.getUserId());
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setUsername(userDto.getUsername());

		return user;
	}

	public User findById(int id) throws NotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen user sa id-em:" + id));
	}

	public List<User> findAll() throws NotFoundException {
		return userRepository.findAll();
	}

	public User update(UserDto userDto) throws NotFoundException {
		User user = findById(userDto.getUserId());
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setUsername(userDto.getUsername());
		return userRepository.save(user);
	}

	public void delete(User user) throws NotFoundException {
		if (userRepository.existsById(user.getUserId())) {
			userRepository.delete(user);
		} else {
			throw new NotFoundException("Nije pronađen user sa id-em:" + user.getUserId());
		}
	}

	public RegistrationDto save(UserDto userDto) throws RegistrationException, NotFoundException {
		if (!userRepository.existsByEmail(userDto.getEmail())
				&& !userRepository.existsByUsername(userDto.getUsername())) {
			User user = userRepository.save(buildUserFromDto(userDto));
			Role role = roleService.findByName(ROLE_USER);
			UserRoleKey key = new UserRoleKey(user.getUserId(), role.getRoleId());
			userRoleRepository.save(new UserRole(key, user, role));

			if (Objects.nonNull(userRepository.save(user))) {
				return new RegistrationDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getFirstName(),
						user.getLastName());
			} else {
				throw new RegistrationException("Greška prilikom registracije korisnika!");
			}
		}
		throw new RegistrationException("E-mail i username moraju biti jedinstveni");

	}

}
