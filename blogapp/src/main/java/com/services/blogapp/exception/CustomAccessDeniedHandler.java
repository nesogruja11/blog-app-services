package com.services.blogapp.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.blogapp.utils.FormatUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e)
			throws IOException, ServletException {

		ErrorMessage errorMessage = new ErrorMessage(FormatUtils.getSimpleDateFormat().format(new Date()),
				HttpStatus.FORBIDDEN.value(), "Nemate privilegiju za ovu opciju!",
				httpServletRequest.getRequestURI().toString());
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, errorMessage);
		out.flush();
		out.close();
	}
}
