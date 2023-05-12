package com.lanimatran.birthdaycalendar.error_handler.exceptions;
public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 4478763916025476501L;

	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

