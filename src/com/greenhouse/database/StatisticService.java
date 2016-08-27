package com.greenhouse.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StatisticService {
	
	private DatabaseHelper databaseHelper;
	public StatisticService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void init(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "insert into statistic(mac, year, month, day, hour, "
				+ "soiltemp, soilhum, soilph, airtemp, airhum, co2, illum) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] object = new Object[]{mac, 2016, 8, 21, 19, 28, 80, 70, 28, 80, 10000};
		db.execSQL(s, object);
		db.close();
	}
	
	public void insertRecord(String mac, Integer year, Integer month, Integer day, Integer hour,
			Integer soiltemp, Integer soilhum, Integer soilph, Integer airtemp, Integer airhum, Integer co2, Integer illum) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "insert into statistic(mac, year, month, day, hour, "
				+ "soiltemp, soilhum, soilph, airtemp, airhum, co2, illum) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] object = new Object[]{mac, year, month, day, hour,
				soiltemp, soilhum, soilph, airtemp, airhum, co2, illum};
		db.execSQL(s, object);
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
		boolean b = true;
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where year=? and month=? and day=? and hour=?";
		String[] ss = new String[]{year+"", month+"", day+"", hour+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			b = false;
		}
		cursor.close();
		return b;
	}
	
	public List<Integer> getSoiltempHistory(Integer month, Integer day) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where month=? and day=?";
		String[] ss = new String[]{month+"", day+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soiltemp"))));
		}
		cursor.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getSoilhumHistory(Integer month, Integer day) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where month=? and day=?";
		String[] ss = new String[]{month+"", day+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soilhum"))));
		}
		cursor.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getSoilphHistory(Integer month, Integer day) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where month=? and day=?";
		String[] ss = new String[]{month+"", day+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soilph"))));
		}
		cursor.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getAirtempHistory(Integer month, Integer day) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where month=? and day=?";
		String[] ss = new String[]{month+"", day+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("airtemp"))));
		}
		cursor.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getAirhumHistory(Integer month, Integer day) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where month=? and day=?";
		String[] ss = new String[]{month+"", day+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("airhum"))));
		}
		cursor.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getCo2History(Integer month, Integer day) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where month=? and day=?";
		String[] ss = new String[]{month+"", day+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("co2"))));
		}
		cursor.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	
	public List<Integer> getIllumHistory(Integer month, Integer day) {
		List<Integer> listHistory = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from statistic where month=? and day=?";
		String[] ss = new String[]{month+"", day+""};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			listHistory.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("illum"))));
		}
		cursor.close();
		if(listHistory.size()>0) {
			return listHistory;
		} else {
			return null;
		}
	}
	

	public List<Integer> getHistoryValue(Integer month, Integer day, Integer sensor_type) {
		List<Integer> listHistory = new ArrayList<Integer>();
		switch (sensor_type) {
		case 1:
			listHistory = getSoiltempHistory(month, day);
			break;
		case 2:
			listHistory = getSoilhumHistory(month, day);
			break;
		case 3:
			listHistory = getSoilphHistory(month, day);
			break;
		case 4:
			listHistory = getAirtempHistory(month, day);
			break;
		case 5:
			listHistory = getAirhumHistory(month, day);
			break;
		case 6:
			listHistory = getCo2History(month, day);
			break;
		case 7:
			listHistory = getIllumHistory(month, day);
			break;
			
		}
		return listHistory;
	}

	

}
