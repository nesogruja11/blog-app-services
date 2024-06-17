package com.services.blogapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "blog")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_blog_seq")
	@SequenceGenerator(name = "gen_blog_seq", allocationSize = 1, sequenceName = "gen_blog_seq")
	private int blogId;

	@Column(name = "blog_title", nullable = false)
	private String blogTitle;

	@Column(name = "travel_date", nullable = false)
	private LocalDate travelDate;

	@Column(name = "cover_image_url", nullable = false)
	private String coverImageUrl;

	@Column(name = "blog_content", nullable = false)
	private String blogContent;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	// author
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@ManyToOne
	@JoinColumn(name = "blog_status_id", nullable = false)
	private BlogStatus blogStatus;
}
