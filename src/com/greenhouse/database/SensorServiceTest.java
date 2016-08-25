package com.greenhouse.database;

import java.util.List;

import com.greenhouse.model.Sensor;

import android.test.AndroidTestCase;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016年2月22日下午1:59:47 
* @version		1.0  
* @description			 
*/
public class SensorServiceTest extends AndroidTestCase{
	
	private static final String TAG = "SensorServiceTest.java";
	
	public void testInit() throws Throwable {
		SensorService sensorService = new SensorService(this.getContext());
		sensorService.init("hfut");
	}
	
}
