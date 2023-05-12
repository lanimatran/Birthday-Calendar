package com.lanimatran.birthdaycalendar.classes.DTOs;

import java.util.Date;

public class BirthdayDTO {
	private long id;
	private String firstName;	
	private String lastName;	
	private String middleName;	
	private int birthDay;	
	private int birthMonth;		
	private boolean willRemindOneDayPrior;	
	private boolean willRemindOneWeekPrior;	
	private boolean willRemindCustomTime;	
	private Date customReminder;	
	
	public long getID() {
		return id;
	}

	public void setID(long iD) {
		id = iD;
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
	
	public BirthdayDTO() {
		super();
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.middleName = "";
		this.birthDay = 0;
		this.birthMonth = 0;
		this.willRemindOneDayPrior = false;
		this.willRemindOneWeekPrior = false;
		this.willRemindCustomTime = false;
		this.customReminder = null;
	}

	public BirthdayDTO(long id, String firstName, String lastName, String middleName, int birthDay, int birthMonth,
			boolean willRemindOneDayPrior, boolean willRemindOneWeekPrior, boolean willRemindCustomTime,
			Date customReminder) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.willRemindOneDayPrior = willRemindOneDayPrior;
		this.willRemindOneWeekPrior = willRemindOneWeekPrior;
		this.willRemindCustomTime = willRemindCustomTime;
		this.customReminder = customReminder;
	}

	@Override
	public String toString() {
		return "BirthdayDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName="
				+ middleName + ", birthDay=" + birthDay + ", birthMonth=" + birthMonth + ", willRemindOneDayPrior="
				+ willRemindOneDayPrior + ", willRemindOneWeekPrior=" + willRemindOneWeekPrior
				+ ", willRemindCustomTime=" + willRemindCustomTime + ", customReminder=" + customReminder + "]";
	}	
}
