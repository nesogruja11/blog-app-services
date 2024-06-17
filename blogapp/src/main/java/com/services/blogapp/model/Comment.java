package com.services.blogapp.model;

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
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_comment_seq")
	@SequenceGenerator(name = "gen_comment_seq", allocationSize = 1, sequenceName = "gen_comment_seq")
	private int commentId;

	@Column(name = "comment_content", nullable = false)
	private String commentContent;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	// author of the comment
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "blog_id", nullable = false)
	private Blog blog;
}
