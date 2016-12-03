package com.greenhouse.util;

import java.util.Calendar;

import com.greenhouse.database.ControllerService;
import com.greenhouse.database.JackService;
import com.greenhouse.database.SensorService;
import com.greenhouse.database.StatisticService;
import com.greenhouse.model.Sensor;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.ui.JackFragmentMaster;
import com.greenhouse.ui.JackFragmentShowinfo;
import com.greenhouse.ui.Launcher;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016/2/23 AM 10:18:38 
* @version		1.0  
* @description	
*/
public class MessageHandle {
	private static final String TAG = "MessageHandle";
	
	private static final String TIME = "54494d45";
	private static final String CONT = "434f4e54";
	private static final String STAT = "53544154";
	private static final String SENS = "53454e53";
	private static final String TASK = "5441534b";
	private static final String REMO = "52454d4f";
	private static final String HAVE = "48415645";
	private static final String TACK = "5441434b";
	private static final String BUDD = "42554444";
	private static final String BUND = "42554e44";
	private static final String PROB = "50524f42";
	private static final String DELE = "44454c45";
	private static final String STOP = "53544f50";
	private static final String LOST = "4e4f5354";
	private static final String DANI = "44414e49";
//	private static final String THRE = "54485245";
	private static final String TRES = "54524553";
	private static final String OUTT = "4f555454";
	private static final String INTE = "494e5445";
	
	public static void MessageHandleFromController(final String MESSAGE) {
		// TODO Auto-generated method stub
		
		final String strRegexMsg = MESSAGE.substring(0, 40) + "WANG";
		final String byteRegexMsg = DataFormatConversion.convertHexToString(MESSAGE).substring(0,40) + "WANG";
		Thread newThread;         //声明一个子线程
		
		SocketOutputTask.sendMsgQueue.remove(strRegexMsg);
		SocketOutputTask.sendMsgQueue.remove(byteRegexMsg);
		
		
		
//		if () {
////			Log.d(TAG, "strRegexMsg: " + strRegexMsg);
//		} else if () {
////			Log.d(TAG, "byteRegexMsg: " + byteRegexMsg);
//		} else {
////			Log.d(TAG, "sendMsgQueue remove null");
//		}
		
		final String func_key = MESSAGE.substring(32, 40);		
		final String data;		
		switch (func_key) {			
		case TIME:
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			Log.d(TAG, "[Recv:TIME]" + SocketInputTask.MESSAGE);
			Launcher.recvTIME = true;
			break;
		case STOP:
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			JackFragmentMaster.MCU_IS_STOP = true;
			Log.d(TAG, "[Recv:STOP]" + SocketInputTask.MESSAGE);
//			mainHandler.sendEmptyMessage(Const.SOCKET_DISCONNECTED);//弹出“连接失败提示框”
//			mainHandler.removeMessages(Const.TIMEOUT);//取消弹出“连接超时提示框”
			break;
		case CONT:
			//1.解析并截取报文数据段
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			Log.d(TAG, "[Recv:CONT]" + SocketInputTask.MESSAGE);
			data = SocketInputTask.MESSAGE.substring(20, 40);
			//3.本地保存并更新界面
			setSwitchState(data);
			break;
		case SENS:	
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			Log.d(TAG, "[Recv:SENS]" + SocketInputTask.MESSAGE);
			data = SocketInputTask.MESSAGE.substring(20, 40);
			MessageHandle.setSensorCurrentValue(data);
			break;
		case REMO://传感器解除绑定，bund＝0，传感器值仍然随着报文实时刷新
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			Log.d(TAG, "[Recv:REMO]" + SocketInputTask.MESSAGE);
			data = SocketInputTask.MESSAGE.substring(20, 40);
			setRemoSensor(data);
			break;
		case PROB:
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			Log.d(TAG, "[Recv:PROB]" + SocketInputTask.MESSAGE);
			break;
		case LOST://lost报文未测试
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			data = SocketInputTask.MESSAGE.substring(20, 40);
			setSensorLost(data);
			Log.d(TAG, "[Recv:LOST]" + SocketInputTask.MESSAGE);
			break;
		case DANI:
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			Log.d(TAG, "[Recv:DANI]" + SocketInputTask.MESSAGE);
			data = SocketInputTask.MESSAGE.substring(20, 28);
			setControllerDayNight(data);
			break;
		case TRES:
			SocketInputTask.MESSAGE = DataFormatConversion.convertHexToString(MESSAGE);
			Log.d(TAG, "[Recv:TRES]" + SocketInputTask.MESSAGE);
			data = SocketInputTask.MESSAGE.substring(20, 28);
			 setControllerThredshold(data);
			break;
			//-----------------------------------------Hex String----------------------------------------------
		case BUDD://插座传感器门限值，收到BUDD后将传感器任务同步到APP
			Log.d(TAG, "[Recv:BUDD]" + MESSAGE);
			 setJackBundSensorTask(SocketInputTask.MESSAGE.substring(40, 80));
			break;
		case STAT://TIME后收到该报文，1-数据库；2-本地缓存；3-通知刷新界面
			Log.d(TAG, "[Recv:STAT]" + MESSAGE);
			setAllSwitchState(SocketInputTask.MESSAGE.substring(40, 80));
//			if( !SocketOutputTask.sendMsgQueue.contains("CONT")) {
//			}
			break;	
		case HAVE:
			Log.d(TAG, "[Recv:HAVE]" + MESSAGE + "[状态 1-更新缓存 2-更新数据库]");
			 setAllJackTaskMark(SocketInputTask.MESSAGE.substring(40, 80));
			break;
		case TACK://设置定时任务回执，收到回执后清除消息队列中的BUND
			Log.d(TAG, "[Recv:TACK]" + MESSAGE);
			setAllJackTask(SocketInputTask.MESSAGE.substring(40, 80));
			break;
		case DELE:
			Log.d(TAG, "[Recv:DELE]" + MESSAGE);
			deletelJackTask(SocketInputTask.MESSAGE.substring(40, 80));
			break;
			//--------------------------只更新消息队列不做数据同步的回执报文-------------------------------------
		case TASK:
			Log.d(TAG, "[Recv: TASK]" + MESSAGE);
//			setAllJackTask(SocketInputTask.MESSAGE.substring(40, 80));
			break;
		case BUND:
			Log.d(TAG, "[Recv: BUND]" + MESSAGE);
			break;
		case OUTT:
			Log.e(TAG, "[Recv: OUTT]");
			break;
		case INTE:
			Log.e(TAG, "[Recv: INTE]");
			break;
		}
	}

	
	
	/**
	 * CONT OPEN/CLOS 
	 * @param 
	 * @return 
	 */
	private static void setSwitchState(String data) {
		int index1 = 2, offset1 = 4, index2 = 4, offset2 = 8;
		String id_obj = data.substring(index1, offset1);
		final int id = Integer.parseInt(id_obj);
		String state = data.substring(index2, offset2);
		
		if (state.equals("OPEN")) {	
			if (SocketOutputTask.sendMsgQueue.contains("CONT" + data)) {
				SocketOutputTask.sendMsgQueue.remove("CONT" + data);
			}
			JackService jackService = new JackService(GreenHouseApplication.getContext());
			jackService.modifySwitchstate(1, id);
			
		} else if (state.equals("CLOS")) {
			if (SocketOutputTask.sendMsgQueue.contains("CONT" + data)) {
				SocketOutputTask.sendMsgQueue.remove("CONT" + data);
			}
			JackService jackService = new JackService(GreenHouseApplication.getContext());
			jackService.modifySwitchstate(0, id);
		}	
		
		Log.i(TAG, "[JackId " + id + "]");
	}
	
	private static void setRemoSensor(String data) {
		int index1 = 2, offset1 = 4;
		String id_obj = data.substring(index1, offset1);
		final int id = Integer.parseInt(id_obj);
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.deleteSensorTask(id);
	}

	/**
	 * @Title:       setAllSwitchState
	 * @description: TODO 保存开关状态到数据库Jack表
	 * @param        @param hexstring
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 28, 2016, 3:22:02 PM
	 */
	public static void setAllSwitchState(String hexstring) {
		
		String switchstate = DataFormatConversion.HexStringToBinaryString(hexstring);
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifySwitchstate(switchstate);
		
//		if (JackFragmentSwitchTest.jackSwitchInfoList != null) {
//			for(int i = 0; i < 35; i++){
//				Integer state = Integer.parseInt(switchstate.substring(i, i+1));
//				JackFragmentSwitchTest.jackSwitchInfoList.get(i).setSwitchstate(state);
//			}
//			
//			JackFragmentSwitchTest.switchHandler.post(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					Message msg = JackFragmentSwitchTest.switchHandler.obtainMessage();
//					msg.arg1 = Const.UI_SWITCH_TEST;
//					JackFragmentSwitchTest.switchHandler.sendMessage(msg);
//				}
//			});
//		}
		
	}
	
	/**
	 * HAVE
	 */
	public static void setAllJackTaskMark(String hexstring) {
		String taskmark = DataFormatConversion.HexStringToBinaryString(hexstring);
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyJackTaskMark(taskmark);
	}

	/**
	 * @Title:       setJackBundSensorTask
	 * @description: TODO 接收并处理BUND报文，保存并刷新界面
	 * @param        @param data
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 21, 2016, 9:53:54 AM
	 */
	private static void setJackBundSensorTask(String data) {
		
		String hexJackId = data.substring(0,2);              //插座编号十六进制字符
		String hexSensorAndDeviceType = data.substring(2,4); //传感器和设备类型
		String hexDayThre1 = data.substring(4,6);            //早门限1
		String hexNightThre1 = data.substring(6,8);          //晚门限1
		String hexDayThre2 = data.substring(8,10);           //早门限2
		String hexNightThre2 = data.substring(10,12);        //晚门限2
		String hexDayThre3 = data.substring(12,14);          //早门限3
		String hexNightThre3 = data.substring(14,16);        //晚门限3
		String hexDayThre4 = data.substring(16,18);          //早门限4
		String hexNightThre4 = data.substring(18,20);        //晚门限4
		String hexDayThre5 = data.substring(20,22);          //早门限5
		String hexNightThre5 = data.substring(22,24);        //晚门限5
		String hexDayThre6 = data.substring(24,27);          //早门限6
		String hexNightThre6 = data.substring(27,30);        //晚门限6
		String hexDayThre7 = data.substring(30,34);          //早门限7
		String hexNightThre7 = data.substring(34,38);        //晚门限7
		String hexBundSensor = data.substring(38,40);        //绑定传感器
		
		Integer jackId = DataFormatConversion.HexStr2ToJackId(hexJackId);                                        //十六转十,调用了自定义方法,和Java API一样
		String binSensorAndDeviceType = DataFormatConversion.HexStringToBinaryString(hexSensorAndDeviceType);   //十六进制转二进制后,七位是传感器类型,一位是设备类型
//		Integer sensorType = DataFormatConversion.SensorTypeConversion(binSensorAndDeviceType.substring(0, 7)); //传感器类型八位二进制转1-7
		Integer deviceType = Integer.parseInt(binSensorAndDeviceType.substring(7));                             //设备类型/绑定类型
		String binBundSensor = DataFormatConversion.HexStringToBinaryString(hexBundSensor);                     //绑定传感器
		Integer bundType1 = Integer.parseInt(binSensorAndDeviceType.substring(0, 1));                           //是否绑定了传感器类型1-土壤温度
		Integer dayThre1 = Integer.parseInt(hexDayThre1,16);											        //白天门限1
		Integer nightThre1 = Integer.parseInt(hexNightThre1,16);										        //夜间门限1
		Integer bundType2 = Integer.parseInt(binSensorAndDeviceType.substring(1, 2));                           //是否绑定了传感器类型2－土壤湿度
		Integer dayThre2 = Integer.parseInt(hexDayThre2,16);								      				//白天门限2
		Integer nightThre2 = Integer.parseInt(hexNightThre2,16); 											    //夜间门限2
		Integer bundType3 = Integer.parseInt(binSensorAndDeviceType.substring(2, 3));							//是否绑定了传感器类型3-土壤酸碱度
		Integer dayThre3 = Integer.parseInt(hexDayThre3,16);											        //夜间门限3
		Integer nightThre3 = Integer.parseInt(hexNightThre3,16);											    //夜间门限3
		Integer bundType4 = Integer.parseInt(binSensorAndDeviceType.substring(3, 4));							//是否绑定了传感器类型4-空气温度
		Integer dayThre4 = Integer.parseInt(hexDayThre4,16);											        //夜间门限4
		Integer nightThre4 = Integer.parseInt(hexNightThre4,16);											    //夜间门限4
		Integer bundType5 = Integer.parseInt(binSensorAndDeviceType.substring(4, 5));							//是否绑定了传感器类型5-空气湿度
		Integer dayThre5 = Integer.parseInt(hexDayThre5,16);											        //夜间门限5
		Integer nightThre5 = Integer.parseInt(hexNightThre5,16);											    //夜间门限5
		Integer bundType6 = Integer.parseInt(binSensorAndDeviceType.substring(5, 6));							//是否绑定了传感器类型6-CO2浓度
		Integer dayThre6 = Integer.parseInt(hexDayThre6,16);											        //夜间门限6
		Integer nightThre6 = Integer.parseInt(hexNightThre6,16);											    //夜间门限6
		Integer bundType7 = Integer.parseInt(binSensorAndDeviceType.substring(6, 7));							//是否绑定了传感器类型7-光照度
		Integer dayThre7 = Integer.parseInt(hexDayThre7,16);											        //夜间门限7
		Integer nightThre7 = Integer.parseInt(hexNightThre7,16);											    //夜间门限7
		
		Log.v(TAG, "BUDD解析: "+"插座id＝"+jackId + ", 绑定传感器＝"+binBundSensor+", 设备类型＝"+deviceType);
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.deleteOneJackTask(jackId+"");
		jackService.modifyAllTypeSensorTask(jackId, binBundSensor, deviceType,
				bundType1, dayThre1, nightThre1, 
				bundType2, dayThre2, nightThre2, 
				bundType3, dayThre3, nightThre3,
				bundType4, dayThre4, nightThre4, 
				bundType5, dayThre5, nightThre5, 
				bundType6, dayThre6, nightThre6, 
				bundType7, dayThre7, nightThre7);
		
		Log.e(TAG, "空气温度白天："+dayThre4+",空气温度夜间："+nightThre4);
		
	}
	

	public static void setJackSensorRestriction(String data) {
		final Integer jackId = Integer.parseInt(data.substring(0, 2)) - 1;
		String sensors = DataFormatConversion.IntSensorToBinSensor(data.substring(2, 4));
		Integer sensortype = Integer.parseInt(data.substring(7, 8));
		Integer day = Integer.parseInt(data.substring(8, 12));
		Integer night = Integer.parseInt(data.substring(12, 16));
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyJackSensorRestriction((jackId + 1), sensors, day, night, sensortype);
		Log.i(TAG, "setJackSensroRestriction: " + (jackId+1) + "-" + sensors + "-" + sensortype + "-" + day + "-" + night);
	}
	
	

	/**
	 * @Title:       setSensorCurrentValue
	 * @description: TODO 传感器实时值保存到数据库(1)传感器表(2)统计查询表(3)Jack表和JackFragmentShowInfo的实时显示
	 * @param        @param data
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 21, 2016, 5:28:37 PM
	 */
	public static void setSensorCurrentValue(String data) {
		
		SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		
		Integer sensorid = Integer.parseInt(data.substring(0, 2));
		Integer soiltemp = Integer.parseInt(data.substring(2, 4));
		Integer soilhum = Integer.parseInt(data.substring(4, 6));
		Integer soilph = Integer.parseInt(data.substring(6, 8));
		Integer airtemp = Integer.parseInt(data.substring(8, 10));
		Integer airhum = Integer.parseInt(data.substring(10, 12));
		Integer co2 = Integer.parseInt(data.substring(12, 16));
		Integer illu = Integer.parseInt(data.substring(16, 20));
		
		//每收到sensorid＝1的实时值，就将所有传感器设置为offline
//		if (sensorid == 1) {
//			sensorService.modifyAllSensorOffline();  
//		}
		
		if (data.substring(2, 20).equals("000000000000000000")) {
			sensorService.modifySensorCurrentValue(sensorid, 0, soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
		} else {
			sensorService.modifySensorCurrentValue(sensorid, 1, soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
			//将传感器实时值[在每天的8，12，17，19点的四个时间点]保存到统计查询表
			setSensorCurrentValueToStatistic(soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
		}

		if (JackFragmentShowinfo.jackInfoList.size() > 0) {
			for (int i = 0; i < 35; i++) {
				String bundSensors = JackFragmentShowinfo.jackInfoList.get(i).getSensors();
				int[] jackCurrValue = sensorService.getIntSelectSensorAverage(bundSensors);
				Log.v(TAG, "第"+i+"列插座上绑定的传感器："+JackFragmentShowinfo.jackInfoList.get(i).getSensors());
				Log.v(TAG, "[(数据库同步)Sensor实时值]"+jackCurrValue[0]+jackCurrValue[1]+jackCurrValue[2]+jackCurrValue[3]
						+jackCurrValue[4]+jackCurrValue[5]+jackCurrValue[6]);
				
				if (!bundSensors.equals("00000000")) {
					jackService.modifyJackCurrentValue((i+1), jackCurrValue);
				}
			}
		}
			
			
		Log.i(TAG, "sensor" + sensorid + "-" + soiltemp + "-" + soilhum + "-" + soilph + "-" + airtemp + "-" + airhum + "-" + co2 + "-" + illu);
	}
	
	/**
	 * @Title:       setSensorCurrentValueToStatistic
	 * @description: TODO 将传感器实时值每小时保存一次到统计查询表
	 * @param        
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 21, 2016, 7:23:08 PM
	 */
	private static void setSensorCurrentValueToStatistic(Integer soiltemp, Integer soilhum, Integer soilph,
			Integer airtemp, Integer airhum, Integer co2, Integer illu) {
		Calendar c = Calendar.getInstance();
		Integer hour = Integer.valueOf(c.get(Calendar.HOUR_OF_DAY));
		Integer minute = Integer.valueOf(c.get(Calendar.MINUTE));
		Integer year = Integer.valueOf(c.get(Calendar.YEAR));
		Integer month = Integer.valueOf(c.get(Calendar.MONTH));
		Integer day = Integer.valueOf(c.get(Calendar.DAY_OF_MONTH));
		
		StatisticService statisticService = new StatisticService(GreenHouseApplication.getContext());
//		statisticService.insertRecord(Launcher.selectMac , year, month, day, hour, soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
//		Log.d(TAG, "(SQLite TEST) Statistic: " + year+"/"+month+"/"+day+" " + hour + ":" + minute + ":" + seconed);
		
		
		if (!statisticService.isCurrentDataSaved(year, month, day, hour)) {
			statisticService.insertRecord(Launcher.selectMac , year, month, day, hour, minute, soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
			Log.d(TAG, "[(数据库同步)统计查询]" + year+"/"+month+"/"+day+" " + hour + ":" + minute);
		} 
		
	}
	
	/**
å	 * @Title:       CalculateJackBundSensorsCurrentValue
	 * @description: TODO 计算插座上绑定的多个传感器和绑定类型的平均值，传入参数：绑定插座10110000，返回类型int[7]
	 * @param        @param sensors
	 * @param        @param sensortype
	 * @param        @return
	 * @return       int[]
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 24, 2016, 4:10:28 PM
	 */
	public static int[] CalculateJackBundSensorsCurrentValue(String sensors, Integer sensortype) {
		SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
		Sensor sensorAverage = sensorService.getSelectSensorAverage(sensors);
		int cv1, cv2, cv3, cv4, cv5, cv6, cv7;
			cv1 = sensorAverage.getSoiltemp();
			cv2 = sensorAverage.getSoilhum();
			cv3 = sensorAverage.getSoilph();
			cv4 = sensorAverage.getAirtemp();
			cv5 = sensorAverage.getAirhum();
			cv6 = sensorAverage.getCo2();
			cv7 = sensorAverage.getIllumination();
		int currentvalue[] = {cv1, cv2, cv3, cv4, cv5, cv6, cv7};;
		return currentvalue;
	}

	public static void setAllJackTask(String jacktasks) {
		
		String year = Integer.valueOf(jacktasks.substring(0, 4), 16).toString();
		String month = Integer.valueOf(jacktasks.substring(4, 6), 16).toString();
		String day = Integer.valueOf(jacktasks.substring(6, 8), 16).toString();
		String hour = DataFormatConversion.JackTaskDataFormatConversion(jacktasks.substring(8, 10));
		String minute = DataFormatConversion.JackTaskDataFormatConversion(jacktasks.substring(10, 12));
		String on_hour = DataFormatConversion.JackTaskDataFormatConversion(jacktasks.substring(12, 14));
		String on_minute = DataFormatConversion.JackTaskDataFormatConversion(jacktasks.substring(14, 16));
		String off_hour = DataFormatConversion.JackTaskDataFormatConversion(jacktasks.substring(16, 18));
		String off_minute = DataFormatConversion.JackTaskDataFormatConversion(jacktasks.substring(18, 20));
		Integer cycle = Integer.valueOf(jacktasks.substring(22, 24), 16);
		Integer jackId = DataFormatConversion.HexStrBit12ToJackId(jacktasks.substring(24, 36));
		
		Log.i(TAG, "[(数据库同步)定时任务]" + year + "-" + month + "-" + day + " 开始:" + hour + ":" + minute 
				+ " 打开:" + on_hour + ":" + on_minute + " 关闭:" + off_hour + ":" + off_minute + " 循环:" 
				+ cycle + "次 插座" + jackId);
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyJackTask(year + "-" + month + "-" + day + " " + hour + ":" + minute, 
				on_hour + ":" + on_minute, off_hour + ":" + off_minute, cycle, jackId+"");
	}
	
	
	public static void setSensorLost(String data) {
		Integer sensorid = Integer.valueOf(data.substring(3, 4));
		SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
		sensorService.modifySensorCurrentValue(sensorid, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public static void deletelJackTask(String jacktasks) {
		
		String jackIds = DataFormatConversion.HexStringToBinaryString(jacktasks.substring(0, 12));
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.deleteJackTask(jackIds);
		
	}
	
	
	public static void setControllerDayNight(String timeset) {
		Integer day = Integer.valueOf(timeset.substring(0, 2));
		Integer night = Integer.valueOf(timeset.substring(4, 6));
		ControllerService controllerService = new ControllerService(GreenHouseApplication.getContext());
		controllerService.modifyDayNight(day, night);
	}
	
	public static void setControllerThredshold(String timeset) {
		Integer type = Integer.valueOf(timeset.substring(0, 2));
		Integer thre = Integer.valueOf(timeset.substring(2, 6));
		ControllerService controllerService = new ControllerService(GreenHouseApplication.getContext());
		
		switch (type) {
		case 1:
			controllerService.modifyThredshold1(thre);
			break;
		case 2:
			controllerService.modifyThredshold2(thre);
			break;
		case 3:
			controllerService.modifyThredshold3(thre);
			break;
		case 4:
			controllerService.modifyThredshold4(thre);
			break;
		case 5:
			controllerService.modifyThredshold5(thre);
			break;
		case 6:
			controllerService.modifyThredshold6(thre);
			break;
		case 7:
			controllerService.modifyThredshold7(thre);
			break;
			default:
				break;
				
		}
		
	}
	



	
}
