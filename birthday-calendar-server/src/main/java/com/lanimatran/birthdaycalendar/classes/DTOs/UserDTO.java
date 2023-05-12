package com.lanimatran.birthdaycalendar.classes.DTOs;

import java.util.Date;

public class UserDTO {
	private long ID;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String middleName;
	private String nameSuffix;
	private Date dateOfBirth;
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getNameSuffix() {
		return nameSuffix;
	}
	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public UserDTO() {
		super();
	}
	public UserDTO(long iD, String username, String email, String firstName, String lastName,
			String middleName, String nameSuffix, Date dateOfBirth) {
		super();
		ID = iD;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.nameSuffix = nameSuffix;
		this.dateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "UserDTO [ID=" + ID + ", username=" + username + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", nameSuffix="
				+ nameSuffix + ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	
}
