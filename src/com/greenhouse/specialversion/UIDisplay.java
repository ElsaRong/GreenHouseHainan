package com.greenhouse.specialversion;

import com.greenhouse.util.Const;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			May 29, 2016 5:30:25 PM 
* @version		1.0  
* @description	数据库和协议中的存储格式转换成UI上的直观显示
*/
public class UIDisplay {
	
	/**
	 * @Title:       SetBinearySensorsToShow
	 * @description: TODO 将数据库和协议中的绑定传感器格式转换到UI上的直观显示,eg.10100000->传感器1号 传感器3号
	 * @param        @param binarySensors
	 * @param        @return
	 * @return       String
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 21, 2016, 2:42:07 PM
	 */
	public static String ShowBundSensor(String binBundSensor) {
		String sensors = "";
		for (int i = 0; i < Const.SENSOR_SUM; i++) {
			if (binBundSensor.substring(i, i+1).equals("1")) {
				sensors += (i+1) + "号 ";
			}
		}
		if (sensors.length() > 1) {
			sensors = "传感器" + sensors.substring(0, sensors.length()-1);
		} else {
			sensors = "无传感器";
		}
		return sensors;
	}
	
	/**
	 * @Title:       ShowLiteBundTask
	 * @description: TODO 将冗余的插座绑定传感器任务精简到适合Listview的Adapter
	 * @param        @param bundTask
	 * @param        @return
	 * @return       int[][]
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 21, 2016, 5:19:53 PM
	 */
	public static int[][] ShowLiteBundTask(int[][] bundTask) {
		int count = 0;
		for (int i=0; i<7; i++) {
			if (bundTask[i][0]>=1 && bundTask[i][0] <= 7) {
				count++;
			}
		}
		int[][] liteBundTask = new int[count][4];
		int countx = 0;
		for (int i=0; i<7; i++) {
			if (bundTask[i][0]>=1 && bundTask[i][0] <= 7) {
				for (int j=0; j<4; j++) {
					liteBundTask[countx][j] = bundTask[i][j];
				}
				countx++;
			}
		}
		return liteBundTask;
	}
	
	/**
	 * @Title:       showSensInfoListSensType
	 * @description: TODO 将int值转换成String表示的传感器类型
	 * @param        @param sensorType
	 * @param        @return
	 * @return       String
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 28, 2016, 9:54:38 AM
	 */
	public static String showSensInfoListSensType(int sensorType) {
		String sensorTypeStr = "null type";
		switch (sensorType) {
		case 1:
			sensorTypeStr = Const.SOIL_TEMP;
			break;
		case 2:
			sensorTypeStr = Const.SOIL_HUM;
			break;
		case 3:
			sensorTypeStr = Const.PH;
			break;
		case 4:
			sensorTypeStr = Const.AIR_TEMP;
			break;
		case 5:
			sensorTypeStr = Const.AIR_HUM;
			break;
		case 6:
			sensorTypeStr = Const.CO2;
			break;
		case 7:
			sensorTypeStr = Const.ILLUMINATION;
	     	break;
		default:
			break;
		}
		return sensorTypeStr;
	}
	
	/**
	 * @Title:       showSensInforListUnit
	 * @description: TODO 将int值转换成String表示的传感器类型单位
	 * @param        @param sensorType
	 * @param        @return
	 * @return       String
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 28, 2016, 9:58:32 AM
	 */
	public static String showSensInforListUnit(int sensorType) {
		String sensorTypeUnitStr = "";
		switch (sensorType) {
		case 1:
			sensorTypeUnitStr = " " + Const.SOIL_TEMP_UNIT;
			break;
		case 2:
			sensorTypeUnitStr = " " + Const.SOIL_HUM_UNIT;
			break;
		case 3:
			sensorTypeUnitStr = " " + Const.PH_UNIT;
			break;
		case 4:
			sensorTypeUnitStr = " " + Const.AIR_TEMP_UNIT;
			break;
		case 5:
			sensorTypeUnitStr = " " + Const.AIR_HUM_UNIT;
			break;
		case 6:
			sensorTypeUnitStr = " " + Const.CO2_UNIT;
			break;
		case 7:
			sensorTypeUnitStr = " " + Const.ILLUMINATION_UNIT;
	     	break;
		default:
			break;
		}
		return sensorTypeUnitStr;
	}
	
	/**
	 * @Title:       showSensInfoListValue
	 * @description: TODO ph，光照度值的显示处理
	 * @param        @param sensorType
	 * @param        @param intValue
	 * @param        @return
	 * @return       String
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 28, 2016, 10:28:44 AM
	 */
	public static String showSensInfoListValue(int sensorType, int intValue) {
		String strValue = "";
		if (sensorType == 3) {
			strValue = intValue/10 + "." + intValue%10;
		} else if (sensorType == 7) {
			strValue = intValue*10 + "";
		} else {
			strValue = intValue + "";
		}
		return strValue;
	}

}
