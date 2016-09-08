package com.jack.signdemo;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Utils {
	
	/**
	 * 获取当月的 天数
	 */
	public static int getCurrentMonthDay() {
		
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	public static String getTime(long time) {
		
		DateFormat format = new DateFormat();
		String str = (String) format.format("yyyy年MM月", time);
		return str;
		
	}
	
	public static int getCurrentMonthStart() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat format = new SimpleDateFormat("E");
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == 1)
			day = 7;
		else
			day = day - 1;
		
		return day;
	}
	
	public static int getDayOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
}
