package com.greenhouse.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.greenhouse.R;
import com.greenhouse.database.SensorService;
import com.greenhouse.model.Sensor;
import com.greenhouse.mvadpater.SensorRecyclerViewAdapter;
import com.greenhouse.util.Const;
import com.greenhouse.util.DataFormatConversion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorRecyclerView extends Activity {
	
	private Button btnEnter;
	
	public static SensorRecyclerViewAdapter sensorsRecyclerViewAdapter;
	public static List<Sensor> sOnlineSensorInfo; //在线传感器基本信息List，用于界面显示
	public SensorService sensorService;           //数据库Sensor表对象
	public static Handler handler;      
	
	public static Map<String, String> selectSensorsMap = new HashMap<String,String>(); //<1,0><2,0><3,0><4,0><5,0>
	public static List<Sensor> sSelectSensorInfo; //用户选中的传感器基本信息List
	public static String sBinSelectSensor;        //用户选中的传感器编号8位二进制表示
	public static String sProbeSensor;            //用户选中的探测传感器编号
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);
		
		sensorService = new SensorService(this);
		sOnlineSensorInfo = sensorService.getAllOnlineSensor();
		
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.arg1 == Const.UI_REFRESH) {
					sensorsRecyclerViewAdapter.notifyDataSetChanged();
				} 
			}
		 };
							
		RecyclerView sensorRecyclerView = (RecyclerView)findViewById(R.id.sensor_recyclerview);		
		LinearLayoutManager sensorsLayoutManager = new LinearLayoutManager(this);
		sensorsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		
		sensorRecyclerView.setLayoutManager(sensorsLayoutManager);		
		sensorsRecyclerViewAdapter = new SensorRecyclerViewAdapter(this, sOnlineSensorInfo, selectSensorsMap);
		sensorRecyclerView.setAdapter(sensorsRecyclerViewAdapter);
		
		btnEnter = (Button)findViewById(R.id.sensor_enter);
		btnEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean hasSelected = false;
				for(String value : selectSensorsMap.values()) {
					if (value.equals("1"))
						hasSelected = true;
				}
				
				if (hasSelected) {
					List<String> sensorList = new ArrayList<String>();
					for (int i = 0; i < selectSensorsMap.size(); i++){
						if (selectSensorsMap.get(i+"").equals("1")) {
							sensorList.add((i + 1)+"");
						}
					}
					sBinSelectSensor = DataFormatConversion.ListSelectSensorToBinString(sensorList); //用户选择的传感器
					sSelectSensorInfo = sensorService.getSelectSensor(sBinSelectSensor);             //用户选择的传感器基本信息
					sSelectSensorInfo.add(sensorService.getSelectSensorAverage(sBinSelectSensor));   //用户选择的传感器求各项平均
					startActivity(new Intent(SensorRecyclerView.this, SensorSetting.class));		 //跳转到设置传感器界面	
				} else {
					Dialog alertDialog = new AlertDialog.Builder(SensorRecyclerView.this). 
			                setTitle("提示"). 
			                setMessage("请至少选择一个传感器"). 
			                create(); 
			        alertDialog.show(); 
				}
			}
		});
		
		// title_bar theme
		TextView localTextView = (TextView) findViewById(R.id.title);
		localTextView.setText(R.string.sensorUI);
		
		// title_btn
		ImageView localImageView = (ImageView) findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back);
		localImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SensorRecyclerView.this.finish();
				onBackPressed();
			}
		});
		
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


}
