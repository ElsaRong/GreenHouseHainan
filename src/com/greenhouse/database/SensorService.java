package com.greenhouse.database;

import java.util.ArrayList;
import java.util.List;
import com.greenhouse.model.Sensor;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016/2/20 PM 1:09:17 
* @version		1.0  
* @description		Sensor insert updata delete  
*/
public class SensorService {
	
	private DatabaseHelper databaseHelper;

	public SensorService(Context context) {
		databaseHelper =new DatabaseHelper(context);
	}

	public void init(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "insert into sensor(mac, sensorid, online, soiltemp, soilhum, soilph, "
				+ "airtemp, airhum, co2, illum) "
				+ "values(?,?,?,?,?,?,?,?,?,?)"; 
		db.beginTransaction();
		try {
			for (int i=0; i<Const.SENSOR_SUM; i++)  {
				//初始化顺序：ID，online，土壤温度，土壤湿度，土壤酸碱度＊10，空气温度，空气湿度，CO2浓度，光照度
				db.execSQL(s, new Object[]{mac, (i+1), 0, 0, 0, 0, 0, 0, 0, 0});
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
		db.endTransaction();
		}
	}
	
	public void deleteAllSensor(String mac) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.execSQL("delete from sensor where mac=?", new Object[]{mac});
		db.close();
	}
	
	public List<Sensor> getSelectSensor(String sensorsid) {
		List<Sensor> sensors = new ArrayList<Sensor>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		for (int i = 0; i < Const.SENSOR_SUM; i++) {
			if (sensorsid.substring(i, i+1).equals("1")) {
				String s = "select * from sensor where sensorid=? and mac=?";
				String[] ss = new String[]{(i+1) + "", Launcher.selectMac};
				Cursor cursor = db.rawQuery(s, ss);
				while(cursor.moveToNext()) {
					Sensor sensor = new Sensor();
					sensor.setId(cursor.getInt(cursor.getColumnIndex("sensorid")));
					sensor.setOnline(cursor.getInt(cursor.getColumnIndex("online")));
					sensor.setSoiltemp(cursor.getInt(cursor.getColumnIndex("soiltemp")));
					sensor.setSoilhum(cursor.getInt(cursor.getColumnIndex("soilhum")));
					sensor.setSoilph(cursor.getInt(cursor.getColumnIndex("soilph")));				
					sensor.setAirtemp(cursor.getInt(cursor.getColumnIndex("airtemp")));
					sensor.setAirhum(cursor.getInt(cursor.getColumnIndex("airhum")));
					sensor.setCo2(cursor.getInt(cursor.getColumnIndex("co2")));
					sensor.setIllumination(cursor.getInt(cursor.getColumnIndex("illum")));	
					sensors.add(sensor);
				}
				cursor.close();
			}
		}
		db.close();
		return sensors;
	}
	
	/**
	 * @Title:       getSelectSensorAverage
	 * @description: TODO 输入绑定传感器10110000，求绑定传感器的7中类型平均值，返回Sensor类型
	 * @param        @param sensors
	 * @param        @return
	 * @return       Sensor
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 24, 2016, 4:11:31 PM
	 */
	public int[] getIntSelectSensorAverage(String sensors) {
		int[] selectSensorAverage = new int[]{0,0,0,0,0,0,0};
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
//		int averageSensorNum = SensorRecyclerView.selectSensors.size();
//		Sensor sensorAverage = new Sensor();
		Integer soiltemp=0, soilhum=0, soilph=0, airtemp=0, airhum=0, co2=0, illum=0;
		Integer soiltempnum=0, soilhumnum=0, soilphnum=0, airtempnum=0, airhumnum=0, co2num=0, illumnum=0;
		for (int i = 0; i < Const.SENSOR_SUM; i++) {
			if (sensors.substring(i, i+1).equals("1")) {
				String s = "select * from sensor where sensorid=? and mac=?";
				String[] ss = new String[]{(i+1) + "", Launcher.selectMac};
				Cursor cursor = db.rawQuery(s, ss);
				while(cursor.moveToNext()) {
					if (cursor.getInt(cursor.getColumnIndex("soiltemp"))!=0){
						soiltemp += cursor.getInt(cursor.getColumnIndex("soiltemp"));
						soiltempnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("soilhum"))!=0){
						soilhum += cursor.getInt(cursor.getColumnIndex("soilhum"));
						soilhumnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("soilph"))!=0){
						soilph += cursor.getInt(cursor.getColumnIndex("soilph"));
						soilphnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("airtemp"))!=0){
						airtemp += cursor.getInt(cursor.getColumnIndex("airtemp"));
						airtempnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("airhum"))!=0){
						airhum += cursor.getInt(cursor.getColumnIndex("airhum"));
						airhumnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("co2"))!=0){
						co2 += cursor.getInt(cursor.getColumnIndex("co2"));
						co2num++;
					}
					if (cursor.getInt(cursor.getColumnIndex("illum"))!=0){
						illum += cursor.getInt(cursor.getColumnIndex("illum"));
						illumnum++;
					}
				}
				cursor.close();
			}
		}
		
		if (soiltempnum!=0) {
			soiltemp = soiltemp/soiltempnum;
		}
		if (soilhumnum!=0) {
			soilhum = soilhum/soilhumnum;
		}
		if (soilphnum!=0) {
			soilph = soilph/soilphnum;
		}
		if (airtempnum!=0) {
			airtemp = airtemp/airtempnum;
		}
		if (airhumnum!=0) {
			airhum = airhum/airhumnum;
		}
		if (co2num!=0) {
			co2 = co2/co2num;
		}
		if (illumnum!=0) {
			illum = illum/illumnum;
		}
		
		selectSensorAverage = new int[]{soiltemp, soilhum, soilph, airtemp, airhum, co2, illum};
		db.close();
		return selectSensorAverage;
	}
	
	public Sensor getSelectSensorAverage(String sensors) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
//		int averageSensorNum = SensorRecyclerView.selectSensors.size();
		Sensor sensorAverage = new Sensor();
		Integer soiltemp=0, soilhum=0, soilph=0, airtemp=0, airhum=0, co2=0, illum=0;
		Integer soiltempnum=0, soilhumnum=0, soilphnum=0, airtempnum=0, airhumnum=0, co2num=0, illumnum=0;
		for (int i = 0; i < Const.SENSOR_SUM; i++) {
			if (sensors.substring(i, i+1).equals("1")) {
				String s = "select * from sensor where sensorid=? and mac=?";
				String[] ss = new String[]{(i+1) + "", Launcher.selectMac};
				Cursor cursor = db.rawQuery(s, ss);
				while(cursor.moveToNext()) {
					if (cursor.getInt(cursor.getColumnIndex("soiltemp"))!=0){
						soiltemp += cursor.getInt(cursor.getColumnIndex("soiltemp"));
						soiltempnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("soilhum"))!=0){
						soilhum += cursor.getInt(cursor.getColumnIndex("soilhum"));
						soilhumnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("soilph"))!=0){
						soilph += cursor.getInt(cursor.getColumnIndex("soilph"));
						soilphnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("airtemp"))!=0){
						airtemp += cursor.getInt(cursor.getColumnIndex("airtemp"));
						airtempnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("airhum"))!=0){
						airhum += cursor.getInt(cursor.getColumnIndex("airhum"));
						airhumnum++;
					}
					if (cursor.getInt(cursor.getColumnIndex("co2"))!=0){
						co2 += cursor.getInt(cursor.getColumnIndex("co2"));
						co2num++;
					}
					if (cursor.getInt(cursor.getColumnIndex("illum"))!=0){
						illum += cursor.getInt(cursor.getColumnIndex("illum"));
						illumnum++;
					}
				}
				cursor.close();
			}
		}
		
		if (soiltempnum!=0) {
			soiltemp = soiltemp/soiltempnum;
		}
		if (soilhumnum!=0) {
			soilhum = soilhum/soilhumnum;
		}
		if (soilphnum!=0) {
			soilph = soilph/soilphnum;
		}
		if (airtempnum!=0) {
			airtemp = airtemp/airtempnum;
		}
		if (airhumnum!=0) {
			airhum = airhum/airhumnum;
		}
		if (co2num!=0) {
			co2 = co2/co2num;
		}
		if (illumnum!=0) {
			illum = illum/illumnum;
		}
		
		sensorAverage.setSoiltemp(soiltemp);
		sensorAverage.setSoilhum(soilhum);
		sensorAverage.setSoilph(soilph);				
		sensorAverage.setAirtemp(airtemp);
		sensorAverage.setAirhum(airhum);
		sensorAverage.setCo2(co2);
		sensorAverage.setIllumination(illum);
		db.close();
		return sensorAverage;
	}
	
	
	public List<Sensor> getAllSensor() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from sensor where mac=?";
		String[] formatid = new String[]{Launcher.selectMac};
		Cursor cursor = db.rawQuery(s, formatid);
		while(cursor.moveToNext()) {
			Sensor sensor = new Sensor();
			sensor.setId(cursor.getInt(cursor.getColumnIndex("sensorid")));
			sensor.setOnline(cursor.getInt(cursor.getColumnIndex("online")));
			sensor.setSoiltemp(cursor.getInt(cursor.getColumnIndex("soiltemp")));
			sensor.setSoilhum(cursor.getInt(cursor.getColumnIndex("soilhum")));
			sensor.setSoilph(cursor.getInt(cursor.getColumnIndex("soilph")));				
			sensor.setAirtemp(cursor.getInt(cursor.getColumnIndex("airtemp")));
			sensor.setAirhum(cursor.getInt(cursor.getColumnIndex("airhum")));
			sensor.setCo2(cursor.getInt(cursor.getColumnIndex("co2")));
			sensor.setIllumination(cursor.getInt(cursor.getColumnIndex("illum")));	
			sensors.add(sensor);
		}
		cursor.close();
		db.close();
		return sensors;
	}
	
	public List<Sensor> getOnlineSensor() {//�������д���������
		List<Sensor> sensors = new ArrayList<Sensor>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from sensor where online=? and mac=?";
		String[] formatid = new String[]{1+"", Launcher.selectMac};
		Cursor cursor = db.rawQuery(s, formatid);
		while(cursor.moveToNext()) {
			Sensor sensor = new Sensor();
			sensor.setId(cursor.getInt(cursor.getColumnIndex("sensorid")));
			sensor.setSoiltemp(cursor.getInt(cursor.getColumnIndex("soiltemp")));
			sensor.setSoilhum(cursor.getInt(cursor.getColumnIndex("soilhum")));
			sensor.setSoilph(cursor.getInt(cursor.getColumnIndex("soilph")));				
			sensor.setAirtemp(cursor.getInt(cursor.getColumnIndex("airtemp")));
			sensor.setAirhum(cursor.getInt(cursor.getColumnIndex("airhum")));
			sensor.setCo2(cursor.getInt(cursor.getColumnIndex("co2")));
			sensor.setIllumination(cursor.getInt(cursor.getColumnIndex("illum")));	
			sensors.add(sensor);
		}
		cursor.close();
		db.close();
		return sensors;
	}

	public void modifySensorCurrentValue(Integer sensorid, int online, int soiltemp, int soilhum, int soilph, int airtemp, int airhum, int co2, int illum){
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update sensor set online=?, soiltemp=?, soilhum=?, soilph=?, airtemp=?, airhum=?, co2=?, illum=? where sensorid=? and mac=?";
		Object[] object = new Object[]{online, soiltemp, soilhum, soilph, airtemp, airhum, co2, illum, sensorid, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}
	
	public void modifySensorOnline(Integer sensorid, int online) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update sensor set online=? where sensorid=? and mac=?";
		Object[] object = new Object[]{online, sensorid, Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}
	
	public void modifyAllSensorOffline() {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "update sensor set online=?, soiltemp=?, soilhum=?, soilph=?, airtemp=?, airhum=?, co2=?, illum=? where mac=?";
		Object[] object = new Object[]{0,0,0,0,0,0,0,0,Launcher.selectMac};
		db.execSQL(s, object);
		db.close();
	}
	
	public int querySensorOnline (Integer sensorid) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select online from sensor where sensorid=? and mac=?";
		String[] ss = new String[]{sensorid+"", Launcher.selectMac};
		int online=0;
		Cursor cursor = db.rawQuery(s, ss);
		if(cursor.moveToNext()) {
			online=cursor.getInt(cursor.getColumnIndex("online"));
			/*int online = cursor.getInt(cursor.getColumnIndex("online"));
			cursor.close();
			return online;*/
		}
			cursor.close();
			db.close();
			return online;
	}
	
	/**
	 *
	 * @return sensors 返回传感器id和是否在线列表
	 */
	public List<Sensor> getAllOnlineSensor() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String s = "select * from sensor where online=? and mac=?";
		String[] formatid = new String[]{1+"", Launcher.selectMac};
		Cursor cursor = db.rawQuery(s, formatid);
		while(cursor.moveToNext()) {
			Sensor sensor = new Sensor();
			sensor.setId(cursor.getInt(cursor.getColumnIndex("sensorid")));
			sensor.setOnline(cursor.getInt(cursor.getColumnIndex("online")));
			sensors.add(sensor);
		}
		cursor.close();
		db.close();
		return sensors;
	}
	
	public long getCount() {
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		String s = "select count(*) from sensor where online=? and mac=?";
		String[] ss = new String[]{1+"", Launcher.selectMac};
		Cursor cursor = db.rawQuery(s, ss);
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		cursor.close();
		db.close();
		return count;
	}
}
