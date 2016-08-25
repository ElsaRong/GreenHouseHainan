package com.greenhouse.ui;


import com.greenhouse.R;
import com.greenhouse.model.Sensor;
import com.greenhouse.ui.SensorSetting.OnSensorSettingClickedListener;
import com.greenhouse.util.Const;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/** 
* @author 		Elsa 
* @Email 		elsarong715@gmail.com
* @date 		2015/12/12/ PM 5:07:22 
* @version  	1.0  
* @description 
*/
public class SensorSettingFragment extends Fragment implements OnSensorSettingClickedListener{
	
	private TextView tvSensorType, tvNightThre, tvDayThre,tvCurrent, tvNightRecord, tvDayRecord, tvCurrentRecord;
	public static EditText edNightThre,edDayThre; //在SensorSetting中获取用户输入的早晚门限
	private Sensor sensor;
	private int averageSensorId, valuerecord;
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View vw = inflater.inflate(R.layout.sensor_setting_fragment, container, false);
		
		tvSensorType = (TextView)vw.findViewById(R.id.show_current_value);
		tvCurrent = (TextView)vw.findViewById(R.id.current_value);
		tvCurrentRecord = (TextView)vw.findViewById(R.id.value_record);		
		tvNightThre = (TextView)vw.findViewById(R.id.show_night_threshold);
		tvDayThre = (TextView)vw.findViewById(R.id.show_day_threshold);
		tvNightRecord = (TextView)vw.findViewById(R.id.night_threshold_record);
		tvDayRecord = (TextView)vw.findViewById(R.id.day_threshold_record);
		edNightThre = (EditText)vw.findViewById(R.id.night_threshold);
		edDayThre = (EditText)vw.findViewById(R.id.day_threshold);
		
		sensor = SensorRecyclerView.sSelectSensorInfo.get(SensorRecyclerView.sSelectSensorInfo.size()-1); //选定传感器的实时值平均
		tvSensorType.setText(Const.SOIL_TEMP);              //显示传感器类型
		tvNightThre.setText(Const.DAY_THRE_VALUE);          //显示“白天门限”
		tvDayThre.setText(Const.NIGHT_THRE_VALUE);          //显示“夜间门限”
		tvNightRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT); //显示夜间门限建议值
		tvDayRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT);   //显示白天门限建议值
		tvCurrent.setText(sensor.getSoiltemp() + Const.SOIL_TEMP_UNIT); 					   //显示选定传感器的实时平均值
//		valuerecord = SensorRecyclerView.sSelectSensorInfo.get(averageSensorId).getSoiltemp(); //获取选定传感器的平均历史值
//		tvCurrentRecord.setText(Const.RECORD_VALUE + valuerecord + Const.SOIL_TEMP_UNIT);      //显示选定传感器的平均历史值
		return vw;			
	}


	@Override
	public void onSensorSettingClickedListener(String msg) {
		// TODO Auto-generated method stub
		switch (msg) {
		case "soil_temp":
			SensorSetting.sSensorType = 1;         						  //保存传感器类型                                     
			tvSensorType.setText(Const.SOIL_TEMP); 						  //显示传感器类型
			tvNightRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT); //显示夜间门限建议值
			tvDayRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT);   //显示白天门限建议值
			tvCurrent.setText(sensor.getSoiltemp() + Const.SOIL_TEMP_UNIT);                        //显示选定传感器的实时平均值
			break;
		case "soil_hum":
			SensorSetting.sSensorType = 2;
			tvSensorType.setText(Const.SOIL_HUM); 						  //显示传感器类型
			tvNightRecord.setText("建议值：60-80" + Const.SOIL_HUM_UNIT);  //显示夜间门限建议值
			tvDayRecord.setText("建议值：60-80" + Const.SOIL_HUM_UNIT);    //显示白天门限建议值
			tvCurrent.setText(sensor.getSoilhum() + Const.SOIL_HUM_UNIT); //显示选定传感器的实时平均值
			break;
		case "soil_ph":
			SensorSetting.sSensorType = 3; 
			tvSensorType.setText(Const.PH); 					   //显示传感器类型
			tvNightRecord.setText("建议值：5-7" + Const.PH_UNIT);   //显示夜间门限建议值
			tvDayRecord.setText("建议值：5-7" + Const.PH_UNIT);     //显示白天门限建议值
			tvCurrent.setText(sensor.getSoilph() + Const.PH_UNIT); //显示选定传感器的实时平均值
			break;
		case "air_temp":
			SensorSetting.sSensorType = 4;
			tvSensorType.setText(Const.AIR_TEMP); 					   //显示传感器类型
			tvNightRecord.setText("建议值：5-7" + Const.AIR_TEMP_UNIT);   //显示夜间门限建议值
			tvDayRecord.setText("建议值：5-7" + Const.AIR_TEMP_UNIT);     //显示白天门限建议值
			tvCurrent.setText(sensor.getAirtemp() + Const.AIR_TEMP_UNIT); //显示选定传感器的实时平均值
			break;
		case "air_hum":
			SensorSetting.sSensorType = 5;
			tvSensorType.setText(Const.AIR_HUM); 					   //显示传感器类型
			tvNightRecord.setText("建议值：60-80" + Const.AIR_HUM_UNIT);   //显示夜间门限建议值
			tvDayRecord.setText("建议值：60-80" + Const.AIR_HUM_UNIT);     //显示白天门限建议值
			tvCurrent.setText(sensor.getAirhum() + Const.AIR_HUM_UNIT); //显示选定传感器的实时平均值
			break;
		case "co2":
			SensorSetting.sSensorType = 6;
			tvSensorType.setText(Const.CO2); 					   //显示传感器类型
			tvNightRecord.setText("建议值：300-800" + Const.CO2_UNIT);   //显示夜间门限建议值
			tvDayRecord.setText("建议值：300-800" + Const.CO2_UNIT);     //显示白天门限建议值
			tvCurrent.setText(sensor.getCo2() + Const.CO2_UNIT); //显示选定传感器的实时平均值
			break;
		case "illum":
			SensorSetting.sSensorType = 7;
			tvSensorType.setText(Const.ILLUMINATION); 					   //显示传感器类型
			tvNightRecord.setText("建议值：5000-20000" + Const.ILLUMINATION_UNIT);   //显示夜间门限建议值
			tvDayRecord.setText("建议值：5000-20000" + Const.ILLUMINATION_UNIT);     //显示白天门限建议值
			tvCurrent.setText(sensor.getIllumination() + Const.ILLUMINATION_UNIT); //显示选定传感器的实时平均值
			break;
			default:
				break;
		}
		
	}
	

	
	

	
}