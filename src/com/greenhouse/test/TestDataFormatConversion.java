package com.greenhouse.test;

import java.util.ArrayList;
import java.util.List;

import com.greenhouse.networkservice.CommProtocol;
import com.greenhouse.ui.JackFragmentModeSet;
import com.greenhouse.ui.SensorRecyclerView;
import com.greenhouse.ui.SensorSetting;
import com.greenhouse.util.Const;
import com.greenhouse.util.DataFormatConversion;
import android.test.AndroidTestCase;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			Aug 19, 20168:36:21 PM 
* @version		1.0  
* @description			 
*/
public class TestDataFormatConversion extends AndroidTestCase{
	
	private static final String TAG = "TestDataFormatConversion.java";
	
	public void testIntegerJackToHexString() throws Throwable {
		for (int i=0; i<Const.JACK_SUM; i++) {
			DataFormatConversion.IntegerJackToHexString(i+1);
		}
	}
	
	public void testHexStringToByte() throws Throwable {
		DataFormatConversion.HexStringToByte("101");
		DataFormatConversion.HexStringToByte("00000101");
	}
	
//	public void testBinaryStringToHexString() throws Throwable {
////		DataFormatConversion.BinaryStringToHexString("00010001");   //普通用例|PASS 
//	}
	
	public void testBinaryStringToHexString2() throws Throwable {
		DataFormatConversion.BinaryStringToHexString2("00010001");  //普通用例|PASS
		DataFormatConversion.BinaryStringToHexString2("000100010"); //长度错误用例|PASS
		DataFormatConversion.BinaryStringToHexString2("00011111");  //普通用例|PASS
	}
	
	public void testBinaryStringToHexString() throws Throwable {
		DataFormatConversion.BinStr48ToHexStr12("000100010001000100010001000100010001000100010001");  //普通用例|PASS
		DataFormatConversion.BinStr48ToHexStr12("111100001111000011110000111100001111000011110000");  //长度错误用例|PASS
		DataFormatConversion.BinStr48ToHexStr12("111111110000000000000000000000000000000000000000");  //普通用例|PASS
		DataFormatConversion.BinStr48ToHexStr12("111011100000000000000000000000000000000000000000");  //普通用例|PASS
		DataFormatConversion.BinStr48ToHexStr12("000010000000000000000000000000000000000000000000"); //极值用例|PASS
	}
	
	public void testJavaFormatConversionAPI() throws Throwable {
		Log.i(TAG, Integer.toHexString(1));   //普通用例|PASS
		Log.i(TAG, Integer.toHexString(5));   //普通用例|PASS
		Log.i(TAG, Integer.toHexString(10));  //普通用例|PASS
		Log.i(TAG, Integer.toHexString(11));  //普通用例|PASS
		Log.i(TAG, Integer.toHexString(12));  //普通用例|PASS
		Log.i(TAG, Integer.toHexString(15));  //普通用例|PASS
		Log.i(TAG, Integer.toHexString(16));  //普通用例|PASS
		Log.i(TAG, Integer.toHexString(17));  //普通用例|PASS
		Log.i(TAG, Integer.toHexString(48));  //普通用例|PASS
		
		Log.i(TAG, Integer.toHexString(Integer.parseInt("28"))); //温度用例1|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("26"))); //温度用例2|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("82"))); //湿度用例1|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("70"))); //温度用例2|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("300")));   //CO2用例1|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("500")));   //CO2用例2|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("1500")));  //CO2用例3|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("3000")));  //光照度用例1|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("8000")));  //光照度用例2|PASS
		Log.i(TAG, Integer.toHexString(Integer.parseInt("12000"))); //光照度用例2|PASS
	}
	
	public void testIntSensorAndDeviceTypeToHexstring() throws Throwable {
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(0,0); //错误用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(1,0); //普通用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(2,1); //普通用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(3,0); //普通用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(4,1); //普通用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(5,0); //普通用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(6,1); //普通用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(7,0); //普通用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(8,1); //错误用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(9,1); //错误用例|PASS
		DataFormatConversion.IntSensorAndDeviceTypeToHexstring(10,1);//错误用例|PASS
	}
	
	public void testMultiSensAndDevTypeToHexStr() throws Throwable {
		int[] chosedSensor1 = {1,1,1,1,1,1,1};
		int[] chosedSensor2 = {1,0,0,0,0,0,0};
		int[] chosedSensor3 = {1,1,0,0,0,0,0};
		int[] chosedSensor4 = {0,0,0,1,0,0,0};
		DataFormatConversion.MultiSensAndDevTypeToHexStr(chosedSensor1, 1);
		DataFormatConversion.MultiSensAndDevTypeToHexStr(chosedSensor2, 1);
		DataFormatConversion.MultiSensAndDevTypeToHexStr(chosedSensor3, 1);
		DataFormatConversion.MultiSensAndDevTypeToHexStr(chosedSensor4, 1);
	}
	
	public void testListSelectSensorToHexString() throws Throwable {
		List<String> testList1= new ArrayList<String>(); //普通用例|PASS
		testList1.add("1");
		testList1.add("2");
		testList1.add("4");
		testList1.add("7");
		testList1.add("8");
		DataFormatConversion.ListSelectSensorToHexString(testList1);
	}
	
	public void testListSelectSensorToBinString() throws Throwable {
		List<String> testList1= new ArrayList<String>(); //普通用例|PASS
		testList1.add("1");
		testList1.add("2");
		testList1.add("4");
		testList1.add("7");
		testList1.add("8");
		DataFormatConversion.ListSelectSensorToBinString(testList1);
	}
	
	public void testIrreguStringToHexString() throws Throwable {
		DataFormatConversion.IrreguStringToHexValue("HFUT");     //普通用例|PASS 48465554
		DataFormatConversion.IrreguStringToHexValue("WANG");     //普通用例|PASS
		DataFormatConversion.IrreguStringToHexValue("WANA");     //普通用例|PASS
		DataFormatConversion.IrreguStringToHexValue("ACCF232");  //普通用例|PASS
		DataFormatConversion.IrreguStringToHexValue("00001010"); //普通用例|PASS 3030303031303130
		DataFormatConversion.IrreguStringToHexValue("00001111"); //普通用例|PASS
	}
	
	public void testSuite() throws Throwable {
		testIrreguStringToHexString();
		testJavaFormatConversionAPI();
	}
	
//	public void testSuite2() throws Throwable {
//		JackFragmentModeSet.sJackId = 1;                  //1
//		SensorSetting.sSensorType = 1;
//		SensorSetting.sDeviceType = 0;					  //80
//		SensorRecyclerView.sBinSelectSensor = "10100000"; //a0
//		SensorSetting.sSetDayThre = "28";                 //1c
//		SensorSetting.sSetNightThre = "26";				  //1a
//		CommProtocol.sendMessageBUND();                   //普通用例|PASS
//		
//		JackFragmentModeSet.sJackId = 15;                 //23
//		SensorSetting.sSensorType = 7;
//		SensorSetting.sDeviceType = 1;					  //3
//		SensorRecyclerView.sBinSelectSensor = "11111111"; //e0
//		SensorSetting.sSetDayThre = "12000";              //2ee0
//		SensorSetting.sSetNightThre = "8000";			  //1f40
//		CommProtocol.sendMessageBUND();                   //极值用例|PASS
//		
//		JackFragmentModeSet.sJackId = 35;                 //23
//		SensorSetting.sSensorType = 6;
//		SensorSetting.sDeviceType = 1;					  //3
//		SensorRecyclerView.sBinSelectSensor = "11100000"; //e0
//		SensorSetting.sSetDayThre = "3000";              //2ee0
//		SensorSetting.sSetNightThre = "500";			  //1f40
//		CommProtocol.sendMessageBUND();                   //极值用例|PASS
//	}
	
	public void testHexStringToBinaryString() throws Throwable {
		DataFormatConversion.HexStringToBinaryString("01"); //普通用例|PASS
		DataFormatConversion.HexStringToBinaryString("23"); //普通用例|PASS
		DataFormatConversion.HexStringToBinaryString("0f"); //普通用例|PASS
		DataFormatConversion.HexStringToBinaryString("080000000000"); //普通用例|PASS
	}
	
	public void testHexStringToInt() throws Throwable {
		DataFormatConversion.HexStrBit12ToJackId("800000000000");
		DataFormatConversion.HexStrBit12ToJackId("400000000000");
		DataFormatConversion.HexStrBit12ToJackId("200000000000");
		DataFormatConversion.HexStrBit12ToJackId("100000000000");
		DataFormatConversion.HexStrBit12ToJackId("080000000000");
		DataFormatConversion.HexStrBit12ToJackId("040000000000");
		DataFormatConversion.HexStrBit12ToJackId("020000000000");
		DataFormatConversion.HexStrBit12ToJackId("010000000000");
//		DataFormatConversion.HexStringToInt("000080000000");
//		DataFormatConversion.HexStringToInt("000040000000");
//		DataFormatConversion.HexStringToInt("000020000000");
//		DataFormatConversion.HexStringToInt("000010000000");
	}
	
	
	public void testInt2String() throws Throwable {
		int[] test = {0,0,0,0,0,0,1,0,1};
		String test_str = DataFormatConversion.Int2String(test);
//		DataFormatConversion.BinaryStringToHexString(test_str);
	}
	
	public void testHexAndBinStringToInt() throws Throwable {
		Log.i(TAG, "0x23=" + DataFormatConversion.HexStr2ToJackId("23"));
		Log.i(TAG, "0x0f=" + DataFormatConversion.HexStr2ToJackId("0f"));
		Log.i(TAG, "00100011=" + DataFormatConversion.BinStringToInt("00100011"));
		Log.i(TAG, "00100011=" + DataFormatConversion.BinStringToInt("00100011"));
	}
	
	public void testFormatStringByAddZero() throws Throwable {
		DataFormatConversion.FormatStringByAddZero("1", 2);
		DataFormatConversion.FormatStringByAddZero("1a", 2);
		DataFormatConversion.FormatStringByAddZero("1a", 3);
		DataFormatConversion.FormatStringByAddZero("1f40", 4);
		DataFormatConversion.FormatStringByAddZero("1f40", 6);
		DataFormatConversion.FormatStringByAddZero("1f40", 8);
	}
	
	public void testFormatAllThreByAddZero()throws Throwable {
		DataFormatConversion.FormatAllThreByAddZero("1234", 1);
		DataFormatConversion.FormatAllThreByAddZero("1234", 2);
		DataFormatConversion.FormatAllThreByAddZero("1234", 3);
		DataFormatConversion.FormatAllThreByAddZero("1234", 4);
		DataFormatConversion.FormatAllThreByAddZero("1234", 5);
		DataFormatConversion.FormatAllThreByAddZero("123456", 6);
		DataFormatConversion.FormatAllThreByAddZero("12345678", 7);
	}
	
	
}
