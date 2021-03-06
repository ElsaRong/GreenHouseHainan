package com.greenhouse.networkservice;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import com.greenhouse.database.SensorService;
import com.greenhouse.model.Sensor;
import com.greenhouse.model.SocketServer;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;

import android.telecom.TelecomManager;
import android.util.Log;

/** 
* @author    	Elsa 
* @Email     	elsarong715@gmail.com
* @date      	2015/12/16 PM7:42:44 
* @version      1.0  
* @description  
*/

public class SocketServerSendTask implements Runnable {
	
	private static final String TAG = "SocketServerTask.java";
	
	private DataOutputStream outputStream = null;
	
	int msgNum = 0;
	
	private SocketServer socket_server;
	
	private SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
	
	private List<Sensor> sensors = new ArrayList<Sensor>();
	
	private String id, soiltemp, soilhum, soilph, airtemp, airhum, co2, illum, tvMsg="";
	private String initMsg = "HFUT00000000000000000000000000000000WANG";
	
	public SocketServerSendTask(SocketServer socket_server) {
		this.socket_server = socket_server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Log.d(TAG, "Server Send Run");
		
		while (socket_server.getSocketServer() != null && socket_server.getServerState() == Const.SOCKET_CONNECTED) {
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				outputStream = new DataOutputStream(socket_server.getSocketServer().getOutputStream());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			BufferedWriter writer;
			writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			AssembleSendMessage();
			try {
				writer.write(tvMsg);
				writer.flush();
				Log.v(TAG, "[Server:Send]" + tvMsg);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				socket_server.setServerState(Const.SOCKET_DISCONNECTED);
				socket_server.destroy();
				e.printStackTrace();
			} 
			
					
		}
		
		Log.d(TAG, "Server Send End");
			
	}


	private void assembleMsgFour() {
		for (int i=0; i<4; i++) {
			
		}
	}
	
	public void AssembleSendMessage() {
		
		tvMsg = "";
		sensors.clear();
		sensors = new ArrayList<Sensor>();
		sensors = sensorService.getAllSensor();
		
//		sensors = sensorService.getOnlineSensor();
//		Log.d(TAG, "当前传感器在线个数＝" + sensors.size());
		
		if (Launcher.selectMac.equals("ACCF2357F44E")) {
//			txtController.setText("二号棚");
			for (int i = 0; i < 3; i++) {
				Sensor sensor = sensors.get(i);
				id = ModifyDataFormat(sensor.getId().toString());
				soiltemp = ModifyDataFormat(sensor.getSoiltemp().toString());
				soilhum = ModifyDataFormat(sensor.getSoilhum().toString());
				soilph = ModifyDataFormat(sensor.getSoilph().toString());
				airtemp = ModifyDataFormat(sensor.getAirtemp().toString());
				airhum = ModifyDataFormat(sensor.getAirhum().toString());
				co2 = ModifyDataFormat(sensor.getCo2().toString());
				illum = ModifyDataFormat(sensor.getIllumination().toString());
				tvMsg += "HFUT" + id + soiltemp + soilhum + soilph + airtemp + airhum + co2 + illum + "WANG";
			}
		} else if (Launcher.selectMac.equals("ACCF236FA948")) {
//			txtController.setText("一号棚");
			for (int i = 0; i < 4; i++) {
				Sensor sensor = sensors.get(i);
				id = ModifyDataFormat(sensor.getId().toString());
				soiltemp = ModifyDataFormat(sensor.getSoiltemp().toString());
				soilhum = ModifyDataFormat(sensor.getSoilhum().toString());
				soilph = ModifyDataFormat(sensor.getSoilph().toString());
				airtemp = ModifyDataFormat(sensor.getAirtemp().toString());
				airhum = ModifyDataFormat(sensor.getAirhum().toString());
				co2 = ModifyDataFormat(sensor.getCo2().toString());
				illum = ModifyDataFormat(sensor.getIllumination().toString());
				tvMsg += "HFUT" + id + soiltemp + soilhum + soilph + airtemp + airhum + co2 + illum + "WANG";
			}
		}
			
	}
	
	
	
	private String ModifyDataFormat(String s) {
		if (s == null || s.equals("")) {
			s = "0000";
		} else {
			int len = s.length();
			switch (len) {
			case 1:
				s = "000" + s;
				break;
			case 2:
				s = "00" + s;
				break;
			case 3:
				s = "0" + s;
				break;
			case 4:
				break;
			}
		}
		return s;
	}
	
	
	

}

