package com.lanimatran.birthdaycalendar.error_handler.exceptions;

public class UnauthorizedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6277533942371990614L;
	private final static String DEFAULT_MESSAGE = "You are unauthorized to view this resource.";
	
	public UnauthorizedException() {
        super(DEFAULT_MESSAGE);
    }
	
	public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
