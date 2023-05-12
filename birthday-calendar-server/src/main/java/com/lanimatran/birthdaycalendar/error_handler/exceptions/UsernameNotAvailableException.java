package com.lanimatran.birthdaycalendar.error_handler.exceptions;

public class UsernameNotAvailableException  extends RuntimeException{
	private static final long serialVersionUID = -3162101133726896084L;
	private final static String DEFAULT_MESSAGE = "This username has already been used.";
	
	public UsernameNotAvailableException() {
        super(DEFAULT_MESSAGE);
    }
	
	public UsernameNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
