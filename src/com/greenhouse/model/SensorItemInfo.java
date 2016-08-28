package com.greenhouse.model;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			Aug 28, 2016 9:22:27 AM 
* @version		1.0  
* @description  联动模式显示数据，类对象List绑定到ShowSensorInfoAdapter
*/

public class SensorItemInfo {
	
	private int sensorType;
	private int averageValue;
	private int dayThre;
	private int nightThre;
	
	public void setSensorType(int sensorType) {
		this.sensorType = sensorType;
	}
	
	public void setAverageValue(int averageValue) {
		this.averageValue = averageValue;
	}
	
	public void setDayThre(int dayThre) {
		this.dayThre = dayThre;
	}
	
	public void setNightthre(int nightThre) {
		this.nightThre = nightThre;
	}
	
	public int getSensorType() {
		return sensorType;
	}
	
	public int getAverageValue() {
		return averageValue;
	}
	
	public int getDayThre() {
		return dayThre;
	}
	
	public int getNightThe() {
		return nightThre;
	}
	

}
