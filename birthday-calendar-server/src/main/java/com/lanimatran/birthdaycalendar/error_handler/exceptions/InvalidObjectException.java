package com.lanimatran.birthdaycalendar.error_handler.exceptions;

public class InvalidObjectException extends RuntimeException{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6213299519333509684L;
	private final static String DEFAULT_MESSAGE = "Invalid information submitted. Please double check that all informations are entered correctly";
	
	public InvalidObjectException() {
        super(DEFAULT_MESSAGE);
    }
	
	public InvalidObjectException(String errorMessage) {
        super(errorMessage);
    }

}