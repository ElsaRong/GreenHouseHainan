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

}
