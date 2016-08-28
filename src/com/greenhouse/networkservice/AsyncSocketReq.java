package com.greenhouse.networkservice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016/2/27 PM 3:20:11 
* @version		1.0  
* @description			 
*/
public class AsyncSocketReq extends AsyncTask<String, Void, String>{
	
	private static final String TAG = "AsyncSocketReq";

	private Handler mainHandler;
	
	public AsyncSocketReq(Handler handler) {
		this.mainHandler = handler;
	}

	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		mainHandler.sendEmptyMessage(Const.SOCKET_CONNECTING);
		
		if(NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_MOBILE) {
			Log.i(TAG, "NETWORK_MOBILE");
			Launcher.client.setSocket(RequestServer("112.74.115.147", 5000));
			initSocketConfig2(1, mainHandler);
		} else if(NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_WIFI) {
			Log.i(TAG, "NETWORK_WIFI");
			
			Log.i(TAG, "[Connect 1] request controller by local ip address");
			Launcher.client.setSocket(RequestServer(Launcher.selectIp, Const.client_PORT));
			initSocketConfig1(1, mainHandler);
			
			if (Launcher.client.getState() == Const.SOCKET_DISCONNECTED) {
				
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
					Log.i(TAG, "[Connect 2] request controller by UDP");
					Launcher.client.setSocket(RequestServer(Launcher.selectIp, Const.client_PORT));//����һ��
					initSocketConfig2(1, mainHandler);
				} else {
					Log.i(TAG, "[Connect 3] request server");
//				    Launcher.client.setSocket(RequestServer("198.11.169.151", 4000));�Ϸ�����
					Launcher.client.setSocket(RequestServer("112.74.115.147", 5000));
					initSocketConfig2(0, mainHandler);
				}
				
			}
			
			
		} else {
			Log.d(TAG, "NETWORK_NONE");
			mainHandler.sendEmptyMessage(Const.SOCKET_DISCONNECTED);
		}	
		
		return null;
	}
	
	private static void initSocketConfig1(int terminalType, Handler mainHandler) {
		if(Launcher.client.getSocket() != null && Launcher.client.getSocket().isConnected() && !Launcher.client.getSocket().isClosed()) {
			Launcher.client.setState(Const.SOCKET_CONNECTED);
			Launcher.client.setTerminalType(terminalType);
			mainHandler.sendEmptyMessage(Const.SOCKET_CONNECTED);
		} else {
			Launcher.client.setState(Const.SOCKET_DISCONNECTED);
//			mainHandler.sendEmptyMessage(Const.SOCKET_DISCONNECTED);
		}
	}
	
	private static void initSocketConfig2(int terminalType, Handler mainHandler) {
		if(Launcher.client.getSocket() != null && Launcher.client.getSocket().isConnected() && !Launcher.client.getSocket().isClosed()) {
			Launcher.client.setState(Const.SOCKET_CONNECTED);
			Launcher.client.setTerminalType(terminalType);
			mainHandler.sendEmptyMessage(Const.SOCKET_CONNECTED);
		} else {
			Launcher.client.setState(Const.SOCKET_DISCONNECTED);
//			mainHandler.sendEmptyMessage(Const.SOCKET_DISCONNECTED);
		}
	}
	
	private static Socket RequestServer(String ip, int host) {	
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ip,host);
		Log.d(TAG, "RequestServer(ip, host) : [" + ip + ":" + host + "]");
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
