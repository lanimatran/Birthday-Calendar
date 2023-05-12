package com.lanimatran.birthdaycalendar.error_handler.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7286673552402897565L;
	private final static String DEFAULT_MESSAGE = "A user with this email address already exists.";
	
	public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
	
	public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
