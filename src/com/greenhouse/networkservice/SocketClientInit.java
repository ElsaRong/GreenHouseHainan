package com.greenhouse.networkservice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016－2－27 PM 3:20:11 
* @version		1.0  
* @description			 
*/
public class SocketClientInit{
	
	
	private static final String TAG = "SocketClientInit";
	
	public static Integer connController() {
		
		Integer ret = Integer.valueOf(-1);
		
		UdpBroadcastTask udpBroadcastTask = new UdpBroadcastTask();
		Thread UDPThread = new Thread(udpBroadcastTask);
		UDPThread.start();			
		
		try {
			UDPThread.join();
		} catch (InterruptedException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		}
		
		if (Launcher.selectIp != null) {
			Launcher.client = RequestServer(Launcher.selectIp, Const.client_PORT);
//			Launcher.client = RequestServer("192.168.1.103", 8899);
			if (Launcher.client != null) {
				ret = Integer.valueOf(0);
				return ret;
			}
		} else {
			Log.i(TAG, "UDP failed");
			ret = connAliyun();
		}
		
		return ret;

	}
	
	public static Integer connAliyun() {
		Integer ret = Integer.valueOf(-1);
		Launcher.client = RequestServer("112.74.115.147", 5000);
		if (Launcher.client != null) {
			ret = Integer.valueOf(1);
			return ret;
		}		
		return ret;
	}
	
	private static Socket RequestServer(String ip, int host) {	
		Log.d(TAG, "RequestServer(ip, host) : [" + ip + ":" + host + "]");
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, host);
		Socket socket = new Socket();
		try {
			socket.connect(inetSocketAddress, 3000);
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		if(socket.isConnected() && !socket.isClosed()) {
			return socket;
		}else{
			return null;
		}
	}



}
