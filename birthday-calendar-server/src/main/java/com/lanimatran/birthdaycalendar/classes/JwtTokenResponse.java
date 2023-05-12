package com.lanimatran.birthdaycalendar.classes;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 8317676219297719109L;

  	private final String token;
  	private final String firstName;
  	

    public JwtTokenResponse(String token, String firstName) {
        this.token = token;
        this.firstName = firstName;
    }

    public String getToken() {
        return this.token;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
}