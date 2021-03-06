package com.greenhouse.networkservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.greenhouse.model.SocketServer;
import com.greenhouse.ui.Launcher;
import com.greenhouse.util.Const;

import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016/3/8 PM 8:09:37 
* @version		1.0  
* @description			 
*/
public class SocketServerAccept implements Runnable {
	
	private static final String TAG = "SocketServerAccept";
	
	private Socket socket;
	
	private ThreadPoolManager threadPoolManager;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		ThreadPoolManager.ACCEPT_IsRUNNING = true;
		
		while (ThreadPoolManager.ACCEPT_IsRUNNING) {
			try {
				Launcher.server = new ServerSocket(Const.server_PORT);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Log.d(TAG, "Accept Task Run");
			try {
				socket = Launcher.server.accept();
				Log.d(TAG, "accepted");
				if(socket.isConnected() && !socket.isClosed()) {
					SocketServer tvServer = new SocketServer();
					tvServer.setSocketServer(socket);
					tvServer.setServerState(Const.SOCKET_CONNECTED);
					threadPoolManager = ThreadPoolManager.getInstance();
					threadPoolManager.startServerSendTask(tvServer);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d(TAG, "Server is exception");
				e.printStackTrace();
			}
			
			try {
				Launcher.server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				Launcher.server = null;
			}
		}
		
		ThreadPoolManager.ACCEPT_IsRUNNING = false;
	}

}
