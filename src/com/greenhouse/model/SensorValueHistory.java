package com.greenhouse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;

/** 
* @author   	Elsa 
* @Email    	elsarong715@gmail.com
* @date     	2015/11/1 AM11:00:38 
* @version  	1.0  
* @description  
*/
public class SensorValueHistory {
	
	private static final String TAG = "SensorValueHistory.class";
	
	private ArrayList<Map<String, Object>> mListData_Jack = new ArrayList<Map<String, Object>>();
	
	public static  int SensorArrayOffset = 0;
	public static String OnePiece = null;
	public String[] SensorArray1 = new String[30000];
	private String[] SensorArray2 = new String[30000];
	private String[] SensorArray3 = new String[30000];
	private String[] SensorArray4 = new String[30000];
	private String[] SensorArray5 = new String[30000];
	private String[] SensorArray6 = new String[30000];
	private String[] SensorArray7 = new String[30000];

	public String[] getStatistic(int sensor_type) {
		switch(sensor_type) { 
		case 1:
			return SensorArray1;
		case 2:
			return SensorArray2;
		case 3:
			return SensorArray3;
		case 4:
			return SensorArray4;
		case 5:
			return SensorArray5;
		case 6:
			return SensorArray6;
		case 7:
			return SensorArray7;
		default:
			return null;
		}
	}

	public SensorValueHistory() {
		
		for (int i = 0; i < 30000; i++) {
			SensorArray1[i] = 0+"";
			SensorArray2[i] = 0+"";
			SensorArray3[i] = 0+"";
			SensorArray4[i] = 0+"";
			SensorArray5[i] = 0+"";
			SensorArray6[i] = 0+"";
			SensorArray7[i] = 0+"";
		}
	}
	
	// 如果传递的是指针效率就比较高，不用重新开一个30000长度的数组
	public void setSensorArray(String str, int index, int sensorId) {		
		switch(sensorId) {
		case 1:
			SensorArray1[index] = str;
//			Log.d(TAG, "SensorArray1" + "[" + index + "] = " + str);
			break;
		case 2:
			SensorArray2[index] = str;
//			Log.d(TAG, "SensorArray2" + "[" + index + "] = " + str);
			break;
		case 3:
			SensorArray3[index] = str;
			break;
		case 4:
			SensorArray4[index] = str;
			break;
		case 5:
			SensorArray5[index] = str;
			break;
		case 6:
			SensorArray6[index] = str;
			break;			
		}
	}
	
	public void setSensorArray(String str, int index, int conditionId, int sensorId) {		
		if (conditionId == 1) {
			setSensorArray(str, index, sensorId);	
		} else if (conditionId == 2) {		
			setSensorArray(str, (index + SensorArrayOffset), sensorId);
		} else if (conditionId == 3) {
			setSensorArray(str, (index + SensorArrayOffset), sensorId);
		}
	}

	
	public List<Map<String, Object>> getListDataJack() {
		return mListData_Jack;
	}
	
	// 20151222 添加传感器界面的映射表
	public void setControllerSensorTable(String id, String value) {
		Map<String, Object> map= new HashMap<String, Object>();
		value.split(",");
		map.put(id, value);
	}
	
	public void setListDataJackSwitch(int position, int state) {
		mListData_Jack.get(position).put("switch_state", state);
	}
	
	public  List<Map<String, Object>> getListDataJackSwitch() {
		return mListData_Jack;
	}
	
	public void setStatistics(int sensorId, String[] data) {
		Map<String, Object> map= new HashMap<String, Object>();
		map.put(sensorId + "", data);
	}

	
	private static class ControllerInstance {
		private static final SensorValueHistory instance = new SensorValueHistory();
	}
	
	public static SensorValueHistory getInstance() {
		return ControllerInstance.instance;
	}
	

	

	
}
