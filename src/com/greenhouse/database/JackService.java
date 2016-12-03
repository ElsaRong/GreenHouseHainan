package com.greenhouse.database;

import java.util.ArrayList;
import java.util.List;

import com.greenhouse.model.Jack;
import com.greenhouse.model.Sensor;
import com.greenhouse.ui.JackFragmentShowinfo;
import com.greenhouse.ui.Launcher;
import com.greenhouse.ui.Timer;
import com.greenhouse.util.Const;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import net.tsz.afinal.exception.DbException;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016-2-20 1:09:09 
* @version		1.0  
* @description		Jack 
*/
public class JackService {

	private final static String TAG = "JackService";
	
	private DatabaseHelper databaseHelper;
	
	public JackService(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	//test success
	public void init(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "insert into jack(mac, jackid, name, drawable, switch_state, bund, sensors, "
				+ "bundtype1, current_value1, day_threshold1, night_threshold1,"
				+ "bundtype2, current_value2, day_threshold2, night_threshold2,"
				+ "bundtype3, current_value3, day_threshold3, night_threshold3,"
				+ "bundtype4, current_value4, day_threshold4, night_threshold4,"
				+ "bundtype5, current_value5, day_threshold5, night_threshold5,"
				+ "bundtype6, current_value6, day_threshold6, night_threshold6,"
				+ "bundtype7, current_value7, day_threshold7, night_threshold7, "
				+ "start, poweron, poweroff, cycle, stop) " +
				   "values(?,?,?,?,?,?,?,?,?,?," +
				          "?,?,?,?,?,?,?," +
				          "?,?,?,?,?,?," +
					      "?,?,?,?,?,?,?,?,?,?," +
				          "?,?,?,?,?,?,?)"; 
		db.beginTransaction();
		try {
			for (int i=0; i<Const.JACK_SUM; i++) {
				db.execSQL(s, new Object[]{mac,(i+1),(i+1)+"","R.drawable.gray_hps", 0, 0, "00000000", 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
			}
//			db.execSQL(s, new Object[]{mac,1,"1","R.drawable.gray_hps", 0, 1, "0100000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,2,"2","R.drawable.gray_hps", 0, 1, "1100000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,3,"3","R.drawable.gray_hps", 0, 1, "0011000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,4,"4","R.drawable.gray_hps", 0, 1, "0000000000", 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			
//			db.execSQL(s, new Object[]{mac,5,"5","R.drawable.gray_hps", 0, 2, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,6,"6","R.drawable.gray_hps", 0, 2, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,7,"7","R.drawable.gray_water_pump", 0, 2, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,8,"8","R.drawable.gray_water_pump", 0, 2, "0000000000", 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "2016-05-22","01:30", "00:30", 0, "0000-00-00"});
//			
//			db.execSQL(s, new Object[]{mac,9,"9","R.drawable.gray_water_pump", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,10,"10","R.drawable.gray_water_pump", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,11,"11","R.drawable.gray_air_conditioning", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,12,"12","R.drawable.gray_air_conditioning", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			
//			db.execSQL(s, new Object[]{mac,13,"13","R.drawable.gray_fanner", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,14,"14","R.drawable.gray_fanner", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,15,"15","R.drawable.gray_fanner", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,16,"16","R.drawable.gray_water_pump", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			
//			db.execSQL(s, new Object[]{mac,17,"17","R.drawable.gray_water_pump", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,18,"18","R.drawable.gray_fan", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,19,"19","R.drawable.gray_fan", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,20,"20","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			
//			db.execSQL(s, new Object[]{mac,21,"21","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,22,"22","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,23,"23","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,24,"24","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			
//			db.execSQL(s, new Object[]{mac,25,"25","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,26,"26","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,27,"27","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,28,"28","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			
//			db.execSQL(s, new Object[]{mac,29,"29","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,30,"30","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,31,"31","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,32,"32","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//		
//			db.execSQL(s, new Object[]{mac,33,"33","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,34,"34","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
//			db.execSQL(s, new Object[]{mac,35,"35","R.drawable.jack", 0, 0, "0000000000", 1, 00, 9999, 0000, 1, 00, 9999, 0000, 1, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, 0, 00, 9999, 0000, "0000-00-00","00:00", "00:00", 0, "0000-00-00"});
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.endTransaction();
		db.close();
	}
	
	
	public void modifyControllerJackNameTest(String name, int jackid, String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set name=? where jackId=? and mac=?";
		Object[] object = new Object[]{name, jackid, mac};
		db.execSQL(s,object);
		db.close();
	}
	
	public void modifyControllerJackDrawableTest(String drawable, int jackid, String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set drawable=? where jackId=? and mac=?";
		Object[] object = new Object[]{drawable, jackid, mac};
		db.execSQL(s,object);
		db.close();
	}
	
	public void modifyJackBund(int bund, int jackid, String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set bund=? where jackId=? and mac=?";
		Object[] object = new Object[]{bund, jackid, mac};
		db.execSQL(s,object);
		db.close();
	}
	
	public void deleteAllJack(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "delete from jack where mac=?";
		Object[] object = new Object[]{mac};
		db.execSQL(s, object);
		db.close();
	}
	
//	public ArrayList<Jack> getAllJackInfo(String mac) {
//		ArrayList<Jack> jacks = new ArrayList<Jack>();
//		SQLiteDatabase db = databaseHelper.getWritableDatabase();
//		String s = "select * from jack where mac=?";
//		String[] ss = new String[]{mac};
//		Cursor cursor = db.rawQuery(s, ss);
//		while(cursor.moveToNext()) {
//			
//		}
//		cursor.close();
//		return jacks;
//	}
	
	/**
	 * @Title:       modifyJackCurrentValue
	 * @description: TODO 根据sen报文实时更新数据库Jack表中的currentvalue1-7的值，而且是根据绑定传感器情况计算所得的平均
	 * @param        @param jackId
	 * @param        @param current_value
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 24, 2016, 4:20:31 PM
	 */
	public void modifyJackCurrentValue(int jackId, int[] current_value) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Integer soiltemp = current_value[0];
		Integer soilhum = current_value[1];
		Integer soilph = current_value[2];
		Integer airtemp = current_value[3];
		Integer airhum = current_value[4];
		Integer co2 = current_value[5];
		Integer illum = current_value[6];
//		Log.e(TAG, "修改平均值: JackId = " + jackId + ", Value = " + soiltemp +","+ soilhum 
//				+","+ soilph +","+ airtemp +","+ airhum +","+ co2 +","+ illum + "");
		String s = "update jack set current_value1=?, current_value2=?,current_value3=?,"
				+ "current_value4=?,current_value5=?,current_value6=?,current_value7=? where jackId=? and mac=?";
		Object[] object = new Object[]{soiltemp, soilhum, soilph, airtemp, airhum, co2, illum, jackId, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}
	
	//test success
	public ArrayList<Jack> getAllJack(String mac) {
		ArrayList<Jack> jacks = new ArrayList<Jack>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from jack where mac=?";
		String[] ss = new String[]{mac};
		Cursor cursor = db.rawQuery(s, ss);
		int i = 0;
		while(cursor.moveToNext()) {
			i++;
			Log.v(TAG, "(数据库)第"+i+"个插座平均值: "+cursor.getInt(cursor.getColumnIndex("current_value1"))
				+","+cursor.getInt(cursor.getColumnIndex("current_value2"))
				+","+cursor.getInt(cursor.getColumnIndex("current_value3"))
				+","+cursor.getInt(cursor.getColumnIndex("current_value4"))
				+","+cursor.getInt(cursor.getColumnIndex("current_value5"))
				+","+cursor.getInt(cursor.getColumnIndex("current_value6"))
				+","+cursor.getInt(cursor.getColumnIndex("current_value7")));
			Jack jack = new Jack();
			jack.setMac(cursor.getString(cursor.getColumnIndex("mac")));
			jack.setJackId(cursor.getInt(cursor.getColumnIndex("jackid")));
			jack.setName(cursor.getString(cursor.getColumnIndex("name")));
			jack.setDrawable(cursor.getString(cursor.getColumnIndex("drawable")));
			jack.setSwitchstate(cursor.getInt(cursor.getColumnIndex("switch_state")));
			jack.setBund(cursor.getInt(cursor.getColumnIndex("bund")));
			jack.setSensors(cursor.getString(cursor.getColumnIndex("sensors")));
			
			jack.setbundtype1(cursor.getInt(cursor.getColumnIndex("bundtype1")));
			jack.setCurrentValue1(cursor.getInt(cursor.getColumnIndex("current_value1")));
			jack.setDay_threshold1(cursor.getInt(cursor.getColumnIndex("day_threshold1")));
			jack.setNight_threshold1(cursor.getInt(cursor.getColumnIndex("night_threshold1")));
			
			jack.setbundtype2(cursor.getInt(cursor.getColumnIndex("bundtype2")));
			jack.setCurrentValue2(cursor.getInt(cursor.getColumnIndex("current_value2")));
			jack.setDay_threshold2(cursor.getInt(cursor.getColumnIndex("day_threshold2")));
			jack.setNight_threshold2(cursor.getInt(cursor.getColumnIndex("night_threshold2")));
			
			jack.setbundtype3(cursor.getInt(cursor.getColumnIndex("bundtype3")));
			jack.setCurrentValue3(cursor.getInt(cursor.getColumnIndex("current_value3")));
			jack.setDay_threshold3(cursor.getInt(cursor.getColumnIndex("day_threshold3")));
			jack.setNight_threshold3(cursor.getInt(cursor.getColumnIndex("night_threshold3")));
			
			jack.setbundtype4(cursor.getInt(cursor.getColumnIndex("bundtype4")));
			jack.setCurrentValue4(cursor.getInt(cursor.getColumnIndex("current_value4")));
			jack.setDay_threshold4(cursor.getInt(cursor.getColumnIndex("day_threshold4")));
			jack.setNight_threshold4(cursor.getInt(cursor.getColumnIndex("night_threshold4")));
			
			jack.setbundtype5(cursor.getInt(cursor.getColumnIndex("bundtype5")));
			jack.setCurrentValue5(cursor.getInt(cursor.getColumnIndex("current_value5")));
			jack.setDay_threshold5(cursor.getInt(cursor.getColumnIndex("day_threshold5")));
			jack.setNight_threshold5(cursor.getInt(cursor.getColumnIndex("night_threshold5")));
			
			jack.setbundtype6(cursor.getInt(cursor.getColumnIndex("bundtype6")));
			jack.setCurrentValue6(cursor.getInt(cursor.getColumnIndex("current_value6")));
			jack.setDay_threshold6(cursor.getInt(cursor.getColumnIndex("day_threshold6")));
			jack.setNight_threshold6(cursor.getInt(cursor.getColumnIndex("night_threshold6")));

			jack.setbundtype7(cursor.getInt(cursor.getColumnIndex("bundtype7")));
			jack.setCurrentValue7(cursor.getInt(cursor.getColumnIndex("current_value7")));
			jack.setDay_threshold7(cursor.getInt(cursor.getColumnIndex("day_threshold7")));
			jack.setNight_threshold7(cursor.getInt(cursor.getColumnIndex("night_threshold7")));
			
			jack.setStart(cursor.getString(cursor.getColumnIndex("start")));
			jack.setPoweron(cursor.getString(cursor.getColumnIndex("poweron")));
			jack.setPoweroff(cursor.getString(cursor.getColumnIndex("poweroff")));
			jack.setCycle(cursor.getInt(cursor.getColumnIndex("cycle")));
			jack.setStop(cursor.getString(cursor.getColumnIndex("stop")));
			jacks.add(jack);
		}
		cursor.close();
		db.close();
		return jacks;
	}
	

	public ArrayList<Jack> getAllJackTask(String mac) {
		ArrayList<Jack> jacks = new ArrayList<Jack>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from jack where mac=?";
		String[] ss = new String[]{mac};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			Jack jack = new Jack();
			jack.setJackId(cursor.getInt(cursor.getColumnIndex("jackid")));
			jack.setName(cursor.getString(cursor.getColumnIndex("name")));
			jack.setStart(cursor.getString(cursor.getColumnIndex("start")));
			jack.setPoweron(cursor.getString(cursor.getColumnIndex("poweron")));
			jack.setPoweroff(cursor.getString(cursor.getColumnIndex("poweroff")));
			jack.setCycle(cursor.getInt(cursor.getColumnIndex("cycle")));
			jack.setStop(cursor.getString(cursor.getColumnIndex("stop")));
			jacks.add(jack);
		}
		cursor.close();
		db.close();
		return jacks;
	}
	
	public ArrayList<Jack> getAllJackNameAndState(String mac) {
		ArrayList<Jack> jacks = new ArrayList<Jack>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from jack where mac=?";
		String[] ss = new String[]{mac};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			Jack jack = new Jack();
			jack.setJackId(cursor.getInt(cursor.getColumnIndex("jackid")));
			jack.setName(cursor.getString(cursor.getColumnIndex("name")));
			jack.setSwitchstate(cursor.getInt(cursor.getColumnIndex("switch_state")));
			jacks.add(jack);
		}
		cursor.close();
		db.close();
		return jacks;
	}
	
	public String getJackBundSensors(int jackId) {
		String bundSensors = null;
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		String s = "select * from jack where sensors=? and mac=?";
		String[] ss = new String[]{jackId+"", Launcher.selectMac};
		Cursor cursor = db.rawQuery(s, ss);
		while(cursor.moveToNext()) {
			bundSensors = cursor.getString(cursor.getColumnIndex("sensors"));
		}
		cursor.close();
		db.close();
		return bundSensors;
	}
	
	public void deleteSensorTask(Integer jackId) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set bund=? where mac=? and jackid=?";
		Object[] object = new Object[]{0, Launcher.selectMac, jackId};
		db.execSQL(s, object);
		db.close();
	}
	
	public Jack queryJackIdTask(Integer jackId) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from jack where mac=? and jackid=?";
		String[] ss = new String[]{Launcher.selectMac, jackId+""};
		Cursor cursor = db.rawQuery(s, ss);
		if(cursor.moveToNext()) {
			Jack jack = new Jack();
			jack.setStart(cursor.getString(cursor.getColumnIndex("start")));
			jack.setPoweron(cursor.getString(cursor.getColumnIndex("poweron")));
			jack.setPoweroff(cursor.getString(cursor.getColumnIndex("poweroff")));
			jack.setCycle(cursor.getInt(cursor.getColumnIndex("cycle")));
			cursor.close();
			return jack;
		}
		cursor.close();
		db.close();
		return null;
	}
	

	
	//STAT
	public void modifySwitchstate(String switchstate) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set switch_state=? where jackId=? and mac=?";
		db.beginTransaction();
		try {
			for (int i = 0; i < 35; i++) {
				Integer state = Integer.parseInt(switchstate.substring(i, i+1));
				Object[] object = new Object[]{state, (i + 1), Launcher.selectMac};
				db.execSQL(s,object);
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.endTransaction();
		db.close();
	}
	
	
	//CONT
	public void modifySwitchstate(int switchstate, int jackId) {
		Log.e(TAG, "(SQLite) modify jackId="+jackId + ", switchstate="+switchstate);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set switch_state=? where jackId=? and mac=?";
		Object[] object = new Object[]{switchstate, jackId, Launcher.selectMac};
		db.execSQL(s,object);
		db.close();
	}
	
	
	
	public void modifyName(Integer i, String name) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set name=? where mac=? and jackId=?";
		Object[] object = new Object[]{name, Launcher.selectMac, i};
		db.execSQL(s, object);
		db.close();
	}
	
	public void modifySensorTask(Integer jackId, List<Sensor> sensors,Integer day1, Integer night1,Integer day2, Integer night2,Integer day3, Integer night3,Integer day4, Integer night4,Integer day5, Integer night5,Integer day6, Integer night6,Integer day7, Integer night7) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String sensorId = "";
		for (Sensor sensor : sensors) {
			sensorId += sensor.getId() + ",";
		}
		String s = "update jack set sensors=?, day_threshold1=?, night_threshold1=?,day_threshold2=?, night_threshold2=?,day_threshold3=?, night_threshold3=?,day_threshold4=?, night_threshold4=?,day_threshold5=?, night_threshold5=?,day_threshold6=?, night_threshold6=?,day_threshold7=?, night_threshold7=?,  bund=? where jackId=? and mac=?";
		Object[] object = new Object[]{sensorId, day1, night1, day2, night2, day3, night3, day4, night4, day5, night5, day6, night6, day7, night7,  1, jackId, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}
	
	/**
	 * @Title:       modifyAllTypeSensorTask
	 * @description: TODO 保存指定MAC和插座ID上的全部(七种)类型传感器任务,针对BUND和BUDD报文
	 * @param        @param jackId      指定插座
	 * @param        @param bundSensor  绑定传感器,eg.11010000
	 * @param        @param deviceType  设备类型,高于or低于阈值动作
	 * @param        @param day1        白天门限
	 * @param        @param night1
	 * @param        @param day2
	 * @param        @param night2
	 * @param        @param day3
	 * @param        @param night3
	 * @param        @param day4
	 * @param        @param night4
	 * @param        @param day5
	 * @param        @param night5
	 * @param        @param day6
	 * @param        @param night6
	 * @param        @param day7
	 * @param        @param night7
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 21, 2016, 2:56:57 PM
	 */
	public void modifyAllTypeSensorTask(Integer jackId, String bundSensor, Integer deviceType, 
			Integer bundtype1, Integer day1, Integer night1, 
			Integer bundtype2, Integer day2, Integer night2, 
			Integer bundtype3, Integer day3, Integer night3,
			Integer bundtype4, Integer day4, Integer night4, 
			Integer bundtype5, Integer day5, Integer night5, 
			Integer bundtype6, Integer day6, Integer night6, 
			Integer bundtype7, Integer day7, Integer night7) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Object[] object;
		String s = "update jack set bund=?, sensors=?, "
				+ "bundtype1=?, day_threshold1=?, night_threshold1=?, " 
				+ "bundtype2=?, day_threshold2=?, night_threshold2=?, "
				+ "bundtype3=?, day_threshold3=?, night_threshold3=?, "
				+ "bundtype4=?, day_threshold4=?, night_threshold4=?, "
				+ "bundtype5=?, day_threshold5=?, night_threshold5=?, "
				+ "bundtype6=?, day_threshold6=?, night_threshold6=?, "
				+ "bundtype7=?, day_threshold7=?, night_threshold7=? "
				+ "where jackId=? and mac=?";
		object = new Object[]{1, bundSensor, 
				bundtype1, day1, night1, 
				bundtype2, day2, night2, 
				bundtype3, day3, night3, 
				bundtype4, day4, night4, 
				bundtype5, day5, night5, 
				bundtype6, day6, night6, 
				bundtype7, day7, night7, 
				jackId, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}
	
//	public void modifySensorTask(Integer jackId, String sensors,Integer day, Integer night, Integer sensortype) {
//		SQLiteDatabase db = databaseHelper.getWritableDatabase();
//		String s = "";
//		Object[] object;
//		switch(sensortype) {
//		case 1:
//			s = "update jack set sensors=?, day_threshold1=?, night_threshold1=?, sensortype=?, bund=? where jackId=? and mac=?";
//			object = new Object[]{sensors, day, night, sensortype, 1, jackId, Launcher.selectMac};
//			db.execSQL(s, object);
//			break;
//		case 2:
//			s = "update jack set sensors=?, day_threshold2=?, night_threshold2=?, sensortype=?, bund=? where jackId=? and mac=?";
//			object = new Object[]{sensors, day, night, sensortype, 1, jackId, Launcher.selectMac};
//			db.execSQL(s, object);
//			break;
//		case 3:
//			s = "update jack set sensors=?, day_threshold3=?, night_threshold3=?, sensortype=?, bund=? where jackId=? and mac=?";
//			object = new Object[]{sensors, day, night, sensortype, 1, jackId, Launcher.selectMac};
//			db.execSQL(s, object);
//			break;
//		case 4:
//			s = "update jack set sensors=?, day_threshold4=?, night_threshold4=?, sensortype=?, bund=? where jackId=? and mac=?";
//			object = new Object[]{sensors, day, night, sensortype, 1, jackId, Launcher.selectMac};
//			db.execSQL(s, object);
//			break;
//		case 5:
//			s = "update jack set sensors=?, day_threshold5=?, night_threshold5=?, sensortype=?, bund=? where jackId=? and mac=?";
//			object = new Object[]{sensors, day, night, sensortype, 1, jackId, Launcher.selectMac};
//			db.execSQL(s, object);
//			break;
//		case 6:
//			s = "update jack set sensors=?, day_threshold6=?, night_threshold6=?, sensortype=?, bund=? where jackId=? and mac=?";
//			object = new Object[]{sensors, day, night, sensortype, 1, jackId, Launcher.selectMac};
//			db.execSQL(s, object);
//			break;
//		case 7:
//			s = "update jack set sensors=?, day_threshold7=?, night_threshold7=?, sensortype=?, bund=? where jackId=? and mac=?";
//			object = new Object[]{sensors, day, night, sensortype, 1, jackId, Launcher.selectMac};
//			db.execSQL(s, object);
//			break;
//			default:
//				break;
//		}
//		db.close();
//		
//	}
	
	// Timer���������ɾ����ʱ����
	public void deleteJackTask(String jacks) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set bund=?, start=?, poweron=?, poweroff=?, cycle=? where jackId=? and mac=?";
		for (int i = 0; i < 48; i++) {
			if (jacks.substring(i, i + 1).equals("1")) {
				Object[] object = new Object[]{0, "0000-00-00", "00:00", "00:00", 0, (i + 1), Launcher.selectMac};
				db.execSQL(s, object);
			} 
		}
		db.close();
	}
	
	public void deleteOneJackTask(String jackId) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set bund=?, start=?, poweron=?, poweroff=?, cycle=? where jackId=? and mac=?";
		Object[] object = new Object[]{0, "0000-00-00", "00:00", "00:00", 0, jackId, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}
	
	/**
	 * @Title:       modifyJackTask
	 * @description: TODO 根据接收到的TASK报文，同步定时任务
	 * @param        @param start
	 * @param        @param poweron
	 * @param        @param poweroff
	 * @param        @param cycle
	 * @param        @param jacks
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 24, 2016, 10:41:00 PM
	 */
	public void modifyJackTask(String start, String poweron, String poweroff, Integer cycle, String jackId){
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set bund=?, start=?, poweron=?, poweroff=?, cycle=? where jackId=? and mac=?";
		Object[] object = new Object[]{2, start, poweron, poweroff, cycle, jackId, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}

		
	public void modifyJackSensorRestriction(Integer jackId, String sensors, int day, int night, int sensortype){
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update jack set bund=?, sensors=?, sensortype=?, day_threshold=?, night_threshold=? where jackId=? and mac=?";
		Object[] object = new Object[]{1, sensors, sensortype, day, night, jackId, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}


	// HAVE
	public void modifyJackTaskMark(String taskmark) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			for (int i = 0; i < 48; i++) {
				Integer state = Integer.parseInt(taskmark.substring(i, i+1));
				if (state == 1) {
					//					String s = "update jack set bund=? where jackId=? and mac=?";
					//					Object[] object = new Object[]{2, (i + 1), Launcher.selectMac};
					//					db.execSQL(s,object);
				} else if (state == 0) {
					String s = "update jack set bund=?, start=?, poweron=?, poweroff=?, cycle=? where jackId=? and mac=?";
					Object[] object = new Object[]{0, "0000-00-00", "00:00", "00:00", 0, (i + 1), Launcher.selectMac};
					db.execSQL(s,object);
				}
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.endTransaction();
		db.close();
	}
	

	
	
}
