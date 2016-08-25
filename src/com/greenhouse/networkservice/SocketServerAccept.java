package com.greenhouse.networkservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.greenhouse.model.SocketServer;
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
	
	private static final String TAG = "[new Accept Task Run]";
	
	private ServerSocket server;
	
	private Socket socket;
	
	private ThreadPoolManager manager;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		ThreadPoolManager.ACCEPT_IsRUNNING = true;
		
		Log.d(TAG, "[Accept-Run]");
		
		while(true) {
			
			try {
				server = new ServerSocket(Const.server_PORT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Log.d(TAG, "accepting...");
				socket = server.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.d(TAG, "accepted");
			
			if(socket.isConnected() && !socket.isClosed()) {
				SocketServer tvServer = new SocketServer();
				tvServer.setSocketServer(socket);
				tvServer.setServerState(Const.SOCKET_CONNECTED);
				manager = ThreadPoolManager.getInstance();
				manager.startSocketServerTask(tvServer);
			}
			
		}
		
	}

}