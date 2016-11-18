package com.greenhouse.util;

import java.util.List;
import android.util.Log;

/** 
* class <code>DataFormatConversion</code> 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/10 AM9:16:51 
* @version      1.0  
*/
public class DataFormatConversion {
	public static final String TAG = "DataFormatConversion";

	public static void IntegerJackToHexString(int jackId) {
		String hex = Integer.toBinaryString(jackId);  
//		Log.d(TAG, "jackId = " + jackId + "；Hex = " + hex);
	}
	
	 public static String stringToHexString(String strPart) {
		    String hexString = "";
		    for (int i = 0; i < strPart.length(); i++) {
		        int ch = (int) strPart.charAt(i);
		        String strHex = Integer.toHexString(ch);
		        hexString = hexString + strHex;
		    }
		    return hexString;
		 }
	
	/**
	 * @Title:       HexStr2ToJackId
	 * @description: TODO
	 * @param        @param hexStr
	 * @param        @return
	 * @return       Integer
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 27, 2016, 2:23:51 PM
	 */
	public static Integer HexStr2ToJackId(String hexStr) {
		Integer jackId;
		jackId = Integer.valueOf(hexStr,16);
		return jackId;
	}
	
	
	public static String JackTaskDataFormatConversion(String s) {
		if (s.equals("30")) {
			return "0";
		} else {
			String ss = Integer.valueOf(s, 16).toString();
			return ss;
		}
	}
	 
	 public static String asciiToString(String value) {
		 StringBuffer sbu = new StringBuffer();
		 String[] chars = value.split(",");
		 for(int i = 0; i< chars.length; i++) {
			 sbu.append((char)Integer.parseInt(chars[i]));
		 }
		 return sbu.toString();
	 }
	 
	 
	 /**
	  * @Title:       hexString2binaryString
	  * @description: TODO 十六进制字符串转二进制字符串
	  * @param        @param hexString
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 21, 2016, 10:15:33 AM
	  */
     public static String HexStringToBinaryString(String hexString) {  
         if (hexString == null || hexString.length() % 2 != 0) {
        	 return null;  
         }
         String binString = ""; //转换结果返回的二进制字符串
         String tmp = "";       //
         Integer intTmp = Integer.valueOf(0);
         for (int i = 0; i < hexString.length(); i++)  
         {  
        	 intTmp = Integer.parseInt(hexString.substring(i, i + 1), 16);
             tmp = "0000" + Integer.toBinaryString(intTmp);  
             binString += tmp.substring(tmp.length() - 4);  
         }  
         return binString;  
     } 
     
     /**
      * @Title:       HexStrBit12ToJackId
      * @description: TODO 将TASK关键字携带的插座信息从12位十六进制码转换成Integer型插座ID
      * @param        @param hexString
      * @param        @return
      * @return       Integer
      * @throws
      * @author       Elsa elsarong715@gmail.com
      * @data         Aug 24, 2016, 10:38:06 PM
      */
     public static Integer HexStrBit12ToJackId(String hexString) {
    	 StringBuffer buffer = new StringBuffer();
    	 int JackId = 0;
    	 for (int i=0; i<hexString.length(); i++) {
    		 String s = hexString.substring(i, i+1);
    		 if (s.equals("0")) {
    			 buffer.append("0000");
    		 } else if (s.equals("1")) {
    			 buffer.append("0001");
    		 } else if (s.equals("2")) {
    			 buffer.append("0010");
    		 } else if (s.equals("4")) {
    			 buffer.append("0100");
    		 } else if (s.equals("8")) {
    			 buffer.append("1000");
    		 } 
    	 }
    	 String binString = buffer.toString();
//    	 Log.i(TAG, "hex2bin jackId = " + buffer.toString());
    	 for  (int i=0; i<binString.length(); i++) {
    		 String s = binString.substring(i, i+1);
    		 if (s.equals("1")) {
    			 JackId = (i+1);
//    			 Log.i(TAG, "hex2int jackId = " + JackId);
    			 return JackId;
    		 }
    	 }
		return JackId;
     }
     
     
     
   //二进制字符串转Int,eg.00100011=35
     public static Integer BinStringToInt(String binString) {
    	 if (binString == null || binString.length() % 2 != 0) {
        	 return null;  
         }
    	 Integer integer = Integer.parseInt(binString, 2);
    	 return integer;
     }
     
     /**
      * @Title:       SensorTypeConversion
      * @description: TODO 传感器类型格式转换,eg.1000000->1;0100000->2
      * @param        @param binString
      * @param        @return
      * @return       Integer
      * @throws
      * @author       Elsa elsarong715@gmail.com
      * @data         Aug 27, 2016, 2:18:14 PM
      */
     public static Integer SensorTypeConversion(String binString) {
    	 if (binString == null || binString.length() < 7) {
        	 return (Integer)0;  
         }
    	 Integer SensorType = 0;
    	 if (binString.substring(0, 1).equals("1")) {
    		 SensorType=1;//土壤温度
    	 } else if (binString.substring(1, 2).equals("1")) {
    		 SensorType=2;//土壤湿度
    	 } else if (binString.substring(2, 3).equals("1")) {
    		 SensorType=3;//土壤酸碱度
    	 } else if (binString.substring(3, 4).equals("1")) {
    		 SensorType=4;//空气温度
    	 } else if (binString.substring(4, 5).equals("1")) {
    		 SensorType=5;//空气湿度
    	 } else if (binString.substring(5, 6).equals("1")) {
    		 SensorType=6;//CO2浓度
    	 } else if (binString.substring(6, 7).equals("1")) {
    		 SensorType=7;//光照度
    	 }
    	 return SensorType;
     }
     
	 

	 /**
	  * @Title:       BinaryStringToHexString
	  * @description: TODO 二进制字符串转换成十六进制字符串，通过位运算自定义实现，FAIL!
	  * @param        @param binString
	  * @param        @return 转换后的十六进制字符串
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 11:43:02 AM
	  */
	 public static String BinaryStringToHexString(String binString) {
		 int intTmp = 0;                           //保存每4个二进制字符转换出的1个十六进制字符
		 String hexString = "";                    //转换后的十六进制字符串
		 StringBuffer buffer = new StringBuffer(); //转换过程中使用的字符串缓冲区
		 if (binString == null || binString.equals("") || binString.length() % 8 != 0) {
			 return null;
		 }
		 for (int i=0; i<binString.length(); i+=4) {
			 intTmp = 0;
			 for (int j=0; j<4; j++) {
				 intTmp += Integer.parseInt(binString.substring(i+j, i+j+1)) << (4-j-i); //位运算＋求和，左移＝num*(2^n)
			 }
			 buffer.append(Integer.toHexString(intTmp));
		 }
		 hexString = buffer.toString();
//		 Log.i(TAG, "binString=" + binString + "; hexString=" + hexString);
		 return hexString;
	 }
	 
	 /**
	  * @Title:       BinStr48ToHexStr12
	  * @description: TODO 设置定时任务,选定的插座从48位0-1转换成12位十六进制字符
	  * @param        @param binString
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 27, 2016, 2:19:14 PM
	  */
	 public static String BinStr48ToHexStr12(String binString) {
		 StringBuffer buffer = new StringBuffer();
		 String hexString = "";
		 for (int i=0; i<48; i+=4) {
			 int intTmp = Integer.valueOf(binString.substring(i, i+4), 2);
			 buffer.append(Integer.toHexString(intTmp));
		 }
		 hexString = buffer.toString();
		 hexString = FormatStringByAddZero2(hexString, 12);
//		 Log.i(TAG, "Chosed Jack = " + hexString); 
		 return hexString;
	 }
	 
	 /**
	  * @Title:       BinaryStringToHexString2
	  * @description: TODO 二进制字符串转换成十六进制字符串,通过调用Java API实现
	  * @param        @param binString
	  * @param        @return 转换后的十六进制字符串
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 11:43:02 AM
	  */
	 public static String BinaryStringToHexString2(String binString) {
		 String hexString = "";
		 int intTmp = 0;
		 if (binString == null || binString.equals("") || binString.length() % 8 != 0) {
			 return null;
		 }
		 intTmp = Integer.valueOf(binString,2);
		 hexString = Integer.toHexString(intTmp);
		 Log.i(TAG, "binString=" + binString + "; intTmp=" + intTmp + "; hexString=" + hexString);
		 return hexString;
	 }
	 
	 /**
	  * @Title:       IntSensorAndDeviceTypeToHexstring
	  * @description: TODO 将int型传感器类型和int型位设备类型转换成8位二进制－>十六进制字符串
	  * @param        @param sensor_type 传感器类型，范围1-7
	  * @param        @param device_type 设备类型，0or1，默认0
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 3:17:37 PM
	  */
	 public static String IntSensorAndDeviceTypeToHexstring(int sensor_type, int device_type) {
		 String hexSensorAndDeviceType = "";
		 StringBuffer buffer = new StringBuffer("00000000");
		 if (sensor_type > 7 || sensor_type < 1) {
			 return null;
		 }
		 buffer.replace(sensor_type-1, sensor_type, "1"); //替换传感器类型位
		 buffer.replace(7, 8, device_type+"");			  //替换设备类型位
		 hexSensorAndDeviceType = Integer.toHexString(Integer.valueOf(buffer.toString(),2)); //8位二进制－>十六进制字符
		 Log.i(TAG, "buffer=" + buffer + "; hexSensorAndDeviceType=" + hexSensorAndDeviceType);
		 return hexSensorAndDeviceType;
	 }
	 
	 /**
	  * @Title:       MultiSensAndDevTypeToHexStr
	  * @description: TODO 将int[]型绑定传感器类型和int型设备类型(0or1)转换成8位二进制－>十六进制字符串
	  * @param        @param sensor_type int[]型,数组值为0or1
	  * @param        @param device_type 0or1,默认0
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 27, 2016, 5:32:21 PM
	  */
	 public static String MultiSensAndDevTypeToHexStr(int[] multi_sensor, int device_type) {
		 StringBuffer buffer = new StringBuffer("00000000");
		 String hexMultiSensAndDevType = "";
		 
		 for (int i=0; i<7; i++) {
			 if (multi_sensor[i]==1) {
				 buffer.replace(i, i+1, 1+"");
			 }
		 }
		 buffer.replace(7, 8, device_type+"");
//		 Log.i(TAG, "-----> (bin)hexMultiSensorAndDeviceType = " + buffer.toString());
		 hexMultiSensAndDevType = Integer.toHexString(Integer.valueOf(buffer.toString(),2));
//		 Log.i(TAG, "-----> (hex)hexMultiSensorAndDeviceType = " + hexMultiSensAndDevType);
		 return hexMultiSensAndDevType;
	 }
	 
	 /**
	  * @Title:       ListSelectSensorToHexString
	  * @description: TODO 将用户选中的绑定传感器编号转换成十六进制字符
	  * @param        @param select_sensor
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 4:12:42 PM
	  */
	 public static String ListSelectSensorToHexString(List<String> select_sensor) {
		 String hexSelectSensor = "";
		 StringBuffer buffer = new StringBuffer("00000000");
		 for (int i = 0; i < select_sensor.size(); i++) {
			int id = Integer.parseInt(select_sensor.get(i));
			 buffer.replace(id-1, id, "1");
		 }
		 hexSelectSensor = Integer.toHexString(Integer.valueOf(buffer.toString(),2)); //8位二进制－>int->十六进制字符
		 Log.i(TAG, "List=" + select_sensor.toString() + "; hexSelectSensor=" + hexSelectSensor);
		 return hexSelectSensor;
	 }
	 
	 /**
	  * @Title:       ListSelectSensorToBinString
	  * @description: TODO 将用户选中的绑定传感器编号转换成十六进制字符
	  * @param        @param select_sensor
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 4:12:42 PM
	  */
	 public static String ListSelectSensorToBinString(List<String> select_sensor) {
		 String binString = "";
		 StringBuffer buffer = new StringBuffer("00000000");
		 for (int i = 0; i < select_sensor.size(); i++) {
			int id = Integer.parseInt(select_sensor.get(i));
			 buffer.replace(id-1, id, "1");
		 }
		 binString = buffer.toString();
//		 Log.i(TAG, "binString=" + buffer.toString());
		 return binString;
	 }
	 
	 /**
	  * @Title:       HexStringToByte
	  * @description: TODO 十六进制字符转换成byte，
	  * @param        @param hexString
	  * @param        @return
	  * @return       byte[]
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 7:27:43 PM
	  */
	 public static byte[] HexStringToByte(String hexString) {
		 if (hexString == null || hexString.equals("")) {
			 return null;
		 }
		 hexString = hexString.toUpperCase();
		 int hexLen = hexString.length()/2;
		 char[] hexChar = hexString.toCharArray();
		 byte[] b = new byte[hexLen];
		 for (int i=0; i<hexLen; i++) {
			 int position = i*2;
			 b[i] = (byte) (charToByte(hexChar[position]) << 4 | charToByte(hexChar[position+1]));
//			 Log.e(TAG, "b["+i+"] = "+b[i]);
		 }

		 return b;
	 }
	 
     //单个字符转换成byte
	 public static byte charToByte(char c) {
		 return  (byte)"0123456789ABCDEF".indexOf(c);
	 }

	 /**
	  * @Title:       IrreguStringToHexString
	  * @description: TODO 不规则字符串（不能转为整型）转换为十六进制字符
	  * @param        @param irreguStr
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 7:38:39 PM
	  */
	 public static String IrreguStringToHexValue(String irreguStr) {
		 String hexStr = "";
		 StringBuffer buffer = new StringBuffer();
		 for (int i=0; i<irreguStr.length(); i++) {
			 int c = (int)irreguStr.charAt(i);
			 buffer.append(Integer.toHexString(c));
		 }
		 hexStr = buffer.toString();
//		 Log.i(TAG,"irreguStr=" + irreguStr + "; hexStr=" + hexStr);
		 return hexStr;
	 }
	 
	 /**
	  * @Title:       FormatStringByAddZero
	  * @description: TODO 将输入字符串补0，满足要求长度，这里的补0是往前补
	  * @param        @param unformat_str
	  * @param        @param format_len
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 9:20:05 PM
	  */
	 public static String FormatStringByAddZero(String unformat_str, int format_len) {
		 String formatStr = "";
		 int len = unformat_str.length();
		 if (len < format_len) {
			 formatStr = "0" + unformat_str;
		 } else {
			 formatStr = unformat_str;
			 return formatStr;
		 }
		 return FormatStringByAddZero(formatStr, format_len);
	 }
	 
	 /**
	  * @Title:       FormatStringByAddZero2
	  * @description: TODO 将输入字符串补0，满足要求长度，这里的补0是往后补
	  * @param        @param unformat_str
	  * @param        @param format_len
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 9:20:05 PM
	  */
	 public static String FormatStringByAddZero2(String unformat_str, int format_len) {
		 String formatStr = "";
		 int len = unformat_str.length();
		 if (len < format_len) {
			 formatStr = unformat_str + "0";
		 } else {
			 formatStr = unformat_str;
			 return formatStr;
		 }
		 return FormatStringByAddZero2(formatStr, format_len);
	 }
	 
	 /**
	  * @Title:       FormatAllThreByAddZero
	  * @description: TODO 将7种传感器的早晚门限拼接成标准34位十六进制字符,只适用于绑定一种床传感器,其它全0的情况
	  * @param        @param s
	  * @param        @param sensor_type
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 20, 2016, 10:10:13 PM
	  */
	 public static String FormatAllThreByAddZero(String s, int sensor_type) {
		 String formatAllThre = "";
		 switch(sensor_type) {
		 case 1:
			 formatAllThre = s + "00000000" + "00000000" + "00000000" + "000000";      //4+8*3+6=34
			 break;
		 case 2:
			 formatAllThre = "0000" + s + "00000000" + "00000000" + "00000000" + "00"; //4+4+8*3+2=34
			 break;
		 case 3:
			 formatAllThre = "00000000" + s + "00000000" + "00000000" + "000000";      //8+4+8*2+6=34
			 break;
		 case 4:
			 formatAllThre = "00000000" + "0000" + s + "00000000" + "00000000" + "00"; //8+4+4+8*2+2=34
			 break;
		 case 5:
			 formatAllThre = "00000000" + "00000000" + s + "0000" + "00000000" + "00"; //8*2+4+4+8+2=34
			 break;
		 case 6:
			 formatAllThre = "00000000" + "00000000" + "0000" + s + "00000000";        //8*2+4+6+8=34
			 break;
		 case 7:
			 formatAllThre = "00000000" + "00000000" + "00000000" + "00" + s;          //8*3+2+8=34
			 break;
		 }
		 Log.i(TAG, "formatAllThre.length=" + formatAllThre.length());
		 return formatAllThre;
	 }
	 
	 /**
	  * @Title:       FormatMultiThreByReplace
	  * @description: TODO 将7种传感器的早晚门限拼接成标准34位十六进制字符,适用于绑定多种传感器
	  * @param        @param buffer_all_thre
	  * @param        @param s
	  * @param        @param sensor_type
	  * @param        @return
	  * @return       StringBuffer
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 27, 2016, 6:41:16 PM
	  */
	 public static StringBuffer FormatMultiThreByReplace(StringBuffer buffer_all_thre, String s, int sensor_type) {
		 switch(sensor_type) {
		 case 1:
			 buffer_all_thre.replace(0, 4, s);
			 break;
		 case 2:
			 buffer_all_thre.replace(4, 8, s);
			 break;
		 case 3:
			 buffer_all_thre.replace(8, 12, s);
			 break;
		 case 4:
			 buffer_all_thre.replace(12, 16, s);
			 break;
		 case 5:
			 buffer_all_thre.replace(16, 20, s);
			 break;
		 case 6:
			 buffer_all_thre.replace(20, 26, s);
			 break;
		 case 7:
			 buffer_all_thre.replace(26, 34, s);
			 break;
		 default:
			 break;
		 }
//		 Log.i(TAG, buffer_all_thre.toString());
		 return buffer_all_thre;
	 }
	 
		
	 /**
	  * @Title:       IntSensorToBinSensor
	  * @description: TODO
	  * @param        @param sensorid
	  * @param        @return
	  * @return       String
	  * @throws
	  * @author       Elsa elsarong715@gmail.com
	  * @data         Aug 21, 2016, 4:01:37 PM
	  */
	 public static String IntSensorToBinSensor(String sensorid) {
		String sensors = "00000000";
		switch (sensorid) {
		case "01":
			sensors = "1" + sensors.substring(1, 8);
			break;
		case "02":
			sensors = sensors.substring(0, 1) + "1" + sensors.substring(2, 8);
			break;
		case "03":
			sensors = sensors.substring(0, 2) + "1" + sensors.substring(3, 8);
			break;
		case "04":
			sensors = sensors.substring(0, 3) + "1" + sensors.substring(4, 8);
			break;
		case "05":
			sensors = sensors.substring(0, 4) + "1" + sensors.substring(5, 8);
			break;
		case "06":
			sensors = sensors.substring(0, 5) + "1" + sensors.substring(6, 8);
			break;
		case "07":
			sensors = sensors.substring(0, 6) + "1" + sensors.substring(7, 8);
			break;
		case "08":
			sensors = sensors.substring(0, 7) + "1";
			break;
		}
		return sensors;
	}
	 

		
		public static String Int2String(int[] a) {
			String str="";
			for(int i=0;i<a.length;i++) {
				str+=String.valueOf(a[i]);
			}
			Log.d(TAG, "int[] a = " + a + "；String = " + str);
			return str;
		}
		

		
		 public static String convertHexToString(String hex){
			  StringBuilder sb = new StringBuilder();
			  for( int i=0; i<hex.length()-1; i+=2 ){
			      String output = hex.substring(i, (i + 2));
			      int decimal = Integer.parseInt(output, 16);
			      sb.append((char)decimal);
			  }
			  return sb.toString();
		  }
		 
		 
		 public static String bytesToHexString(byte[] src){  
			    StringBuilder stringBuilder = new StringBuilder("");  
			    if (src == null || src.length <= 0) {  
			        return null;  
			    }  
			    for (int i = 0; i < src.length; i++) {  
			        int v = src[i] & 0xFF;  
			        String hv = Integer.toHexString(v);  
			        if (hv.length() < 2) {  
			            stringBuilder.append(0);  
			        }  
			        stringBuilder.append(hv);  
			    }  
			    return stringBuilder.toString();  
			}
		 
		 
	  
		   
 
	 


		     private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


		     private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };


		     public static char[] encodeHex(byte[] data) {
		         return encodeHex(data, true);
		     }


		     public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		         return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
		     }


		     protected static char[] encodeHex(byte[] data, char[] toDigits) {
		         int l = data.length;
		         char[] out = new char[l << 1];
		         // two characters form the hex value.
		         for (int i = 0, j = 0; i < l; i++) {
		             out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
		             out[j++] = toDigits[0x0F & data[i]];
		         }
		         return out;
		     }

		     public static String encodeHexStr(byte[] data) {
		         return encodeHexStr(data, true);
		     }

		     public static String encodeHexStr(byte[] data, boolean toLowerCase) {
		         return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
		     }

		     protected static String encodeHexStr(byte[] data, char[] toDigits) {
		         return new String(encodeHex(data, toDigits));
		     }

		     public static byte[] decodeHex(char[] data) {
		         int len = data.length;
		         if ((len & 0x01) != 0) {
		             throw new RuntimeException("Odd number of characters.");
		         }
		         byte[] out = new byte[len >> 1];
		         // two characters form the hex value.
		         for (int i = 0, j = 0; j < len; i++) {
		             int f = toDigit(data[j], j) << 4;
		             j++;
		             f = f | toDigit(data[j], j);
		             j++;
		             out[i] = (byte) (f & 0xFF);
		         }
		         return out;
		     }
		     /**
		      *
		      * @param ch
		      * @param inde
		      * @return 
		      * @throws RuntimeException
		      */
		     protected static int toDigit(char ch, int index) {
		         int digit = Character.digit(ch, 16);
		         if (digit == -1) {
		             throw new RuntimeException("Illegal hexadecimal character " + ch
		                     + " at index " + index);
		         }
		         return digit;
		     }


}
