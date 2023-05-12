package com.lanimatran.birthdaycalendar.error_handler.exceptions;

/**
 * Exception thrown when there is already an existing email address in the database
 */
public class EmailNotAvailableException extends RuntimeException{	
	private static final long serialVersionUID = 1603679343305269102L;
	private final static String DEFAULT_MESSAGE = "This email address has already been used.";
	
	public EmailNotAvailableException() {
        super(DEFAULT_MESSAGE);
    }
	
	public EmailNotAvailableException(String errorMessage) {
        super(errorMessage);
    }

}
