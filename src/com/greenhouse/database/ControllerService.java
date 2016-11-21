package com.greenhouse.database;

import java.util.ArrayList;
import java.util.List;

import com.greenhouse.ui.Launcher;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016/2/20 PM 1:09:02 
* @version		1.0  
* @description  ControllerService
*/
public class ControllerService {
	
	private DatabaseHelper databaseHelper;
	
	public ControllerService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	//test success  
	public void init(String mac, String ip) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "insert into controller(mac, ip, name, connected, position, day_threshold, night_threshold, "
				+ "thre1, thre2, thre3, thre4, thre5, thre6, thre7) values(?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?)";
		Object[] object = new Object[]{mac, ip, "controller", false, 1, 8, 18, 2, 5, 10, 2, 5, 10, 1000};
		db.execSQL(s, object);
		db.close();
	}

	
	public String queryIp(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select ip from controller where mac=?";
		String ip=null;
		String[] ss = new String[]{mac};
		Cursor cursor = db.rawQuery(s, ss);
		if(cursor.moveToNext()) {
			ip = cursor.getString(cursor.getColumnIndex("ip"));
		}
		cursor.close();
		db.close();
		return ip;
	}
	
	public Integer queryDay(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select day_threshold from controller where mac=?";
		Integer day = null;
		String[] ss = new String[]{mac};
		Cursor cursor = db.rawQuery(s, ss);
		if(cursor.moveToNext()) {
			day = cursor.getInt(cursor.getColumnIndex("day_threshold"));
		}
		cursor.close();
		db.close();
		return day;
	}
	
	public Integer queryNight(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select night_threshold from controller where mac=?";
		Integer night = null;
		String[] ss = new String[]{mac};
		Cursor cursor = db.rawQuery(s, ss);
		if(cursor.moveToNext()) {
			night = cursor.getInt(cursor.getColumnIndex("night_threshold"));
		}
		cursor.close();
		db.close();
		return night;
	}
	
	public void modifyDayNight(Integer day, Integer night) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set day_threshold=?, night_threshold=? where mac=?";
		Object[] object = new Object[]{day, night, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	public void modifyThredshold1(Integer thre) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set thre1=? where mac=?";
		Object[] object = new Object[]{thre, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	public void modifyThredshold2(Integer thre) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set thre2=? where mac=?";
		Object[] object = new Object[]{thre, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	public void modifyThredshold3(Integer thre) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set thre3=? where mac=?";
		Object[] object = new Object[]{thre, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	public void modifyThredshold4(Integer thre) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set thre4=? where mac=?";
		Object[] object = new Object[]{thre, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	public void modifyThredshold5(Integer thre) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set thre5=? where mac=?";
		Object[] object = new Object[]{thre, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	public void modifyThredshold6(Integer thre) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set thre6=? where mac=?";
		Object[] object = new Object[]{thre, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	public void modifyThredshold7(Integer thre) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set thre7=? where mac=?";
		Object[] object = new Object[]{thre, Launcher.selectMac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	
	//test success
	public String queryMac(Integer id) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select mac from controller limit " + (id - 1) + ",1";
		Cursor cursor = db.rawQuery(s, null);
		if(cursor.moveToNext()) {
			String mac = cursor.getString(cursor.getColumnIndex("mac"));
			return mac;
		}
		cursor.close();
		return null;
	}
	
	public void modifyIp(String ip, String mac) {//�޸�Ip
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update controller set ip=? where mac=?";
		Object[] object = new Object[]{ip, mac};
		db.execSQL(s, object);//�޸�����
		db.close();
	}
	
	//test success
	public List<String> getAllMac() {//����jack��mac�б�
		List<String> macs = new ArrayList<String>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select mac from controller";
		Cursor cursor = db.rawQuery(s, null);
		while(cursor.moveToNext()) {
			String mac = cursor.getString(cursor.getColumnIndex("mac"));
			macs.add(mac);
		}
		cursor.close();
		db.close();
		return macs;
	}
	
	public List<Integer> getAllThredshold() {
		List<Integer> thres = new ArrayList<Integer>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from controller where mac=?";
		String[] ss = new String[]{Launcher.selectMac};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			thres.add(cursor.getInt(cursor.getColumnIndex("thre1")));
			thres.add(cursor.getInt(cursor.getColumnIndex("thre2")));
			thres.add(cursor.getInt(cursor.getColumnIndex("thre3")));
			thres.add(cursor.getInt(cursor.getColumnIndex("thre4")));
			thres.add(cursor.getInt(cursor.getColumnIndex("thre5")));
			thres.add(cursor.getInt(cursor.getColumnIndex("thre6")));
			thres.add(cursor.getInt(cursor.getColumnIndex("thre7")));
//			Log.e("SQL Controller", "thre = " + cursor.getInt(cursor.getColumnIndex("thre1")));
//			Log.e("SQL Controller", "thre = " + cursor.getInt(cursor.getColumnIndex("thre2")));
//			Log.e("SQL Controller", "thre = " + cursor.getInt(cursor.getColumnIndex("thre3")));
//			Log.e("SQL Controller", "thre = " + cursor.getInt(cursor.getColumnIndex("thre4")));
//			Log.e("SQL Controller", "thre = " + cursor.getInt(cursor.getColumnIndex("thre5")));
//			Log.e("SQL Controller", "thre = " + cursor.getInt(cursor.getColumnIndex("thre6")));
//			Log.e("SQL Controller", "thre = " + cursor.getInt(cursor.getColumnIndex("thre7")));
		}
		
		cursor.close();
		db.close();
		return thres;
	}
	
	
	
	

	
	public void deleteController(String mac) {//ɾ��
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "delete from controller where mac=?";
		Object[] object = new Object[]{mac};
		db.execSQL(s, object);
		db.close();
	}
	
	public long getCount() {//??
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		String s = "select count(*) from controller";
		Cursor cursor = db.rawQuery(s, null);
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		cursor.close();
		db.close();
		return count;
	}
	
	public boolean isMacExist(String mac) {//����
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		String s = "select mac from controller where mac=?";
		String[] ss = new String[]{mac};
		boolean bool=false;
		Cursor cursor = db.rawQuery(s, ss);//��ռλ��
		if(cursor.moveToNext()) {
			bool=true;
			/*cursor.close();
			return true;*/
		}
		cursor.close();
		db.close();
		return bool;
	}

//	public void init(String mac, String ip) {
//		// TODO �Զ����ɵķ������
//		SQLiteDatabase db = databaseHelper.getWritableDatabase();//��д�ķ�ʽ�����ݿ��Ӧ��SQLiteDatabase����
//		String s = "insert into controller(mac, ip, name, connected, position) values(?,?,?,?,?)";
//		Object[] object = new Object[]{mac, ip, "controller", false, 1};
//		//db.insert(table-controller, nullColumnHack-nullֵ�����е�����, values-mac,ip,name,connected,position);
//		/*ContentValues vs=new ContentValues();
//		vs.put("mac", "1");
//		vs.put("ip", "001");
//		db.insert("controller", null, vs);*/
//		db.execSQL(s, object);//ִ��sql���
//	}
}
