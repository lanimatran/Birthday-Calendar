package com.lanimatran.birthdaycalendar.error_handler;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lanimatran.birthdaycalendar.error_handler.exceptions.InvalidJwtTokenException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.EmailNotAvailableException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.InvalidObjectException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.ResourceNotFoundException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UnauthorizedException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UserAlreadyExistsException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UserNotFoundException;
import com.lanimatran.birthdaycalendar.error_handler.exceptions.UserNotVerifiedException;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
	    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(EmailNotAvailableException.class)
	protected ResponseEntity<Object> handleEmailNotAvailable(EmailNotAvailableException ex) {
	    ApiError apiError = new ApiError(HttpStatus.CONFLICT);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	protected ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
	    ApiError apiError = new ApiError(HttpStatus.CONFLICT);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(InvalidObjectException.class)
	protected ResponseEntity<Object> handleInvalidObject(InvalidObjectException ex) {
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
	    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(UserNotVerifiedException.class)
	protected ResponseEntity<Object> handleUserNotVerified(UserNotVerifiedException ex) {
	    ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
	    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<Object> handleNotAuthorized(UnauthorizedException ex) {
	    ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}

	@ExceptionHandler(InvalidJwtTokenException.class)
	protected ResponseEntity<Object> handleInvalidJwtTokenException(InvalidJwtTokenException ex) {
	    ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
	    apiError.setMessage(ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	    return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
