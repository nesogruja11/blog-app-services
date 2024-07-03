package com.services.blogapp.exception;

import java.util.Date;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.services.blogapp.utils.FormatUtils;

@ControllerAdvice
public class RestResponseEntityException {

	@Value("${debug.response}")
	private boolean debug;

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorMessage> notAuthorizedExceptionHandler(Unauthorized ex, WebRequest request) {
		return errorBuilder(ex.getMessage(), request, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> notFoundExceptionHandler(NotFoundException ex, WebRequest request) {
		return errorBuilder(ex.getMessage(), request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RegistrationException.class)
	public ResponseEntity<ErrorMessage> registrationExceptionHandler(RegistrationException ex, WebRequest request) {
		return errorBuilder(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException ex,
			WebRequest request) {
		return errorBuilder(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex,
			WebRequest request) {
		StringBuilder sb = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			sb.append(error.getDefaultMessage() + " ");
		});
		return errorBuilder(sb.toString(), request, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<ErrorMessage> errorBuilder(String message, WebRequest request, HttpStatus status) {

		ErrorMessage errorMessage = new ErrorMessage(FormatUtils.getSimpleDateFormat().format(new Date()),
				status.value(), (debug ? message : ""),
				((ServletWebRequest) request).getRequest().getRequestURI().toString());
		return new ResponseEntity<ErrorMessage>(errorMessage, status);
	}
}
