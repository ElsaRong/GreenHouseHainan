package com.greenhouse.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import com.greenhouse.ui.Launcher;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StatisticService {
	
	private final String TAG = "StatisticService";
	
	private DatabaseHelper databaseHelper;
	public StatisticService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void init(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "insert into statistic(mac, year, month, day, hour, minute, "
				+ "soiltemp, soilhum, soilph, airtemp, airhum, co2, illum) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] object = new Object[]{mac, 2016, 8, 21, 19, 0, 28, 80, 70, 28, 80, 10000};
		db.execSQL(s, object);
		db.close();
	}
	
	public void insertRecord(String mac, Integer year, Integer month, Integer day, Integer hour, Integer minute,
			Integer soiltemp, Integer soilhum, Integer soilph, Integer airtemp, Integer airhum, Integer co2, Integer illum) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "insert into statistic(mac, year, month, day, hour, minute,"
				+ "soiltemp, soilhum, soilph, airtemp, airhum, co2, illum) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] object = new Object[]{mac, year, month, day, hour, minute,
				soiltemp, soilhum, soilph, airtemp, airhum, co2, illum};
		db.execSQL(s, object);
		Log.e(TAG, "insert statistic success");
		db.close();
	}
	
	/**
	 * @Title:       isCurrentDataSaved
	 * @description: TODO 检查当前year/month/day/hour字段是否已经保存了一组sensor值
	 * @param        @param year
	 * @param        @param month
	 * @param        @param day
	 * @param        @param hour
	 * @param        @return 需要保存，返回true；否则返回false
	 * @return       boolean 
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 27, 2016, 9:58:49 PM
	 */
	public boolean isCurrentDataSaved(Integer year, Integer month, Integer day, Integer hour) {
		boolean b = false;
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where year=? and month=? and day=? and hour=?";
		String[] ss = new String[]{year+"", month+"", day+"", hour+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			b = true;
		}
		cursor.close();
		db.close();
		return b;
	}
	
//  每分钟保存一次
//	public boolean isCurrentDataSaved(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
//		boolean b = false;
//		SQLiteDatabase db = databaseHelper.getWritableDatabase();
//		String s = "select * from statistic where year=? and month=? and day=? and hour=? and minute=?";
//		String[] ss = new String[]{year+"", month+"", day+"", hour+"", minute+""};
//		Cursor cursor = db.rawQuery(s, ss);
//		while(cursor.moveToNext()) {
//			b = true;
//		}
//		cursor.close();
//		db.close();
//		return b;
//	}
	
	public List<Integer> getSoiltempHistory(Integer month, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		
		for (int i=0; i < time_span.intValue(); i++) {
			String s = "select * from statistic where month=? and mac=?";
			String[] ss = new String[]{(month - i) + "", Launcher.selectMac};
			Cursor cursor = db.rawQuery(s, ss);
			Log.e(TAG, "(SQLite) get history, month=" + (month-i));
			while(cursor.moveToNext()) {
				Log.e(TAG, "(SQLite) get");
				listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soiltemp"))));
			}
			cursor.close();
		}
		
		db.close();
		
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getSoilhumHistory(Integer month, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		for (int i=0; i < time_span.intValue(); i++) {
			String s = "select * from statistic where month=? and mac=?";
			String[] ss = new String[]{month+"", Launcher.selectMac};
			Cursor cursor = db.rawQuery(s, ss);
			while(cursor.moveToNext()) {
				listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soilhum"))));
			}
			cursor.close();
		}
		db.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getSoilphHistory(Integer month, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		for (int i=0; i < time_span.intValue(); i++) {
			String s = "select * from statistic where month=? and mac=?";
			String[] ss = new String[]{month+"", Launcher.selectMac};
			Cursor cursor = db.rawQuery(s, ss);
			while(cursor.moveToNext()) {
				listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soilph")))/10);
			}
			cursor.close();
		}
		db.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getAirtempHistory(Integer month, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		for (int i=0; i < time_span.intValue(); i++) {
			String s = "select * from statistic where month=? and mac=?";
			String[] ss = new String[]{month+"", Launcher.selectMac};
			Cursor cursor = db.rawQuery(s, ss);
			while(cursor.moveToNext()) {
				listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("airtemp"))));
			}
			cursor.close();
		}
		db.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getAirhumHistory(Integer month, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		for (int i=0; i < time_span.intValue(); i++) {
			String s = "select * from statistic where month=? and mac=?";
			String[] ss = new String[]{month+"", Launcher.selectMac};
			Cursor cursor = db.rawQuery(s, ss);
			while(cursor.moveToNext()) {
				listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("airhum"))));
			}
			cursor.close();
		}
		db.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getCo2History(Integer month, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		for (int i=0; i < time_span.intValue(); i++) {
			String s = "select * from statistic where month=? and mac=?";
			String[] ss = new String[]{month+"", Launcher.selectMac};
			Cursor cursor = db.rawQuery(s, ss);
			while(cursor.moveToNext()) {
				listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("co2"))));
			}
			cursor.close();
		}
		db.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getIllumHistory(Integer month, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		for (int i=0; i < time_span.intValue(); i++) {
			String s = "select * from statistic where month=? and mac=?";
			String[] ss = new String[]{month+"", Launcher.selectMac};
			Cursor cursor = db.rawQuery(s, ss);
			while(cursor.moveToNext()) {
				listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("illum"))));
			}
			cursor.close();
		}
		db.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	

	public List<Integer> getHistoryValue(Integer month, Integer sensor_type, Integer time_span) {
		List<Integer> listHistory = new ArrayList<Integer>();
		switch (sensor_type) {
		case 1:
			listHistory = getSoiltempHistory(month, time_span);
			break;
		case 2:
			listHistory = getSoilhumHistory(month, time_span);
			break;
		case 3:
			listHistory = getSoilphHistory(month, time_span);
			break;
		case 4:
			listHistory = getAirtempHistory(month, time_span);
			break;
		case 5:
			listHistory = getAirhumHistory(month, time_span);
			break;
		case 6:
			listHistory = getCo2History(month, time_span);
			break;
		case 7:
			listHistory = getIllumHistory(month, time_span);
			break;
			
		}
		return listHistory;
	}

	

}
