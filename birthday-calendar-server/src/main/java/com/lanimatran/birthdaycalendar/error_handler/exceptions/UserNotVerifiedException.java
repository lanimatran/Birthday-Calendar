package com.lanimatran.birthdaycalendar.error_handler.exceptions;

public class UserNotVerifiedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -427906845067607099L;
	private final static String DEFAULT_MESSAGE = "User not verified.";
	
	public UserNotVerifiedException() {
        super(DEFAULT_MESSAGE);
    }
	
	public UserNotVerifiedException(String errorMessage) {
        super(errorMessage);
    }
}
