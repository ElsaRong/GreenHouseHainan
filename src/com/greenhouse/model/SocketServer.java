package com.greenhouse.model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016-2-23 2:26:31 
* @version		1.0  
* @description			 
*/
public class SocketServer {

	private Socket tvServer;
	
	private int state;
	
	private OutputStream outputStream;
	
	public SocketServer() {}
	
	public void setSocketServer(Socket server) {
		this.tvServer = server;
	}
	
	public Socket getSocketServer() {
		return tvServer;
	}
	
	
	public void setServerState(int state) {
		this.state = state;
	}
	
	public int getServerState() {
		return state;
	}
	
	public void destroy() {
		if (tvServer != null) {
			try {
				tvServer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tvServer = null;
	}
	
	
}
