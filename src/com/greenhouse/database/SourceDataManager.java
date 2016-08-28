package com.greenhouse.database;

import java.util.ArrayList;
import java.util.List;

import com.greenhouse.model.Jack;
import com.greenhouse.ui.Launcher;

import android.content.Context;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			Aug 28, 201611:08:09 AM 
* @version		1.0  
* @description			 
*/

public class SourceDataManager {
	
	public static List<Jack> initJackInfoList(Context context) {
		List<Jack> jackInfoList = new ArrayList<Jack>();
		
		JackService jackService = new JackService(context);
		jackInfoList = jackService.getAllJack(Launcher.selectMac);
		
		return jackInfoList;
	}
	
	public static List<Jack> initJackSwitchInfoList(Context context) {
		List<Jack> jackSwitchInfoList = new ArrayList<Jack>();
		
		JackService jackService = new JackService(context);
		jackSwitchInfoList = jackService.getAllJackNameAndState(Launcher.selectMac);
		
		return jackSwitchInfoList;
	}

}
