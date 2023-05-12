package com.lanimatran.birthdaycalendar.classes;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lanimatran.birthdaycalendar.classes.DTOs.BirthdayDTO;


@Entity
@Table(name = "birthdays")
public class Birthday {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ID;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private int birthDay;
	
	private int birthMonth;	
	
	private boolean willRemindOneDayPrior;
	
	private boolean willRemindOneWeekPrior;
	
	private boolean willRemindCustomTime;
	
	private Date customReminder;
	
	private boolean isProcessed;
	
	@ManyToOne
	@JsonIgnore
	private User user;


	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
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
	
	public int getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public boolean isWillRemindOneDayPrior() {
		return willRemindOneDayPrior;
	}

	public void setWillRemindOneDayPrior(boolean willRemindOneDayPrior) {
		this.willRemindOneDayPrior = willRemindOneDayPrior;
	}

	public boolean isWillRemindOneWeekPrior() {
		return willRemindOneWeekPrior;
	}

	public void setWillRemindOneWeekPrior(boolean willRemindOneWeekPrior) {
		this.willRemindOneWeekPrior = willRemindOneWeekPrior;
	}

	public boolean isWillRemindCustomTime() {
		return willRemindCustomTime;
	}

	public void setWillRemindCustomTime(boolean willRemindCustomTime) {
		this.willRemindCustomTime = willRemindCustomTime;
	}

	public Date getCustomReminder() {
		return customReminder;
	}

	public void setCustomReminder(Date customReminder) {
		this.customReminder = customReminder;
	}	
	
	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.user.getBirthdayList().add(this);
	}

	public Birthday() {
		super();
		this.user = new User();
	}

	public Birthday(Long iD, String firstName, String lastName, String middleName, int birthDay, int birthMonth,
			boolean willRemindOneDayPrior, boolean willRemindOneWeekPrior, boolean willRemindCustomTime,
			Date customReminder, boolean isProcessed, User user) {
		super();
		ID = iD;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.willRemindOneDayPrior = willRemindOneDayPrior;
		this.willRemindOneWeekPrior = willRemindOneWeekPrior;
		this.willRemindCustomTime = willRemindCustomTime;
		this.customReminder = customReminder;
		this.isProcessed = isProcessed;
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Birthday other = (Birthday) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Birthday [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName="
				+ middleName + ", birthDay=" + birthDay + ", birthMonth=" + birthMonth + ", willRemindOneDayPrior="
				+ willRemindOneDayPrior + ", willRemindOneWeekPrior=" + willRemindOneWeekPrior
				+ ", willRemindCustomTime=" + willRemindCustomTime + ", customReminder=" + customReminder
				+ ", isProcessed=" + isProcessed + "]";
	}
	
	public void updateFromDTO(BirthdayDTO birthdayDTO) {
		this.lastName = birthdayDTO.getLastName();
		this.firstName = birthdayDTO.getFirstName();
		this.birthDay = birthdayDTO.getBirthDay();
		this.birthMonth = birthdayDTO.getBirthMonth();
		this.willRemindOneDayPrior = birthdayDTO.isWillRemindOneDayPrior();
		this.willRemindOneWeekPrior = birthdayDTO.isWillRemindOneWeekPrior();
		this.willRemindCustomTime = birthdayDTO.isWillRemindCustomTime();
		this.customReminder = birthdayDTO.getCustomReminder();
	}
	
}
