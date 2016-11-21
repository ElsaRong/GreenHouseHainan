package com.greenhouse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016��2��21������9:17:17 
* @version		1.0  
* @description	GreenHouse���ݿ⽨�����������ű�	CONTROLLER\JACK\SENSOR	 
*/
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "greenhouse";
	private static final int DATABASE_VERSION =1;
	
	private static final String DATABASE_CREATE_CONTROLLER = "create table controller ("
			+ "_id integer primary key autoincrement, "//�����ֶ�
			+ "mac varchar(20), "
			+ "ip varchar(20), "
			+ "name varchar(20), "
			+ "connected integer,"
			+ "position varchar(20),"
			+ "day_threshold integer,"
			+ "night_threshold integer,"
			+ "thre1 integer," //土壤温度
			+ "thre2 integer," //土壤湿度
			+ "thre3 integer," //土壤PH
			+ "thre4 integer," //空气温度
			+ "thre5 integer," //空气湿度
			+ "thre6 integer," //co2
			+ "thre7 integer);"; //光照度

	private static final String DATABASE_CREATE_JACK = "create table jack ("
			+ "_id integer primary key autoincrement, "
			+ "mac varchar(20), "
			+ "jackid integer, "
			+ "name varchar(10), "
			+ "drawable varchar(20), "
			+ "switch_state integer, "
			+ "bund integer, "
			+ "sensors varchar(20), "
			
			+ "bundtype1 integer, "
			+ "current_value1 integer, "
			+ "day_threshold1 integer, "
			+ "night_threshold1 integer, "
			
			+ "bundtype2 integer, "
			+ "current_value2 integer, "
			+ "day_threshold2 integer, "
			+ "night_threshold2 integer, "
			
			+ "bundtype3 integer, "
			+ "current_value3 integer, "
			+ "day_threshold3 integer, "
			+ "night_threshold3 integer, "
			
			+ "bundtype4 integer, "
			+ "current_value4 integer, "
			+ "day_threshold4 integer, "
			+ "night_threshold4 integer, "
			
			+ "bundtype5 integer, "
			+ "current_value5 integer, "
			+ "day_threshold5 integer, "
			+ "night_threshold5 integer, "
			
			+ "bundtype6 integer, "
			+ "current_value6 integer, "
			+ "day_threshold6 integer, "
			+ "night_threshold6 integer, "
			
			+ "bundtype7 integer, "
			+ "current_value7 integer, "
			+ "day_threshold7 integer, "
			+ "night_threshold7 integer, "
			
			+ "start varchar, "
			+ "poweron varchar, "
			+ "poweroff varchar, "
			+ "cycle integer, "
			+ "stop varchar);";

	private static final String DATABASE_CREATE_SENSOR = "create table sensor ("
			+ "_id integer primary key autoincrement, "
			+ "mac varchar(20), "
			+ "sensorid integer, "
			+ "online integer, "
			+ "soiltemp integer, "
			+ "soilhum integer, "
			+ "soilph integer, "
			+ "airtemp integer, " 
			+ "airhum integer, " 
			+ "co2 integer, " 
			+ "illum integer); ";
	
	private static final String DATABASE_CREATE_STATISTIC = "create table statistic ("
			+ "_id integer primary key autoincrement, "
			+ "mac varchar(20), "
			+ "year integer, "
			+ "month integer, "
			+ "day integer, "
			+ "hour integer, "
			+ "minute integer, "
			+ "soiltemp integer, "
			+ "soilhum integer, "
			+ "soilph integer, "
			+ "airtemp integer, " 
			+ "airhum integer, " 
			+ "co2 integer, " 
			+ "illum integer); ";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);//����
	}

	@Override
	public void onCreate(SQLiteDatabase sqldb) {
		sqldb.execSQL(DATABASE_CREATE_JACK);//ִ��SQL��䣬������ռλ���� SQL���
		sqldb.execSQL(DATABASE_CREATE_CONTROLLER);
		sqldb.execSQL(DATABASE_CREATE_SENSOR);
		sqldb.execSQL(DATABASE_CREATE_STATISTIC);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqldb, int oldVersion, int newVersion) {//���°汾�ã����İ汾��
		sqldb.execSQL("DROP TABLE IF EXISTS controller");
		sqldb.execSQL("DROP TABLE IF EXISTS jack");
		sqldb.execSQL("DROP TABLE IF EXISTS sensor");
		sqldb.execSQL("DROP TABLE IF EXISTS statistic");
		onCreate(sqldb);
	}
}
