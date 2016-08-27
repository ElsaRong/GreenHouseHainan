package com.greenhouse.networkservice;

import java.util.Calendar;
import com.greenhouse.ui.JackFragmentModeSet;
import com.greenhouse.ui.Launcher;
import com.greenhouse.ui.SensorRecyclerView;
import com.greenhouse.ui.SensorSetting;
import com.greenhouse.ui.Timer;
import com.greenhouse.util.DataFormatConversion;
import com.greenhouse.widget.CustomDatePicker;
import com.greenhouse.widget.CustomTimePicker;
import com.greenhouse.widget.JackStatisticCustomDatePicker;
import com.greenhouse.widget.JackStatisticCustomNumberPicker;
import android.os.Message;
import android.util.Log;

/** 
* 组装待发送的报文，发送在SocketOutputTask中实现，本类只负责组装报文
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/1/11 PM 9:14:00 
* @version      1.0  
*/

public class CommProtocol{

	private static final String TAG = "CommProtocol.java";

	public static void sendMessageHEARTBEAT() {
		String 	msg = "HFUT" + Launcher.selectMac + "HEARTBEAT00000000000WANG";
		SocketOutputTask.sendMessageStr(msg);
		Log.d(TAG, "[Send:HEARTBEAT]" + msg);
	}
	
	public static void sendMessageTIME(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String Year = Integer.toString(year);
		String Month = Integer.toString(month);
		String Day = Integer.toString(day);
		String Hour = Integer.toString(hour);
		String Minute = Integer.toString(minute);
		String Second = Integer.toString(second);
		if (month < 10) {
			Month = "0" + Month;
		}
		if (day < 10) {
			Day = "0" + Day;
		}
		if (hour < 10) {
			Hour = "0" + Hour;
		}
		if (minute < 10) {
			Minute = "0" + Minute;
		}
		if (second < 10) {
			Second = "0" + Second;
		}
		String 	msg = "HFUT" + Launcher.selectMac + "TIME" + Year + Month + Day + Hour + Minute + Second + "00" + "0000" + "WANG";
		

		Log.d(TAG, "[TIME]" + msg);
		SocketOutputTask.sendMessageStr(msg);
		
	}
	
//	// 20151201基本修改完成
//	public static void sendMessageQUER(){
//		String msg;
//		msg = "HFUT" + Launcher.selectMac + "QUER" +
//				JackStatisticCustomDatePicker.sYear + JackStatisticCustomDatePicker.sMonth + 
//				JackStatisticCustomDatePicker.sDay + "0" + JackStatisticCustomNumberPicker.timeSpan + 
//				"0000000000" + "WANG";
//		SocketOutputTask.sendMessageStr(msg.replace("", ""));
//		Log.e(TAG, "[Send:QUER]" + msg);
//	}
	

	/**
	 * @Title:       sendMessageINTE
	 * @description: TODO 进入开关测试报文
	 * @param        
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 24, 2016, 4:52:25 PM
	 */
	public static void sendMessageINTE() {
		String msg;
		msg = "HFUT" + Launcher.selectMac + "INTE00000000000000000000WANG";
		Log.e(TAG, "[INTE]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}
	
	/**
	 * @Title:       sendMessageOUTT
	 * @description: TODO 进入开关测试报文
	 * @param        
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 24, 2016, 4:52:25 PM
	 */
	public static void sendMessageOUTT() {
		String msg;
		msg = "HFUT" + Launcher.selectMac + "OUTT00000000000000000000WANG";
		Log.e(TAG, "[OUTT]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}
	

	public static void sendMessageTIMESET(Message message) {
		String msg;
		String sDay = "", sNight = "";
		if (message.arg1 < 10) {
			sDay = "0" + message.arg1;
		} else {
			sDay = message.arg1 + "";
		}
		if (message.arg2 < 10) {
			sNight = "0" + message.arg2;
		} else {
			sNight = message.arg2 + "";
		}
		msg = "HFUT" + Launcher.selectMac + "DANI" + sDay + "00" + sNight + "00" + "000000000000" + "WANG";
		Log.d(TAG, "[DANI]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}
	
	// 20151201基本修改完成
	public static void sendMessageTHRE(Message message) {
		String msg = "";
		
		switch(message.arg1) {
		case 1:
			msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "0" + message.arg2 + "0000000000000000" + "WANG";
			break;
		case 2:
			msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "0" + message.arg2 + "0000000000000000" + "WANG";
			break;
		case 3:
			if (message.arg2 > 10) {
				msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "0" + message.arg2 + "0000000000000000" + "WANG";
			} else {
				msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "0" + message.arg2 + "0000000000000000" + "WANG";
			}
			break;
		case 4:
			msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "0" + message.arg2 + "0000000000000000" + "WANG";
			break;
		case 5:
			msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "0" + message.arg2 + "0000000000000000" + "WANG";
			break;
		case 6:
			msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "000" + message.arg2 + "00" + "000000000000" + "WANG";
			break;
		case 7:
			if (message.arg2 < 10) {
				msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "000" + message.arg2 + "00000000000000" + "WANG";
			} else if (message.arg2 >= 10 && message.arg2 < 100) {
				msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "00" + message.arg2 + "00000000000000" + "WANG";
			} else if (message.arg2 >= 100 && message.arg2 < 1000) {
				msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + "0" + message.arg2 + "00000000000000" + "WANG";
			} else if (message.arg2 >= 1000 && message.arg2 < 10000) {
				msg = "HFUT" + Launcher.selectMac + "THRE0" + message.arg1 + message.arg2 + "00000000000000" + "WANG";
			}
			break;
			default:
				break;
		}
		
		Log.d(TAG, "[THRE]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}
	


	// 20151201基本修改完成
	public static void sendMessageCONTOPEN(Object id) {
		String msg;
		msg = "HFUT" + Launcher.selectMac + "CONT" + "00" + id + "OPEN" + "000000000000WANG";
		Log.d(TAG, "[OPEN]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}
	

	// 20151201基本修改完成
	public static void sendMessageCONTCLOS(Object id) {
		String msg;
		msg = "HFUT" + Launcher.selectMac + "CONT" + "00" + id + "CLOS" + "000000000000WANG";
		Log.d(TAG, "[CLOS]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}
	
	public static void sendMessageREMO(Object id) {
		String msg = "HFUT" + Launcher.selectMac + "REMO00" + id + "0000000000000000WANG";
		Log.d(TAG, "[REMO]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}


	public static void sendMessagePROB() {
		String msg = null;
		msg = "HFUT" + Launcher.selectMac + "PROB00" + SensorRecyclerView.sProbeSensor + "0000000000000000WANG";
		Log.d(TAG, "[PROB]" + msg);
		SocketOutputTask.sendMessageStr(msg.replace("", ""));
	}
	
	public static void sendMessageTASK() {
		String msg = null;
		String year, month, day, hour, minute, on_hour, on_minute, off_hour, off_minute, circle;
		year = "0" +Integer.toHexString(CustomDatePicker.Year);
		month = modifyFormat(Integer.toHexString(CustomDatePicker.Month));
		day = modifyFormat(Integer.toHexString(CustomDatePicker.Day));
		hour = modifyFormat(Integer.toHexString(CustomDatePicker.Hour));
		minute = modifyFormat(Integer.toHexString(CustomDatePicker.Minute));
		on_hour = modifyFormat(Integer.toHexString(CustomTimePicker.on_hour));
		on_minute = modifyFormat(Integer.toHexString(CustomTimePicker.on_minute));
		off_hour = modifyFormat(Integer.toHexString(CustomTimePicker.off_hour));
		off_minute = modifyFormat(Integer.toHexString(CustomTimePicker.off_minute));
		circle = "00" + modifyFormat(Integer.toHexString(CustomTimePicker.CIRCLE));
		
		msg = DataFormatConversion.IrreguStringToHexValue("HFUT" + Launcher.selectMac + "TASK") 
				+ year + month + day + hour + minute + on_hour + on_minute + off_hour + off_minute + circle
				+ Timer.chosedJackGroupHex + "0000"
				+ DataFormatConversion.IrreguStringToHexValue("WANG");
		
		byte[] b = DataFormatConversion.HexStringToByte(msg);
		Log.d(TAG, "[TASK-JACK]" + Timer.chosedJackGroupHex);
		Log.d(TAG, "[TASK]" + msg);
		SocketOutputTask.sendMessageByte(b);
	}
	
    public static final byte[] hex2byte(String hex)
            throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }
	
	private static String modifyFormat(String s) {
		if (s.length() == 1) {
			s = "0" + s;
			return s;
		} else {
			return s;
		}
	}

	public static void sendMessageDELE() {
		String msg = null;
		msg = DataFormatConversion.IrreguStringToHexValue("HFUT" + Launcher.selectMac + "DELE")		
				+ Timer.chosedJackGroupHex + DataFormatConversion.IrreguStringToHexValue("00000000000000WANG");
		
		byte[] b = DataFormatConversion.HexStringToByte(msg);	
		Log.d(TAG, "[DELE]" + msg);
		SocketOutputTask.sendMessageByte(b);
	}

	public static void sendMessageSENS() {
		String msg = "HFUT" + Launcher.selectMac + "SENSOPEN0000000000000000WANG";
		SocketOutputTask.sendMessageStr(msg);
	}
	
	
	/**
	 * @Title:       sendMessageBUND
	 * @description: TODO 发送绑定传感器任务报文,只能绑定单个传感器
	 * @param        @param outputStream
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 20, 2016, 2:30:40 PM
	 */
	public static void sendMessageBUND() {
		String msg = "";                    //拼接报文
		String hexJackId = "";              //插座编号转十六进制字符
		String hexSensorAndDeviceType = ""; //选定的传感器类型和设备类型编号转十六进制字符
		String hexBundSensorId = "";        //选择绑定的传感器编号转十六进制字符
		String hexDayThre = "";             //白天门限值转十六进制字符
		String hexNightThre = "";           //夜间门限值转十六进制字符
		String hexAllThre = "";             //拼接7种传感器早晚门限
		
		hexJackId = Integer.toHexString(JackFragmentModeSet.sJackId);
		hexSensorAndDeviceType = DataFormatConversion.IntSensorAndDeviceTypeToHexstring(SensorSetting.sSensorType, SensorSetting.sDeviceType);
		hexBundSensorId = Integer.toHexString(Integer.valueOf(SensorRecyclerView.sBinSelectSensor,2));
//		hexDayThre = Integer.toHexString(Integer.parseInt(SensorSetting.sSetDayThre));
//		hexNightThre =  Integer.toHexString(Integer.parseInt(SensorSetting.sSetNightThre));
		
		//补位：十六进制字符“0”
		hexJackId = DataFormatConversion.FormatStringByAddZero(hexJackId, 2);                           //插座编号：2位十六进制字符
		hexSensorAndDeviceType = DataFormatConversion.FormatStringByAddZero(hexSensorAndDeviceType, 2); //传感器和设备类型：2位十六进制字符
		//第六种传感器要求长度6
		if (SensorSetting.sSensorType == 6) {            
			hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 3);
			hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 3);
		//第七种传感器要求长度6
		} else if (SensorSetting.sSensorType == 7) { 
			hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 4);
			hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 4);
		//其它类型传感器要求长度6
		} else { 
			hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 2);
			hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 2);
		}
		
		//拼接7种传感器早晚门限，累计34位十六进制字符
		hexAllThre = DataFormatConversion.FormatAllThreByAddZero(hexDayThre+hexNightThre, SensorSetting.sSensorType);
		
		msg = DataFormatConversion.IrreguStringToHexValue("HFUT" + Launcher.selectMac + "BUND")
			    + hexJackId + hexSensorAndDeviceType  //插座［2］－传感器设备类型［2］
				+ hexAllThre + hexBundSensorId        //早晚1［4］－早晚2［4］－早晚3［4］－早晚5［4］－早晚5［4］－早晚6［6］-早晚7［8］－选择定传感器［2］
				+ DataFormatConversion.IrreguStringToHexValue("WANG");
		
		byte[] b = DataFormatConversion.HexStringToByte(msg);	
		
		Log.d(TAG, "[BUND]" + msg);
		SocketOutputTask.sendMessageByte(b);

	}
	
	/**
	 * @Title:       sendMessageMultiBund
	 * @description: TODO 发送绑定传感器任务报文,可以绑定多个传感器
	 * @param        
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 27, 2016, 5:09:33 PM
	 */
	public static void sendMessageMultiBund() {
		String msg = "";                    //拼接报文
		String hexJackId = "";              //插座编号转十六进制字符
		String hexSensorAndDeviceType = ""; //选定的传感器类型和设备类型编号转十六进制字符
		String hexBundSensorId = "";        //选择绑定的传感器编号转十六进制字符
		StringBuffer hexAllThreBuffer = new StringBuffer("0000000000000000000000000000000000");//拼接7种传感器早晚门限
		String hexAllThre = "";
		
		hexJackId = Integer.toHexString(JackFragmentModeSet.sJackId);
		hexBundSensorId = Integer.toHexString(Integer.valueOf(SensorRecyclerView.sBinSelectSensor,2));
		
		hexSensorAndDeviceType = DataFormatConversion.MultiSensAndDevTypeToHexStr(SensorSetting.sChosedSensor, SensorSetting.sDeviceType);

		
		//补位: 插座&传感器&设备类型
		hexJackId = DataFormatConversion.FormatStringByAddZero(hexJackId, 2);                           //插座编号：2位十六进制字符
		hexSensorAndDeviceType = DataFormatConversion.FormatStringByAddZero(hexSensorAndDeviceType, 2); //传感器和设备类型：2位十六进制字符
		//补位: 早晚门限
		for (int i=0; i<6; i++) {
			String hexDayThre = Integer.toHexString(SensorSetting.sSetDayThre[i]);
			String hexNightThre = Integer.toHexString(SensorSetting.sSetNightThre[i]);
			//第六种传感器要求长度6
			if (i==5) {
				hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 3);
				hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 3);
			} 
			//第七种传感器要求长度6
			else if (i==6) {
				hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 4);
				hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 4);
			} 
			//其它类型传感器要求长度2
			else {
				hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 2);
				hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 2);
			}
			//拼接7种传感器早晚门限，累计34位十六进制字符
			hexAllThreBuffer = DataFormatConversion.FormatMultiThreByReplace(hexAllThreBuffer, hexDayThre+hexNightThre, i+1);
		}
		hexAllThre = hexAllThreBuffer.toString();
		
		
		msg = DataFormatConversion.IrreguStringToHexValue("HFUT" + Launcher.selectMac + "BUND")
			    + hexJackId + hexSensorAndDeviceType  //插座［2］－传感器设备类型［2］
				+ hexAllThre + hexBundSensorId        //早晚1［4］－早晚2［4］－早晚3［4］－早晚5［4］－早晚5［4］－早晚6［6］-早晚7［8］－选择定传感器［2］
				+ DataFormatConversion.IrreguStringToHexValue("WANG");
		
		byte[] b = DataFormatConversion.HexStringToByte(msg);	
		
		Log.d(TAG, "[BUND]" + msg);
		SocketOutputTask.sendMessageByte(b);

		
	}
	


}
