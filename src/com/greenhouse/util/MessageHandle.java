package com.greenhouse.util;

import java.util.Calendar;

import com.greenhouse.database.ControllerService;
import com.greenhouse.database.JackService;
import com.greenhouse.database.SensorService;
import com.greenhouse.database.StatisticService;
import com.greenhouse.model.Sensor;
import com.greenhouse.mvadpater.GridItemSwitchTestAdapter;
import com.greenhouse.mvadpater.JackRecyclerViewShowinfoAdapter;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.ui.AlarmclockListView;
import com.greenhouse.ui.JackFragmentEnvironment;
import com.greenhouse.ui.JackFragmentMaster;
import com.greenhouse.ui.JackFragmentShowinfo;
import com.greenhouse.ui.JackFragmentSwitchTest;
import com.greenhouse.ui.JackFragmentTimeSet;
import com.greenhouse.ui.Launcher;
import com.greenhouse.ui.SensorRecyclerView;
import com.greenhouse.ui.SensorSetting;
import com.greenhouse.ui.Timer;

import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016/2/23 AM 10:18:38 
* @version		1.0  
* @description	
*/
public class MessageHandle {
	
	private static final String TAG = "MessageHandle.java";
	
	/**
	 * CONT OPEN/CLOS ���Ĵ���
	 * @param 
	 * @return 
	 */
	public static void setSwitchState(String data) {
		int index1 = 2, offset1 = 4, index2 = 4, offset2 = 8;
		String id_obj = data.substring(index1, offset1);
		final int id = Integer.parseInt(id_obj);
		String state = data.substring(index2, offset2);
		
		if (state.equals("OPEN")) {	
			if (GridItemSwitchTestAdapter.sendMsgQueue.contains("CONT" + data)) {
				GridItemSwitchTestAdapter.sendMsgQueue.remove("CONT" + data);
			}
			JackFragmentSwitchTest.jacks.get(id - 1).setSwitchstate(1);
			
			JackService jackService = new JackService(GreenHouseApplication.getContext());
			jackService.modifySwitchstate(1, id);
			
		} else if (state.equals("CLOS")) {
			if (GridItemSwitchTestAdapter.sendMsgQueue.contains("CONT" + data)) {
				GridItemSwitchTestAdapter.sendMsgQueue.remove("CONT" + data);
			}
			
			JackFragmentSwitchTest.jacks.get(id - 1).setSwitchstate(0);
			JackService jackService = new JackService(GreenHouseApplication.getContext());
			jackService.modifySwitchstate(0, id);
		}	
		
		if (JackFragmentSwitchTest.handler != null) {
			JackFragmentSwitchTest.handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = JackFragmentSwitchTest.handler.obtainMessage();
					msg.arg1 = id - 1;
					JackFragmentSwitchTest.handler.sendMessage(msg);
				}
			});
		}
		
		Log.i(TAG, "[JackId " + id + "]");
	}
	
	// REMO 
	public static void setRemoSensor(String data) {
		int index1 = 2, offset1 = 4;
		String id_obj = data.substring(index1, offset1);
		final int id = Integer.parseInt(id_obj);
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.deleteSensorTask(id);
		if (JackFragmentShowinfo.jacks != null) {
			JackFragmentShowinfo.jacks.get(id - 1).setBund(0);
			JackFragmentShowinfo.handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = JackFragmentShowinfo.handler.obtainMessage();
					msg.arg1 = id - 1;
					JackFragmentShowinfo.handler.sendMessage(msg);
				}
			});
		}
		Log.i(TAG, "[REMO]");
	}

	public static void setAllSwitchState(String hexstring) {
		
		String switchstate = DataFormatConversion.HexStringToBinaryString(hexstring);
		
		if (JackFragmentSwitchTest.jacks != null) {
			for(int i = 0; i < 35; i++){
				Integer state = Integer.parseInt(switchstate.substring(i, i+1));
				JackFragmentSwitchTest.jacks.get(i).setSwitchstate(state);
			}
			
			JackFragmentSwitchTest.handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = JackFragmentSwitchTest.handler.obtainMessage();
					msg.arg1 = Const.UI_REFRESH;
					JackFragmentSwitchTest.handler.sendMessage(msg);
				}
			});
		}
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifySwitchstate(switchstate);
	}
	
	/**
	 * HAVE
	 */
	public static void setAllJackTaskMark(String hexstring) {
		
		String taskmark = DataFormatConversion.HexStringToBinaryString(hexstring);
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyJackTaskMark(taskmark);
		
		if (JackFragmentShowinfo.jacks != null) {
			
			for(int i = 0; i < Const.JACK_SUM; i++){
				Integer mark = Integer.parseInt(taskmark.substring(i, i+1));
				if(mark == 1){
				} else {
					JackFragmentShowinfo.jacks.get(i).setBund(0);
				}
			}
			
			JackFragmentShowinfo.handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = JackFragmentShowinfo.handler.obtainMessage();
					msg.arg1 = Const.UI_REFRESH;
					JackFragmentShowinfo.handler.sendMessage(msg);
				}
			});
			
		}
		
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
	public static void setJackBundSensorTask(String data) {
		
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
		
		Integer jackId = DataFormatConversion.HexStringToInt(hexJackId);                                        //十六转十,调用了自定义方法,和Java API一样
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
		
		if (JackFragmentShowinfo.jacks != null) {
			JackFragmentShowinfo.jacks.get(jackId-1).setBund(1);                //绑定标志位置1
//			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype1(deviceType);  //绑定类型/设备类型0or1
			JackFragmentShowinfo.jacks.get(jackId-1).setSensors(binBundSensor); //绑定传感器00110000
			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype1(bundType1);
			JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold1(dayThre1);
			JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold1(nightThre1);
			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype2(bundType2);
			JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold2(dayThre2);
			JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold2(nightThre2);
			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype3(bundType3);
			JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold3(dayThre3);
			JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold3(nightThre3);
			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype4(bundType4);
			JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold4(dayThre4);
			JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold4(nightThre4);
			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype5(bundType5);
			JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold5(dayThre5);
			JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold5(nightThre5);
			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype6(bundType6);
			JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold6(dayThre6);
			JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold6(nightThre6);
			JackFragmentShowinfo.jacks.get(jackId-1).setbundtype7(bundType7);
			JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold7(dayThre7);
			JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold7(nightThre7);
		}
		
		//好像没用？？？？
		JackFragmentShowinfo.handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JackFragmentShowinfo.handler.sendEmptyMessage(Const.UI_REFRESH);
			}
		});
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyAllTypeSensorTask(jackId, binBundSensor, deviceType,
				bundType1, dayThre1, nightThre1, 
				bundType2, dayThre2, nightThre2, 
				bundType3, dayThre3, nightThre3,
				bundType4, dayThre4, nightThre4, 
				bundType5, dayThre5, nightThre5, 
				bundType6, dayThre6,nightThre6, 
				bundType7, dayThre7, nightThre7);
	}
	

	public static void setJackSensorRestriction(String data) {
		
		final Integer jackId = Integer.parseInt(data.substring(0, 2)) - 1;
		String sensors = DataFormatConversion.IntSensorToBinSensor(data.substring(2, 4));
		Integer sensortype = Integer.parseInt(data.substring(7, 8));
		Integer day = Integer.parseInt(data.substring(8, 12));
		Integer night = Integer.parseInt(data.substring(12, 16));
		
		if (JackFragmentShowinfo.jacks != null) {
			JackFragmentShowinfo.jacks.get(jackId).setBund(1);
			JackFragmentShowinfo.jacks.get(jackId).setSensortype(sensortype);
			
			switch(sensortype) {
			case 1:
				JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold1(day);
				JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold1(night);
				break;
			case 2:
				JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold2(day);
				JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold2(night);
				break;
			case 3:
				JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold3(day);
				JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold3(night);
				break;
			case 4:
				JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold4(day);
				JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold4(night);
				break;
			case 5:
				JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold5(day);
				JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold5(night);
				break;
			case 6:
				JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold6(day);
				JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold6(night);
				break;
			case 7:
				JackFragmentShowinfo.jacks.get(jackId-1).setDay_threshold7(day);
				JackFragmentShowinfo.jacks.get(jackId-1).setNight_threshold7(night);
				break;
				default:
					break;
			}
			
			
			JackFragmentShowinfo.handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = JackFragmentShowinfo.handler.obtainMessage();
					msg.arg1 = jackId;
					JackFragmentShowinfo.handler.sendMessage(msg);
				}
			});
		}
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyJackSensorRestriction((jackId + 1), sensors, day, night, sensortype);
		
		
		Log.i(TAG, "[BUDD]" + (jackId+1) + "-" + sensors + "-" + sensortype + "-" + day + "-" + night);
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
		
		Integer sensorid = Integer.parseInt(data.substring(0, 2));
		Integer soiltemp = Integer.parseInt(data.substring(2, 4));
		Integer soilhum = Integer.parseInt(data.substring(4, 6));
		Integer soilph = Integer.parseInt(data.substring(6, 8));
		Integer airtemp = Integer.parseInt(data.substring(8, 10));
		Integer airhum = Integer.parseInt(data.substring(10, 12));
		Integer co2 = Integer.parseInt(data.substring(12, 16));
		Integer illu = Integer.parseInt(data.substring(16, 20));
		
		//每收到sensorid＝1的实时值，就将所有传感器设置为offline
		if (sensorid == 1) {
			sensorService.modifyAllSensorOffline();  
		}
		
		if (data.substring(2, 20).equals("000000000000000000")) {
			sensorService.modifySensorCurrentValue(sensorid, 0, soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
		} else {
			sensorService.modifySensorCurrentValue(sensorid, 1, soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
		}
			
		//未完成，需要实现的功能：通知对应插座，更新传感器实时平均值
		JackService jackService = new JackService(GreenHouseApplication.getContext());
			
		if (JackFragmentShowinfo.jacks != null) {				
			for (int i = 0; i < Const.JACK_SUM; i++) {
				if (JackFragmentShowinfo.jacks.get(i).getBund() == 1) {
					String sensors = JackFragmentShowinfo.jacks.get(i).getSensors();//��ð󶨵�sensors��id��010001000
					Integer sensortype = JackFragmentShowinfo.jacks.get(i).getSensortype();//��ð󶨵�sensors�����ͣ�0��1��2��3������
					int currentvalue[] = CalculateJackBundSensorsCurrentValue(sensors, sensortype);//��������ϰ󶨵�sensors��ʵʱƽ��ֵ������ȡָ����Ȩƽ��
					JackFragmentShowinfo.jacks.get(i).setCurrentValue1(currentvalue[0]);
					JackFragmentShowinfo.jacks.get(i).setCurrentValue2(currentvalue[1]);
					JackFragmentShowinfo.jacks.get(i).setCurrentValue3(currentvalue[2]);
					JackFragmentShowinfo.jacks.get(i).setCurrentValue4(currentvalue[3]);
					JackFragmentShowinfo.jacks.get(i).setCurrentValue5(currentvalue[4]);
					JackFragmentShowinfo.jacks.get(i).setCurrentValue6(currentvalue[5]);
					JackFragmentShowinfo.jacks.get(i).setCurrentValue7(currentvalue[6]);
					//更新指定插座id=i的currentValue1-7
					jackService.modifyJackCurrentValue(i, currentvalue);
				}
			}
				
		}
		
		//刷新环境参数界面
		if (JackFragmentEnvironment.handler != null) {
			JackFragmentEnvironment.handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					JackFragmentEnvironment.handler.sendEmptyMessage(Const.UI_REFRESH);
				}
			});
		}
		
	   //将传感器实时值[在每天的8，12，17，19点的四个时间点]保存到统计查询表
	   setSensorCurrentValueToStatistic(soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
			
		Log.i(TAG, "Sensor" + sensorid + "-" + soiltemp + "-" + soilhum + "-" + soilph + "-" + airtemp + "-" + airhum + "-" + co2 + "-" + illu);
	}
	
	/**
	 * @Title:       setSensorCurrentValueToStatistic
	 * @description: TODO 将传感器实时值[在每天的8，12，17，19点的四个时间点]保存到统计查询表
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
		Integer year = Integer.valueOf(c.get(Calendar.YEAR));
		Integer month = Integer.valueOf(c.get(Calendar.MONTH));
		Integer day = Integer.valueOf(c.get(Calendar.DAY_OF_MONTH));
		StatisticService statisticService = new StatisticService(GreenHouseApplication.getContext());
		statisticService.insertRecord(Launcher.selectMac , year, month, day, hour, soiltemp, soilhum, soilph, airtemp, airhum, co2, illu);
		Log.e(TAG, "insert statistic --------------------> current value" + year+"/"+month+"/"+day+" "+hour+"H" );
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

	/**
	 * TASK ���涨ʱ����1-���浽���ݿ⣻2-���浽���棻3������msgˢ�¶�ʱ�����б�
	 * @param jacktasks Ҫɾ����ʱ����Ĳ�����־λ����ʽΪ����12��ʮ�����ƣ�ff-ff-ff-ff-ff-ff
	 */
	public static void setAllJackTask(String jacktasks) {
		
		if (Timer.sendMsgQueue.contains("TASK" + jacktasks.substring(22, 34))) {
			Timer.sendMsgQueue.remove("TASK" + jacktasks.substring(22, 34));
		}
		
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
		Integer jackId = DataFormatConversion.HexStringToInt(jacktasks.substring(24, 36));
		
		Log.i(TAG, year + "-" + month + "-" + day + " " + hour + ":" + minute + "----" + on_hour + ":" + on_minute + "-"
				+ off_hour + ":" + off_minute + "-" + cycle + "次-插座" + jackId);
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyJackTask(year + "-" + month + "-" + day + " " + hour + ":" + minute, 
				on_hour + ":" + on_minute, off_hour + ":" + off_minute, cycle, jackId+"");
		
		if (JackFragmentShowinfo.jacks != null) {
			JackFragmentShowinfo.jacks.get(jackId-1).setBund(2);
			JackFragmentShowinfo.jacks.get(jackId-1).setStart(year + "-" + month + "-" + day + " " + hour + ":" + minute);
			JackFragmentShowinfo.jacks.get(jackId-1).setPoweron(on_hour + ":" + on_minute);
			JackFragmentShowinfo.jacks.get(jackId-1).setPoweroff(off_hour + ":" + off_minute);
			JackFragmentShowinfo.jacks.get(jackId-1).setCycle(cycle);
		}
		
		if (AlarmclockListView.sAlarmListHandler != null) {
			AlarmclockListView.sAlarmListHandler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = AlarmclockListView.sAlarmListHandler.obtainMessage();
					msg.arg1 = Const.UI_REFRESH;
					AlarmclockListView.sAlarmListHandler.sendMessage(msg);
				}
			});
		}
		
		
	}
	
	
	public static void setSensorLost(String data) {
		
		Integer sensorid = Integer.valueOf(data.substring(3, 4));
		SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
		
//		sensorService.modifySensorOnline(sensorid, 0);
		sensorService.modifySensorCurrentValue(sensorid, 0, 00, 00, 00, 00, 00, 0000, 0000);
		
		if (JackFragmentShowinfo.jacks != null) {
			SensorRecyclerView.handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = SensorRecyclerView.handler.obtainMessage();
					msg.arg1 = Const.UI_REFRESH;
					SensorRecyclerView.handler.sendMessage(msg);
				}
				
			});
		}
		Log.i(TAG, "[���������ߣ�����ˢ��]" );
	}
	
	
	/**
	 * DELE ɾ����ʱ����1-ˢ�����ݿ⣻2-ˢ�²�������bund��3-����msgˢ�¶�ʱ�����б����
	 * @param jacktasks Ҫɾ����ʱ����Ĳ�����־λ����ʽΪ����12��ʮ�����ƣ�ff-ff-ff-ff-ff-ff
	 */
	public static void deletelJackTask(String jacktasks) {
		
		if (Timer.sendMsgQueue.contains("DELE" + jacktasks.substring(0, 14))) {
			Timer.sendMsgQueue.remove("DELE" + jacktasks.substring(0, 14));
			Log.i(TAG, "[��ʱ�����ִ��ɾ���ɹ�]" );
		}
		
		String jackIds = DataFormatConversion.HexStringToBinaryString(jacktasks.substring(0, 12));
		
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.deleteJackTask(jackIds);
		
		if (JackFragmentShowinfo.jacks != null) {
			for (int i = 0; i < 48; i++) {
				if (jackIds.substring(i, i + 1).equals("1")) {
					JackFragmentShowinfo.jacks.get(i).setBund(0);
				}
			}
		}
		
		if (AlarmclockListView.sAlarmListHandler != null) {
			AlarmclockListView.sAlarmListHandler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = AlarmclockListView.sAlarmListHandler.obtainMessage();
					msg.arg1 = Const.UI_REFRESH;
					AlarmclockListView.sAlarmListHandler.sendMessage(msg);
				}
			});
		}
		
		
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
