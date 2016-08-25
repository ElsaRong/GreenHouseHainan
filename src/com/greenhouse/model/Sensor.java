 package com.greenhouse.model;
/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016年2月20日下午1:37:30 
* @version		1.0  
* @description			 
*/
public class Sensor {
	
	private String mac;
	private Integer sensorId;//Integer是int的封装类，用泛型List<Integer> nums;
	private Integer online;
	private Integer soiltemp;
	private Integer soilhum;
	private Integer soilph;
	private Integer airtemp;
	private Integer airhum;
	private Integer co2;
	private Integer illumination;

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getMac() {
		return mac;
	}
	
	public void setId(Integer sensorId) {
		this.sensorId = sensorId;
	}
	
	public Integer getId() {
		return sensorId;
	}
	
	public void setOnline(Integer b) {
		this.online = b;
	}
	
	public Integer getOnline() {
		return online;
	}
	
	public void setSoiltemp(Integer soiltemp) {
		this.soiltemp = soiltemp;
	}
	
	public Integer getSoiltemp() {
		return soiltemp;
	}
	
	public void setSoilhum(Integer soilhum) {
		this.soilhum = soilhum;
	}
	
	public Integer getSoilhum() {
		return soilhum;
	}
	
	public void setSoilph(Integer soilph) {
		this.soilph = soilph;
	}
	
	public Integer getSoilph() {
		return soilph;
	}
	
	public void setAirtemp(Integer airtemp) {
		this.airtemp = airtemp;
	}
	
	public Integer getAirtemp() {
		return airtemp;
	}
	
	public void setAirhum(Integer airhum) {
		this.airhum = airhum;
	}
	
	public Integer getAirhum() {
		return airhum;
	}
	
	public void setCo2(Integer co2) {
		this.co2 = co2;
	}
	
	public Integer getCo2() {
		return co2;
	}
	
	public void setIllumination(Integer illum) {
		this.illumination = illum;
	}
	
	public Integer getIllumination() {
		return illumination;
	}

}
