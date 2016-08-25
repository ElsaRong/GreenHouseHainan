package com.greenhouse.networkservice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.greenhouse.database.ControllerService;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.ToastUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016��3��8������6:56:24 
* @version		1.0  
* @description			 
*/
public class AsyncNetChangedSocketReq extends AsyncTask<String, Void, String>{

	private static final String TAG = "AsyncNetChangedSocketReq";

	private Handler mainHandler;
	
	public AsyncNetChangedSocketReq(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		Launcher.client.destroy();//����֮ǰdestroy��socket���ܻᵼ��null pointer exception
		
		if (Launcher.client.getState() != Const.SOCKET_CONNECTING) {//heartbeat/input���쳣ʱ�ĳ�ͻ����
			
			Launcher.client.setState(Const.SOCKET_CONNECTING);
			
			ControllerService controllerService = new ControllerService(GreenHouseApplication.getContext());
			Launcher.selectIp = controllerService.queryIp(Launcher.selectMac);
			
			if (NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_MOBILE) {
				Log.d(TAG, "NETWORK_MOBILE");
				Launcher.client.setSocket(RequestServer("112.74.115.147", 5000));
			} else if (NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_WIFI) {
				Log.d(TAG, "NETWORK_WIFI");
				Launcher.client.setSocket(RequestServer(Launcher.selectIp, Const.client_PORT));//����һ��
			} else if (NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_NONE) {
				Log.d(TAG, "NETWORK_NONE");
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (Launcher.client.getSocket() != null && Launcher.client.getSocket().isConnected() && !Launcher.client.getSocket().isClosed()) {
				Log.d(TAG, "[�����ɹ�,��ʼ��ʱ]");//����������receive����
				Launcher.client.setState(Const.SOCKET_CONNECTED);
				ThreadPoolManager.getInstance().startSocketClientAgain();
				ThreadPoolManager.getInstance().startSocketServerAccept();
				SocketOutputTask.getHandler().sendEmptyMessageDelayed(Const.TIME, 500);
			} else {
				Log.d(TAG, "[����ʧ�ܣ���ת��������]");
				Launcher.client.setState(Const.SOCKET_DISCONNECTED);
				mainHandler.sendEmptyMessage(Const.BACK_TO_LAUNCHER);//Ҫ��ת�����߳�handler��mainHandler���㲥���ù㲥��ע�����actiivty���ղ���ת
			}
		}
		
		return null;
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
