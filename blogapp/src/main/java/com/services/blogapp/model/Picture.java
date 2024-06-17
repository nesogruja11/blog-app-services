package com.services.blogapp.model;

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
@Table(name = "picture")
public class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_picture_seq")
	@SequenceGenerator(name = "gen_picture_seq", allocationSize = 1, sequenceName = "gen_picture_seq")
	private int pictureId;

	@Column(name = "picture_url", nullable = false)
	private String pictureUrl;

	@ManyToOne
	@JoinColumn(name = "blog_id", nullable = false)
	private Blog blog;
}
