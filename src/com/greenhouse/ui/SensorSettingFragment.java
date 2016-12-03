package com.greenhouse.ui;


import com.greenhouse.R;
import com.greenhouse.model.Sensor;
import com.greenhouse.ui.SensorSetting.OnSensorSettingClickedListener;
import com.greenhouse.util.Const;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
	
	private final String TAG = "SensorSettingFragmetn";
	
	private TextView tvSensorType, tvNightThre, tvDayThre,tvCurrent, tvNightRecord, tvDayRecord, tvCurrentRecord;
	public static EditText edNightThre,edDayThre; //在SensorSetting中获取用户输入的早晚门限
	private Sensor sensor;
//	private int averageSensorId, valuerecord;
	private View vw;
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vw = inflater.inflate(R.layout.sensor_setting_fragment, container, false);
		tvSensorType = (TextView)vw.findViewById(R.id.show_current_value);
		tvCurrent = (TextView)vw.findViewById(R.id.current_value);
		tvCurrentRecord = (TextView)vw.findViewById(R.id.value_record);		
		tvNightThre = (TextView)vw.findViewById(R.id.show_night_threshold);
		tvDayThre = (TextView)vw.findViewById(R.id.show_day_threshold);
		tvNightRecord = (TextView)vw.findViewById(R.id.night_threshold_record);
		tvDayRecord = (TextView)vw.findViewById(R.id.day_threshold_record);
		edNightThre = (EditText)vw.findViewById(R.id.night_threshold);
		edDayThre = (EditText)vw.findViewById(R.id.day_threshold);
		edNightThre.setText(SensorSetting.sSetNightThre[0] + "");
		edDayThre.setText(SensorSetting.sSetDayThre[0] + "");
		
		sensor = SensorRecyclerView.sSelectSensorInfo.get(SensorRecyclerView.sSelectSensorInfo.size()-1); //选定传感器的实时值平均
		Log.e(TAG, "sBinSelectSensor = " + SensorRecyclerView.sBinSelectSensor);
		Log.e(TAG, "sSelectSensorInfo.size() = " + SensorRecyclerView.sSelectSensorInfo.size());
		
		SensorSetting.sSensorType = 1;                      //初始化传感器类型
		tvSensorType.setText(Const.SOIL_TEMP);              //显示初始化传感器类型
		tvNightThre.setText(Const.NIGHT_THRE_VALUE);          //显示“夜间门限”
		tvDayThre.setText(Const.DAY_THRE_VALUE);          //显示“白天门限”
		tvNightRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT); //显示夜间门限建议值
		tvDayRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT);   //显示白天门限建议值
		tvCurrent.setText(sensor.getSoiltemp() + Const.SOIL_TEMP_UNIT); 					   //显示选定传感器的实时平均值
		return vw;			
	}
	

	@Override
	public void onSensorSettingClickedListener(String msg) {
		// TODO Auto-generated method stub
		
		switch (msg) {
		case "soil_temp":
			SensorSetting.sSensorType = 1;         						 	//保存传感器类型     
			SensorSetting.sDeviceType = 1;
			tvSensorType.setText(Const.SOIL_TEMP); 						 	//显示传感器类型
			tvNightRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT);	//显示夜间门限建议值
			tvDayRecord.setText("建议值：25-29" + Const.SOIL_TEMP_UNIT);   	//显示白天门限建议值
			tvCurrent.setText(sensor.getSoiltemp() + Const.SOIL_TEMP_UNIT); //显示选定传感器的实时平均值
			edNightThre.setText(SensorSetting.sSetNightThre[SensorSetting.sSensorType-1] + "");
			edDayThre.setText(SensorSetting.sSetDayThre[SensorSetting.sSensorType-1] + "");
			edNightThre.setInputType(InputType.TYPE_CLASS_NUMBER); 
			edDayThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		case "soil_hum":
			SensorSetting.sSensorType = 2;
			SensorSetting.sDeviceType = 0;
			tvSensorType.setText(Const.SOIL_HUM); 						  //显示传感器类型
			tvNightRecord.setText("建议值：60-80" + Const.SOIL_HUM_UNIT);  //显示夜间门限建议值
			tvDayRecord.setText("建议值：60-80" + Const.SOIL_HUM_UNIT);    //显示白天门限建议值
			tvCurrent.setText(sensor.getSoilhum() + Const.SOIL_HUM_UNIT); //显示选定传感器的实时平均值
			edNightThre.setText(SensorSetting.sSetNightThre[SensorSetting.sSensorType-1] + "");
			edDayThre.setText(SensorSetting.sSetDayThre[SensorSetting.sSensorType-1] + "");
			edNightThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			edDayThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		case "soil_ph":
			SensorSetting.sSensorType = 3; 
			SensorSetting.sDeviceType = 1;
			tvSensorType.setText(Const.PH); 					   									//显示传感器类型
			tvNightRecord.setText("建议值：5-7" + Const.PH_UNIT);   									//显示夜间门限建议值
			tvDayRecord.setText("建议值：5-7" + Const.PH_UNIT);    									//显示白天门限建议值
			tvCurrent.setText(sensor.getSoilph()/10 + "." + sensor.getSoilph()%10 + Const.PH_UNIT); //ph值保存－>显示处理
			edNightThre.setText(SensorSetting.sSetNightThre[SensorSetting.sSensorType-1]/10 + "." 
					+ SensorSetting.sSetNightThre[SensorSetting.sSensorType-1]%10);
			edDayThre.setText(SensorSetting.sSetDayThre[SensorSetting.sSensorType-1]/10 + "."
					+ SensorSetting.sSetDayThre[SensorSetting.sSensorType-1]%10);
			edNightThre.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //使能小数点＋数字输入
			edDayThre.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);   //使能小数点＋数字输入
			break;
		case "air_temp":
			SensorSetting.sSensorType = 4;
			SensorSetting.sDeviceType = 1;
			tvSensorType.setText(Const.AIR_TEMP); 					      //显示传感器类型
			tvNightRecord.setText("建议值：26-28" + Const.AIR_TEMP_UNIT);  //显示夜间门限建议值
			tvDayRecord.setText("建议值：26-28" + Const.AIR_TEMP_UNIT);    //显示白天门限建议值
			tvCurrent.setText(sensor.getAirtemp() + Const.AIR_TEMP_UNIT); //显示选定传感器的实时平均值
			edNightThre.setText(SensorSetting.sSetNightThre[SensorSetting.sSensorType-1] + "");
			edDayThre.setText(SensorSetting.sSetDayThre[SensorSetting.sSensorType-1] + "");
			edNightThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			edDayThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		case "air_hum":
			SensorSetting.sSensorType = 5;
			SensorSetting.sDeviceType = 0;
			tvSensorType.setText(Const.AIR_HUM); 					    //显示传感器类型
			tvNightRecord.setText("建议值：60-80" + Const.AIR_HUM_UNIT); //显示夜间门限建议值
			tvDayRecord.setText("建议值：60-80" + Const.AIR_HUM_UNIT);   //显示白天门限建议值
			tvCurrent.setText(sensor.getAirhum() + Const.AIR_HUM_UNIT); //显示选定传感器的实时平均值
			edNightThre.setText(SensorSetting.sSetNightThre[SensorSetting.sSensorType-1] + "");
			edDayThre.setText(SensorSetting.sSetDayThre[SensorSetting.sSensorType-1] + "");
			edNightThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			edDayThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		case "co2":
			SensorSetting.sSensorType = 6;
			SensorSetting.sDeviceType = 0;
			tvSensorType.setText(Const.CO2); 					      //显示传感器类型
			tvNightRecord.setText("建议值：300-800" + Const.CO2_UNIT); //显示夜间门限建议值
			tvDayRecord.setText("建议值：300-800" + Const.CO2_UNIT);   //显示白天门限建议值
			tvCurrent.setText(sensor.getCo2() + Const.CO2_UNIT);      //显示选定传感器的实时平均值
			edNightThre.setText(SensorSetting.sSetNightThre[SensorSetting.sSensorType-1] + "");
			edDayThre.setText(SensorSetting.sSetDayThre[SensorSetting.sSensorType-1] + "");
			edNightThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			edDayThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		case "illum":
			SensorSetting.sSensorType = 7;
			SensorSetting.sDeviceType = 0;
			tvSensorType.setText(Const.ILLUMINATION); 					              //显示传感器类型
			tvNightRecord.setText("建议值：5000-20000" + Const.ILLUMINATION_UNIT);     //显示夜间门限建议值
			tvDayRecord.setText("建议值：5000-20000" + Const.ILLUMINATION_UNIT);       //显示白天门限建议值
			tvCurrent.setText(10*sensor.getIllumination() + Const.ILLUMINATION_UNIT); //显示选定传感器的实时平均值
			edNightThre.setText(SensorSetting.sSetNightThre[SensorSetting.sSensorType-1] + "");
			edDayThre.setText(SensorSetting.sSetDayThre[SensorSetting.sSensorType-1] + "");
			edNightThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			edDayThre.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
			default:
				break;
		}
		
	}
	

	
	

	
}
