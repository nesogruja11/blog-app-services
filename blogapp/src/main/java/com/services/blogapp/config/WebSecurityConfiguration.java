package com.services.blogapp.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.services.blogapp.exception.CustomAccessDeniedHandler;
import com.services.blogapp.exception.SimpleAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	SimpleAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	CustomAccessDeniedHandler customAcessDeniedHandler;

	private static final String[] ADMIN_URLS = { "/role/**", "/blogStatus/**", "/country/save", "/country/update",
			"/country/delete", "/user/delete", "/user/update", "/user/findAll", "/blogStatus/save",
			"/blogStatus/update", "/blog/approve", "/blog/allUnapprovedBlogs", "/user/findAll", "/user/role",
			"/user/update", "/user/save", };
	private static final String[] USER_URLS = { "/blog/save", "/blog/update", "/country/findbyId", "/country/findAll",
			"/comment/save", "/blog/saveFavouriteBlog", "/blog/deleteFavouriteBlog", "/comment/deleteById",
			"/blog/deleteById", "/picture/findByBlog", "/picture/savePictures", };
	private static final String[] PERMIT_ALL_URLS = { "/user/login", "/blog/findById", "/blog/findAll",
			"/blog/findAllApprovedBlogs", "/comment/findByBlog", "/comment/findAll", "/comment/findById" };

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("X-Auth-Token", "Authorization", "Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// @formatter:off

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
					.authorizeHttpRequests(auth -> auth.requestMatchers(PERMIT_ALL_URLS).permitAll()
				.requestMatchers(ADMIN_URLS).hasRole("ADMIN")
				.requestMatchers(USER_URLS).hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated())
					.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler).accessDeniedHandler(customAcessDeniedHandler))
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// @formatter :on

}
