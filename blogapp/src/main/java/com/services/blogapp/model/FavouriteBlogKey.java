package com.services.blogapp.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FavouriteBlogKey implements Serializable {

	@Column(name = "user_id", nullable = false)
	private int userId;

	@Column(name = "blog_id", nullable = false)
	private int blogId;
}
