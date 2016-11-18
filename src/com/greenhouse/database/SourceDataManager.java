package com.greenhouse.database;

import java.util.ArrayList;
import java.util.List;

import com.greenhouse.model.Jack;
import com.greenhouse.model.Sensor;
import com.greenhouse.ui.JackFragmentEnvironment;
import com.greenhouse.ui.JackFragmentMaster;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.GreenHouseApplication;

import android.content.Context;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			Aug 28, 201611:08:09 AM 
* @version		1.0  
* @description			 
*/

public class SourceDataManager {
	
	public static List<Jack> initJackInfoList() {
		List<Jack> jackInfoList = new ArrayList<Jack>();
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackInfoList = jackService.getAllJack(Launcher.selectMac);
		return jackInfoList;
	}
	
	public static List<Jack> initJackSwitchInfoList() {
		List<Jack> jackSwitchInfoList = new ArrayList<Jack>();
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackSwitchInfoList = jackService.getAllJackNameAndState(Launcher.selectMac);
		return jackSwitchInfoList;
	}
	
	public static List<Sensor> initEnvironmentList() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
		sensors = sensorService.getAllSensor();
		return sensors;
	}

}
