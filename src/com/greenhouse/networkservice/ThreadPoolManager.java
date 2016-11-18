package com.greenhouse.networkservice;

import java.io.IOException;
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
	
	private static final String TAG = "ThreadPoolManager";
	
	private static ThreadPoolManager threadPoolManager = null;
	
	private ExecutorService executor;	

	
	public ThreadPoolManager() {
		executor = Executors.newFixedThreadPool(10);
	}
	
	public static synchronized ThreadPoolManager getInstance() {
		if (threadPoolManager == null) {
			threadPoolManager = new ThreadPoolManager();
		}
		return threadPoolManager;
	}
	
	
	public void releaseInstance() {
		Log.d(TAG, "[releaseInstance]");
		if(threadPoolManager != null) {
			threadPoolManager.destroyAllTasks();
			threadPoolManager = null;
		}
	}
	
	public void destroyClientTasks() {
		if (Launcher.client != null) {
			try {
				Launcher.client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				Launcher.client = null;
			}
		}
	}
	
	public void destroyServerTasks() {
		try {
			Launcher.server.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			Launcher.server = null;
		}
	}
	
	
	public void destroyAllTasks() {
		Log.d(TAG, "[destroyThread]");
		
		try {
			Launcher.client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			Launcher.client = null;
		}
		
		try {
			Launcher.server.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			Launcher.server = null;
		}
		
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
	public void startClientTasks() {
		SocketInputTask inputTask = new SocketInputTask();
		executor.execute(inputTask);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SocketOutputTask outputTask = new SocketOutputTask();
		executor.execute(outputTask);	
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SocketHeartbeatTask heartbeatTask = new SocketHeartbeatTask();
		executor.execute(heartbeatTask);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startServerTask() {
		executor.execute(new SocketServerAccept());
	}
	
	
	public void startSocketServerTask(SocketServer socket) {
		executor.execute(new SocketServerTask(socket));
	}
	

}
