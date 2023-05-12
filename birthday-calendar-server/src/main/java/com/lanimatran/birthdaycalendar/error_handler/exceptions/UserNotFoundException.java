package com.lanimatran.birthdaycalendar.error_handler.exceptions;

public class UserNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6823546074558693855L;
	private final static String DEFAULT_MESSAGE = "User not found.";
	
	public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
	
	public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
