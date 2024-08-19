package com.services.blogapp.projection;

import org.springframework.beans.factory.annotation.Value;

public interface CommentDtoProjection {

	@Value("#{target.comment_id}")
	Integer getCommentId();

	@Value("#{target.comment_content}")
	String getCommentContent();

	@Value("#{target.user_id}")
	Integer getUserId();

	@Value("#{target.created_at}")
	String getCreatedAt();

	@Value("#{target.username}")
	String getUsername();

}
