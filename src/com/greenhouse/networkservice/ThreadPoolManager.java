package com.greenhouse.networkservice;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.greenhouse.model.SocketServer;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;

import android.os.Handler;
import android.util.Log;

/** 
* class <code>SocketThreadPoolManager</code> 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/7 4:40:39 
* @version      1.0  
*/
public class ThreadPoolManager {
	
	public static boolean ACCEPT_IsRUNNING = false;
	public static boolean SERVER_IsRUNNING = false;
	public static boolean OUTPUT_IsRUNNING = false;
	public static boolean INPUT_IsRUNNING = false;
	public static boolean HEARTBEAT_IsRUNNING = false;
	
	private static final String TAG = "ThreadPoolManager.java";
	
	private static ThreadPoolManager threadPoolManager = null;
	
	private Handler mainHandler;
	
	private Handler outputHandler = null;
	
	private static ExecutorService executor;	
	
	public static synchronized ThreadPoolManager getInstance(Handler handler) {
		if(threadPoolManager == null) {
			threadPoolManager = new ThreadPoolManager(handler);
		}
		return threadPoolManager;
	}
	
	public static synchronized ThreadPoolManager getInstance() {
		return threadPoolManager;
	}
	
	ThreadPoolManager(Handler handler) {
		executor = Executors.newFixedThreadPool(10);
		this.mainHandler = handler;
	}
	
	public void releaseInstance() {
		Log.d(TAG, "[releaseInstance]");
		if(threadPoolManager != null) {
			threadPoolManager.destroyThread();
			threadPoolManager = null;
		}
	}
	
	private void destroyThread() {
		Log.d(TAG, "[destroyThread]");
		Launcher.client.closeSocket();
		executor.isShutdown();
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (!executor.isTerminated()) {
			executor.shutdownNow();
		}
	}



	/**
	 * 思路应当是：主线程之外开启线程进行连接，在连接线程中，可以向主线程发送message
	 * 如果连接成功，开始启动线程池，否则：变量＝null；msg＝disconnected
	 */
	public void startSocketClient() {
		if(!ThreadPoolManager.OUTPUT_IsRUNNING) {
			SocketOutputTask outputTask = new SocketOutputTask(mainHandler);
			executor.execute(outputTask);	
		}
		if(!ThreadPoolManager.INPUT_IsRUNNING) {
			SocketInputTask inputTask = new SocketInputTask(mainHandler);
			executor.execute(inputTask);
		}
		if(!ThreadPoolManager.HEARTBEAT_IsRUNNING) {
			SocketHeartbeatTask heartbeatTask = new SocketHeartbeatTask(mainHandler);
			executor.execute(heartbeatTask);
		}
	}
	
	/**
	 * socket重连，socket input线程重启
	 */
	public void startSocketClientAgain() {
		SocketInputTask.getHandler().sendEmptyMessage(Const.SOCKET_CONNECTED);
		if(!ThreadPoolManager.HEARTBEAT_IsRUNNING) {
			SocketHeartbeatTask heartbeatTask = new SocketHeartbeatTask(mainHandler);
			executor.execute(heartbeatTask);
		}
	}
	
	public void startSocketServerAccept() {
		if(!ThreadPoolManager.ACCEPT_IsRUNNING) {
			executor.execute(new SocketServerAccept());
		}
	}
	
	/**
	 * @Title:       stopSocketClient
	 * @description: TODO 清空各Socket线程标识位
	 * @param        
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 27, 2016, 2:44:46 PM
	 */
	public void stopSocketClient() {
		ACCEPT_IsRUNNING = false;
		SERVER_IsRUNNING = false;
		OUTPUT_IsRUNNING = false;
		INPUT_IsRUNNING = false;
		HEARTBEAT_IsRUNNING = false;
	}
	
	public void startSocketServerTask(SocketServer socket) {
		executor.execute(new SocketServerTask(socket));
	}
	

	public void sendMsg(int what){
		if (outputHandler == null ) {
			outputHandler = SocketOutputTask.getHandler();
		} 
		if (!Launcher.client.getSocket().isOutputShutdown()) {
			outputHandler.sendEmptyMessage(what);
		} else {
			Log.e(TAG, "SOCKET ERROR:[OuputStream Shutdown]");
		}
	}



}
