package com.lanimatran.birthdaycalendar.error_handler.exceptions;

/**
 * This Exception indicates that the request was invalid. 
 */
public class InvalidJwtTokenException extends RuntimeException{	
	private static final long serialVersionUID = -2070041641650792431L;
	private final static String DEFAULT_MESSAGE = "Invalid token.";
	
	public InvalidJwtTokenException() {
        super(DEFAULT_MESSAGE);
    }
	
	public InvalidJwtTokenException(String errorMessage) {
        super(errorMessage);
    }
}
