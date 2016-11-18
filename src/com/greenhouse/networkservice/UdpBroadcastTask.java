package com.greenhouse.networkservice;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.greenhouse.database.ControllerService;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.GreenHouseApplication;

import android.util.Log;

/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015-11-12 PM 6:45:49 
* @version      1.0  
*/
public class UdpBroadcastTask implements Runnable{
	
	private static final String TAG = "UDPBroadcastTask";
	private static final String BROADCAST_MSG = "HF-A11ASSISTHREAD";
	private byte[] sendBuf = BROADCAST_MSG.getBytes();
	private InetAddress broadcastAddress = getBroadcastAddress();
	private byte[] receiveBuf = new byte[64];		
	private DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length); 				
	private String returnStr = null;
	
	private DatagramSocket sendSocket;
	private DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, broadcastAddress, 48899);;	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			sendSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			sendSocket.send(sendPacket);	
			sendSocket.setSoTimeout(2000);
			sendSocket.receive(receivePacket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		try {
			returnStr = new String(receivePacket.getData(),"ASCII");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (returnStr != null) {
			Launcher.selectIp = regexIP(returnStr);
		} 			
		
	}
	
	private InetAddress getBroadcastAddress() {
		InetAddress broadcastAddress = null;
		if (NetworkManager.getLocalIpAddress() != null) {
			String[] localIP = NetworkManager.getLocalIpAddress().split("\\.");
			if (localIP != null) {
				String broadcastIPAddress = localIP[0].toString()+"."+localIP[1].toString()+"."+localIP[2].toString()+"."+255;
				try {
					broadcastAddress = InetAddress.getByName(broadcastIPAddress);
				} 
				catch (UnknownHostException uhe) {
					// TODO Auto-generated catch block
					uhe.printStackTrace();
				}
				return broadcastAddress;	
			}
		}
		return null;
	}
	
	private String regexIP(String returnStr) {
		String returnIP = null;
		String regexStr = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}+[,]"+ Launcher.selectMac;
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(returnStr);
		boolean isMatch = matcher.find();
		if (isMatch) {
			String[] test = returnStr.toString().split(",");
			returnIP = test[0].toString();
		}
		return returnIP;
	}

}


