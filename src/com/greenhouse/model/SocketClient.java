package com.greenhouse.model;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import com.greenhouse.networkservice.SocketOutputTask;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016��2��20������3:38:48 
* @version		1.0  
* @description	socket�Ժ����ع�		 
*/
public class SocketClient {

	private Socket client;
	
	private String ip;
	
	private String mac;
	
	private int state;
	
	private boolean runningState;
	
	private int terminalType;//0-corntroller 1-alibaba server
	
	public SocketClient() {	}
	
	public void setSocket(Socket client) {
		if (client != null) {
			this.client = client;
			
			try {
				client.setTcpNoDelay(true);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			this.client = null;
		}
	}
	
	public Socket getSocket() {
		return client;
	}
	
	/**
	 * @Title:       destroy
	 * @description: TODO 销毁本次连接建立的client,包括SocketOutputTask/SocketInputTask标识位复位
	 * @param        
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 27, 2016, 2:40:06 PM
	 */
	public void destroy() {
		if (client != null) {
			
			if (!client.isClosed()) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			client = null;
		}
	}
	
	public void closeSocket() {
		if (client != null) {
			
			if (!client.isClosed()) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			client = null;
		}
	}
	
	
	
	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}
	
	public int getTerminalType() {
		return terminalType;
	}
	
	public void setRunningState(boolean b) {
		this.runningState = b;
	}
	
	public boolean getRunningState() {
		return this.runningState;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIp(){
		return ip;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getMac(){
		return mac;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
}
