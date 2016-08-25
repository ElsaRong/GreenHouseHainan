package com.greenhouse.networkservice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.greenhouse.model.SensorValueHistory;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.MessageHandleService;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/** 
* class <code>SocketHeartbeatThread</code> 是Socket心跳线程的具体实现。
*
* FLAG1:从extends thread改成了implements runnable，将线程管理控制同一交给线程池 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015年11月8日 下午4:21:32 
* @version      1.0  
*/
public class SocketHeartbeatTask implements Runnable{
	
	private static final String TAG = "SocketHeartbeatThread.java";
	
	private static Handler heartbeatHandler;
	
	private BufferedWriter writer;
	
	private Handler mainHandler;
	
	private OutputStream outputStream;
	
	public SocketHeartbeatTask(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}
	
	public static Handler getHandler() {
		return heartbeatHandler;
	}
	
	
	public void run() {	
		
		Log.d(TAG, "[Heartbeat-Run]");
		ThreadPoolManager.HEARTBEAT_IsRUNNING = true;
		Looper.prepare();
		heartbeatHandler = new Handler() {
			public void handleMessage (Message msg) {
				switch (msg.what) {
				case Const.HEARTBEAT:
					if (Launcher.client.getSocket() != null && Launcher.client.getState() == Const.SOCKET_CONNECTED) {
						try {
							outputStream = Launcher.client.getSocket().getOutputStream();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							Launcher.client.setState(Const.SOCKET_DISCONNECTED);
						}
						if (outputStream != null) {
							String heartbeat = "HFUT" + Launcher.selectMac + "HEARTBEAT00000000000WANG";
							writer = new BufferedWriter(new OutputStreamWriter(outputStream));
							try {
								writer.write(heartbeat.replace("", ""));
								writer.flush();
								Log.d(TAG, heartbeat);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Launcher.client.setState(Const.SOCKET_DISCONNECTED);
								
								if (!Launcher.BACK_TO_LAUNCHER && Launcher.client.getRunningState()) { // 第二界面进行跳转,不对socket进行null处理，以免其它线程空指针
									new AsyncNetChangedSocketReq(mainHandler).execute();
								}
								
							}
						}
						
						heartbeatHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Message msg = heartbeatHandler.obtainMessage();
								msg.what = Const.HEARTBEAT;
								heartbeatHandler.sendMessage(msg);
							}
						}, 120000);
						
					}
					
				}
				
			}
		};
			
		Looper.myQueue();
			
		Looper.loop();
			
		ThreadPoolManager.HEARTBEAT_IsRUNNING = false;
		Log.d(TAG, "[Heartbeat-End]");
	}
	
}
	


