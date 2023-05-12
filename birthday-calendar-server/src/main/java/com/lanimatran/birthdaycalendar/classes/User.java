package com.lanimatran.birthdaycalendar.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lanimatran.birthdaycalendar.classes.DTOs.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5278549230841335942L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ID;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private String middleName;
	private String nameSuffix;
	private Date dateOfBirth;
	private Date modifiedDate;
	private Date createdDate;
	private boolean isVerified;
	private boolean isDeleted;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user" )
	private List<Birthday> birthdayList;
	
	public Long getID() {
		return ID;
	}
	
	public void setID(Long id) {
		this.ID = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phoneNumber) {
		this.phone = phoneNumber;
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}	
	
	public List<Birthday> getBirthdayList() {
		return birthdayList;
	}

	public void setBirthdayList(List<Birthday> birthdayList) {
		this.birthdayList = birthdayList;
	}

	public User() {
		super();
		this.ID = (long) 0;
		this.birthdayList = new ArrayList<Birthday>();
	}
	
	public User(Long id) {
		super();
		this.ID = id;
		this.birthdayList = new ArrayList<Birthday>();
	}	

	public User(Long iD, String username, String password, String email, String phone, String firstName,
			String lastName, String middleName, String nameSuffix, Date dateOfBirth, Date modifiedDate,
			Date createdDate, boolean isVerified, boolean isDeleted, List<Birthday> birthdayList) {
		super();
		ID = iD;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.nameSuffix = nameSuffix;
		this.dateOfBirth = dateOfBirth;
		this.modifiedDate = modifiedDate;
		this.createdDate = createdDate;
		this.isVerified = isVerified;
		this.isDeleted = isDeleted;
		this.birthdayList = birthdayList;
	}
	

	@Override
	public String toString() {
		return "User [ID=" + ID + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", nameSuffix="
				+ nameSuffix + ", dateOfBirth=" + dateOfBirth + ", modifiedDate=" + modifiedDate + ", createdDate="
				+ createdDate + ", isVerified=" + isVerified + ", isDeleted=" + isDeleted + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !isDeleted;
	}	
	
	public void updateFromDTO(UserDTO userDTO) {
		this.username = userDTO.getUsername();
		this.email = userDTO.getEmail();
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.middleName = userDTO.getMiddleName();
		this.nameSuffix = userDTO.getNameSuffix();
		this.dateOfBirth = userDTO.getDateOfBirth();
	}
}
