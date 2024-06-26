package com.services.blogapp.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.services.blogapp.model.AuthUserDetails;
import com.services.blogapp.model.User;
import com.services.blogapp.model.UserRole;
import com.services.blogapp.repository.UserRepository;
import com.services.blogapp.repository.UserRoleRepository;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUsername(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Nije pronađen korisnik sa username-om:" + username));
		List<UserRole> userRoles = userRoleRepository.findByUser(user.get());

		AuthUserDetails auth = new AuthUserDetails(user.get(), userRoles);
		return auth;
	}

}