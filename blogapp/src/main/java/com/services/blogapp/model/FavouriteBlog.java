package com.services.blogapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "favourite_blog")
public class FavouriteBlog {

	@EmbeddedId
	@Column(name = "favourite_blog_id")
	private FavouriteBlogKey favouriteBlogKey;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@MapsId("blogId")
	@JoinColumn(name = "blog_id")
	private Blog blog;
}
