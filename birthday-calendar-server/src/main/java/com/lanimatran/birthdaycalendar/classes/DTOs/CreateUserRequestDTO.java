package com.lanimatran.birthdaycalendar.classes.DTOs;

/**
 * CreateUserRequestDTO is used to capture registration requests with minimal information to get a new user started.
 */
public class CreateUserRequestDTO {
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	
	public String getUsername() {
		return username;
	}

	public void getUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public CreateUserRequestDTO() {
		super();
	}
	
	public CreateUserRequestDTO(String email, String password, String firstName, String lastName, String username) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}

	@Override
	public String toString() {
		return "CreateUserRequestDTO [email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
