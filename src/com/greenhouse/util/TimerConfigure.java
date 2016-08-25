package com.greenhouse.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class TimerConfigure {	

	public static int calculate (int a, int b, int c, int d, int e){		
		int x;
		x = (c+d+a+b)*e;
		return x;	
	}
	
	//java求很多分钟后的时间
	public static String addDateMinut(String day, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		
		try {
			date = format.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(date == null) 
			return "";
		System.out.println("front:"+format.format(date));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE,x);
		date = cal.getTime();
		System.out.println("after:"+format.format(date));
		cal = null;
		return format.format(date);		
	}
	
	
}
