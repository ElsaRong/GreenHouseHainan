package com.greenhouse.model;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016��2��20������1:37:16 
* @version		1.0  
* @description  
*/
public class Controller {
	
	private Integer controllerId;//controller 
	private String ip;
	private String mac;
	private String name;
	private boolean connected;
	private Integer day_threshold;
	private Integer night_threshold;
	

	//public Controller() {}
	
	public Controller(String mac, String name,boolean connected, int day_threshold, int night_threshold) {
		this.mac=mac;
		this.name=name;
		this.connected=connected;
		this.day_threshold=day_threshold;
		this.night_threshold=night_threshold;
	}

	public void setControllerId(Integer controllerId) {
		this.controllerId = controllerId;
	}
	
	public Integer getControllerId() {
		return controllerId;
	}
	
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setConnectState(boolean state) {
		this.connected = state;
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getMac() {
		return mac;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getConnectState() {
		return connected;
	}

	public Integer getDay_threshold() {
		return day_threshold;
	}

	public void setDay_threshold(Integer day_threshold) {
		this.day_threshold = day_threshold;
	}

	public Integer getNight_threshold() {
		return night_threshold;
	}

	public void setNight_threshold(Integer night_threshold) {
		this.night_threshold = night_threshold;
	}

	
}
















