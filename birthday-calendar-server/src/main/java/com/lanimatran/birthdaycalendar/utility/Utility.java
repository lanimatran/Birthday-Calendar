package com.lanimatran.birthdaycalendar.utility;

import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.impl.DefaultClock;

public class Utility {
	public static Date getCurrentDateTime() {
		return DefaultClock.INSTANCE.now();
	}
	
	public static Date createDateFromComponent(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);
		return calendar.getTime();
	}
	
	public static Date createDateFromComponentAddedByAmount(int year, int month, int day, int hour, int minute, int second, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);
		calendar.add(field, amount);
		return calendar.getTime();
	}
}
