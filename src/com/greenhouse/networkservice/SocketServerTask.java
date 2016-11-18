package com.greenhouse.networkservice;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import com.greenhouse.database.SensorService;
import com.greenhouse.model.Sensor;
import com.greenhouse.model.SocketServer;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;
import android.util.Log;

/** 
* @author    	Elsa 
* @Email     	elsarong715@gmail.com
* @date      	2015/12/16 PM7:42:44 
* @version      1.0  
* @description  
*/

public class SocketServerTask implements Runnable {
	
	private static final String TAG = "SocketServerTask.java";
	
	private DataOutputStream outputStream = null;
	
	int msgNum = 0;
	
	private SocketServer tvServer;
	
	private SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
	
	private List<Sensor> sensors;
	
	private String id, soiltemp, soilhum, soilph, airtemp, airhum, co2, illum, tvMsg1, tvMsg2, tvMsg3, tvMsg4;
	
	public SocketServerTask(SocketServer server) {
		this.tvServer = server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		ThreadPoolManager.SERVER_IsRUNNING = true;
		
		Log.d(TAG, "[Server-Run]");
		
		while (tvServer.getSocketServer() != null && tvServer.getServerState() == Const.SOCKET_CONNECTED) {
			
			try {
				outputStream = new DataOutputStream(tvServer.getSocketServer().getOutputStream());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			AssembleSendMessage();
			
			// 如果client关闭了，server端怎么检测？问服务器怎么处理
				
				BufferedWriter writer;
				writer = new BufferedWriter(new OutputStreamWriter(outputStream));
				
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					writer.write(tvMsg1);
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					tvServer.setServerState(Const.SOCKET_DISCONNECTED);
					e.printStackTrace();
				} 
				
				Log.d(TAG, "[Server:Send]" + tvMsg1);	
				
//				try {
//					Thread.sleep(6000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//				try {
//					writer.write(tvMsg2);
//					writer.flush();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					tvServer.setServerState(Const.SOCKET_DISCONNECTED);
//					e.printStackTrace();
//				}
//				
//				Log.d(TAG, "[Server:Send]" + tvMsg2);	
//				
//				
//				try {
//					Thread.sleep(6000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//				try {
//					writer.write(tvMsg3);
//					writer.flush();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					tvServer.setServerState(Const.SOCKET_DISCONNECTED);
//					e.printStackTrace();
//				} 
//				
//				Log.d(TAG, "[Server:Send]" + tvMsg3);
				

//				try {
//					Thread.sleep(6000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//				try {
//					writer.write(tvMsg4);
//					writer.flush();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					tvServer.setServerState(Const.SOCKET_DISCONNECTED);
//					e.printStackTrace();
//				} 
				
					
					
		}
		
		Log.d(TAG, "[Server-End]");
			
	}



	
	public void AssembleSendMessage() {
		
		tvMsg1 = "";
//		tvMsg2 = "";
//		tvMsg3 = "";
//		tvMsg4 = "";
		
		
		sensors = sensorService.getAllSensor();// 从数据库中读取传感器值 3s／次
		
		if (sensors.size() == 0) {
			tvMsg1 = "HFUT00000000000000000000000000000000WANGHFUT00000000000000000000000000000000WANGHFUT00000000000000000000000000000000WANG";
		} else if (sensors.size() == 1) {
			Sensor sensor = sensors.get(0);
			id = ModifyDataFormat(sensor.getId().toString());
			soiltemp = ModifyDataFormat(sensor.getSoiltemp().toString());
			soilhum = ModifyDataFormat(sensor.getSoilhum().toString());
			soilph = ModifyDataFormat(sensor.getSoilph().toString());
			airtemp = ModifyDataFormat(sensor.getAirtemp().toString());
			airhum = ModifyDataFormat(sensor.getAirhum().toString());
			co2 = ModifyDataFormat(sensor.getCo2().toString());
			illum = ModifyDataFormat(sensor.getIllumination().toString());
			tvMsg1 = "HFUT" + id + soiltemp + soilhum + soilph + airtemp + airhum + co2 + illum + "WANGHFUT00000000000000000000000000000000WANGHFUT00000000000000000000000000000000WANG";
		} else if (sensors.size() == 2) {
			
			for (int i = 0; i < 2; i++) {
				Sensor sensor = sensors.get(i);
				id = ModifyDataFormat(sensor.getId().toString());
				soiltemp = ModifyDataFormat(sensor.getSoiltemp().toString());
				soilhum = ModifyDataFormat(sensor.getSoilhum().toString());
				soilph = ModifyDataFormat(sensor.getSoilph().toString());
				airtemp = ModifyDataFormat(sensor.getAirtemp().toString());
				airhum = ModifyDataFormat(sensor.getAirhum().toString());
				co2 = ModifyDataFormat(sensor.getCo2().toString());
				illum = ModifyDataFormat(sensor.getIllumination().toString());
				tvMsg1 += "HFUT" + id + soiltemp + soilhum + soilph + airtemp + airhum + co2 + illum + "WANG";
			}
			tvMsg1 += "HFUT00000000000000000000000000000000WANG";
			
		} else if (sensors.size() == 3) {
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
				tvMsg1 += "HFUT" + id + soiltemp + soilhum + soilph + airtemp + airhum + co2 + illum + "WANG";
			}
		}
		
		
//		for (int i = 6; i < 9; i++) {
//			Sensor sensor = sensors.get(i);
//			id = ModifyDataFormat(sensor.getId().toString());
//			soiltemp = ModifyDataFormat(sensor.getSoiltemp().toString());
//			soilhum = ModifyDataFormat(sensor.getSoilhum().toString());
//			soilph = ModifyDataFormat(sensor.getSoilph().toString());
//			airtemp = ModifyDataFormat(sensor.getAirtemp().toString());
//			airhum = ModifyDataFormat(sensor.getAirhum().toString());
//			co2 = ModifyDataFormat(sensor.getCo2().toString());
//			illum = ModifyDataFormat(sensor.getIllumination().toString());
//			tvMsg3 += "HFUT" + id + soiltemp + soilhum + soilph + airtemp + airhum + co2 + illum + "WANG";
//		}
//		
//		Sensor sensor = sensors.get(9);
//		id = ModifyDataFormat(sensor.getId().toString());
//		soiltemp = ModifyDataFormat(sensor.getSoiltemp().toString());
//		soilhum = ModifyDataFormat(sensor.getSoilhum().toString());
//		soilph = ModifyDataFormat(sensor.getSoilph().toString());
//		airtemp = ModifyDataFormat(sensor.getAirtemp().toString());
//		airhum = ModifyDataFormat(sensor.getAirhum().toString());
//		co2 = ModifyDataFormat(sensor.getCo2().toString());
//		illum = ModifyDataFormat(sensor.getIllumination().toString());
//		tvMsg4 = "HFUT" + id + soiltemp + soilhum + soilph + airtemp + airhum + co2 + illum + "WANG";
	}
	
	
	
	private String ModifyDataFormat(String s) {
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
		return s;
	}
	
	
	

}

