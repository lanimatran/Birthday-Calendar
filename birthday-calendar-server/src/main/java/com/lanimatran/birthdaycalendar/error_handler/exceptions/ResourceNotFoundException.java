package com.lanimatran.birthdaycalendar.error_handler.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4658322447962977765L;
	private final static String DEFAULT_MESSAGE = "Resource not found.";
	
	public ResourceNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
	
	public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
