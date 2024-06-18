package com.services.blogapp.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogUpdateDto {
	private int blogId;
	private String blogTitle;
	private LocalDate travelDate;
	private String coverImageUrl;
	private String blogContent;
	private int countryId;
	private String[] pictureURLs;

}
