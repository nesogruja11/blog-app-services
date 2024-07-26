package com.services.blogapp.projection;

import org.springframework.beans.factory.annotation.Value;

public interface PictureDtoProjection {

	@Value("#{target.picture_id}")
	Integer getPictureId();

	@Value("#{target.picture_url}")
	String getPictureUrl();

}
