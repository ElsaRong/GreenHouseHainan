package com.greenhouse.util;

import java.util.List;

/** 
* @author   	Elsa 
* @Email    	elsarong715@gmail.com
* @date     	2015/11/19 AM9:16:48 
* @version  	1.0  
* @description 
*/
public class Const {
	
	public static final int TIME = 0x00;
	public static final int CONTOPEN = 0x01;
	public static final int CONTCLOS = 0x02;
	public static final int SENS = 0x03;
	public static final int TASK = 0x04;
	public static final int REMM = 0x05;
	public static final int ENUP = 0x06;
	public static final int BUUU = 0x07;
	public static final int BUND = 0x08;
	public static final int PROB = 0x09;
	public static final int REMO = 0x0A;
	public static final int QUER = 0x0B;
	public static final int DELE = 0x0C;
	public static final int HEARTBEAT = 0x18;
	public static final int INTE = 0x20;
	public static final int OUTT = 0x21;
	
	public static final int DANI = 0x19;
	public static final int THRE = 0x1A;
	
	public static final int UI_REFRESH = 0x0D;
	
	public static final int UI_REFRESH_FRAG_30 = 0x30;
	public static final int UI_REFRESH_FRAG_31 = 0x31;
	public static final int UI_REFRESH_FRAG_32 = 0x32;
	public static final int UI_REFRESH_FRAG_33 = 0x33;
	public static final int UI_REFRESH_FRAG_34 = 0x34;
	public static final int UI_REFRESH_FRAG_35 = 0x35;
	public static final int UI_REFRESH_FRAG_36 = 0x36;
	public static final int UI_REFRESH_FRAG_ENVIRONMENT = 0xE1;
	public static final int UI_REFRESH_FRAG_TIMESET = 0xE2;
	public static final int UI_REFRESH_FRAG_THRESET = 0xE3;
	
	
	public static final int SOCKET_CONNECTING = 0x0e;
	public static final int SOCKET_CONNECTED = 0x0f;
	public static final int SOCKET_EXCEPTION = 0x10;
	public static final int SOCKET_DISCONNECTED = 0x11;
	
	
	
	public static final int FINISHED = 0X12;
	
	public static final int TIME_SERVICE_FINISHED = 0x13; //授时成功，收到TIME回执
	public static final int TIME_SERVICE_FAILED = 0x14;   //授时失败
	public static final int TIMEOUT = 0x14;               //授时超时，授时3s后没有收到回执
	
	public static final int TITLE_WAITING = 0x15;
	
	public static final int SOCKET_RECONNECTED = 0x16;
//	public static final int SOCKET_RECONNECTING = 0x18;
	
	public static final int BACK_TO_LAUNCHER = 0x17;
	
	
	public static final int server_PORT = 8521; //作为服务端，等待TV客户端连接的监听端口号
	public static final int client_PORT = 8899; //作为客户端，请求连接的阿里云服务器端口号
	
	public static final String HFUT = "48465554";
	public static final String WAN = "57414e";
	public static final String QUER_STATISTIC = "QUER";
	public static final String WANA_STATISTIC = "WANA";
	
	
	public static final int JACK_SUM = 35;   //插座总数35个
	public static final int SENSOR_SUM = 8;  //传感器总数8个
	
	public static final String AIR_TEMP = "空气温度";
	public static final String AIR_HUM = "空气湿度";
	public static final String CO2 = "CO2浓度";
	public static final String SOIL_TEMP = "土壤温度";
	public static final String SOIL_HUM = "土壤湿度";
	public static final String PH = "土壤PH";
	public static final String ILLUMINATION = "光照度";
	public static final String AIR_TEMP_UNIT = "℃";
	public static final String AIR_HUM_UNIT = "%";
	public static final String CO2_UNIT = "PPM";
	public static final String SOIL_TEMP_UNIT = "℃";
	public static final String SOIL_HUM_UNIT = "%";
	public static final String PH_UNIT = " ";
	public static final String ILLUMINATION_UNIT = "LUX";
	
	public static final String CURRENT_VALUE = "实时值 ";
	public static final String RECORD_VALUE = "历史值 ";
	public static final String DAY_THRE_VALUE = "白天门限";
	public static final String DAY_THRE_RECORD_VALUE = "白天门限历史值";
	public static final String NIGHT_THRE_VALUE = "夜间门限";
	public static final String NIGHT_THRE_RECORD_VALUE = "夜间门限历史值";
	
}
