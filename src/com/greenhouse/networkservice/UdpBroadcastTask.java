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
* Task <code>UDPBroadcastTask</code> 利用smartlink的wifi模块特有功能，
* 发送指定内容的广播，返回当前局域网下所有“汉风－smartlink”模块的MAC和IP
* 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015年11月12日 下午6:45:49 
* @version      1.0  
*/
public class UdpBroadcastTask implements Runnable{
	
	private static final String TAG = "UDPBroadcastTask";
	private static final String BROADCAST_MSG = "HF-A11ASSISTHREAD";
	private String[] ipRetrieval = null;
	
	DatagramSocket sendSocket;
	DatagramPacket sendPacket;	

	private InetAddress getBroadcastAddress() {
		InetAddress broadcastAddress = null;
		if (NetworkManager.getLocalIpAddress() != null) {
			String[] localIP = NetworkManager.getLocalIpAddress().split("\\.");
			// 在Tenda1964网络异常状态下，本地ip可能随时为null，需要在UDP中返回null,UDP广播能否广播null的地址待定
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
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		InetAddress broadcastAddress = getBroadcastAddress();
		
		try {
			sendSocket = new DatagramSocket();						
		} catch (SocketException se) {
			// TODO Auto-generated catch block
			se.printStackTrace();
		}
		
		byte[] sendBuf = BROADCAST_MSG.getBytes();
		sendPacket = new DatagramPacket(sendBuf, sendBuf.length, broadcastAddress, 48899);		    	
    
		// 两次读取的超时时间间隔
		// 想知道receive到底是一个包还是两个包同时从流里发过来然后接收的时候放在了arraylist中
	    // 还是两个包发过来，对流读两次才能读到两个sendpacket的数据
		// 不知是哪个位置报了超时异常的warn，总之我是不像看到黄橙橙一片的！
		try {
			sendSocket.send(sendPacket);	
			sendSocket.setSoTimeout(50);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}		
				
							    
		byte[] receiveBuf = new byte[64];						
		int i = 0;
		ArrayList<String> arrayList=new ArrayList<String>();
		
		while(i<50) {
			i++;						
			DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length); 				
			try {							
				sendSocket.receive(receivePacket);
			}catch(SocketException e){  
	        }catch (Exception e) {  
	        } 
			
			try {
				String str=new String(receivePacket.getData(),"ASCII");
				arrayList.add(str);
			} catch (UnsupportedEncodingException uee) {
				// TODO Auto-generated catch block
				uee.printStackTrace();
			}						
			Arrays.fill(receiveBuf, (byte)0);				
		}	
				
		ipRetrieval = IPRetrieval(arrayList);
		if(ipRetrieval != null) {
			// 将ip保存到数据库，不仅仅是Launcher.selectController
			Launcher.selectIp = ipRetrieval[0].toString();
			ControllerService controllerService = new ControllerService(GreenHouseApplication.getContext());
			controllerService.modifyIp(Launcher.selectIp, Launcher.selectMac);
			Log.d(TAG,"UDP received IP - "+ipRetrieval[0].toString());
		}
		else {
			Launcher.selectIp = null;
			Log.d(TAG,"UDP FAILED");
		}
	}
	
	
	
	private String[] IPRetrieval(ArrayList<String> array) {
		String[] ipRetrieval = null;
		String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}+[,]"+ Launcher.selectMac;
		Pattern p = Pattern.compile(regex);
		for(int i = 0; i < array.size(); i++) {
			String arrayBuf = array.get(i);
			Matcher m = p.matcher(arrayBuf);
			boolean result = m.find();
			while(result) {
				ipRetrieval = array.get(i).toString().split(",");
				result = m.find();
				break;
			}
		}
		return ipRetrieval;
	}

}


