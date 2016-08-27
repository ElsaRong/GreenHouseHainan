package com.greenhouse.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import com.greenhouse.R;
import com.greenhouse.networkservice.NetBroadcastReceiver;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;

public class SensorSetting extends Activity implements View.OnClickListener{

	public static final String TAG = "SensorSetting.class";
	
	OnSensorSettingClickedListener myListener;
	public BroadcastReceiver myReceiver;
	public static Handler handler;
	private RadioGroup rg;
	
	public static int[] sSetDayThre   = {0,0,0,0,0,0,0}; //用户设置的白天门限值
	public static int[] sSetNightThre = {0,0,0,0,0,0,0}; //用户设置的夜间门限值
	public static int[] sChosedSensor = {0,0,0,0,0,0,0}; //用户选择设置的传感器类型
	public static int sDeviceType      =  0; //用户选中的插座上绑定的设备类型
	public static int sSensorType      =  1; //用户选中的传感器类型
	
	private Button sensor_setting;
	private Button save_setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_setting_layout);
		
		handler = new Handler() {
			public void handleMessage(Message msg) {
				
				switch (msg.what) {
				case Const.BACK_TO_LAUNCHER:
					ToastUtil.TextToastLong(SensorSetting.this, "网络异常");
					Launcher.client.setState(Const.SOCKET_DISCONNECTED);
					Launcher.server.setServerState(Const.SOCKET_DISCONNECTED);
					Launcher.client.destroy();
					Launcher.server.destroy();
					Intent intent = new Intent(SensorSetting.this, Launcher.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;	
				default:
					break;
				}
			}
		};
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction("com.greenhosue.backtolauncheraction");
        myReceiver = new NetBroadcastReceiver(handler);
        this.registerReceiver(myReceiver, filter);
		
		
		// title_btn
		ImageView localImageView = (ImageView) findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back);
		localImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		
		sensor_setting = (Button)findViewById(R.id.sensor_setting);
		save_setting =  (Button)findViewById(R.id.save_setting);
		
		save_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//校验1
				if (SensorSettingFragment.edNightThre.getText().toString().equals(0) &&
						SensorSettingFragment.edDayThre.getText().toString().equals(0) &&
						SensorSettingFragment.edNightThre.getText().toString().equals("") &&
						SensorSettingFragment.edDayThre.getText().toString().equals("")) 
				{
					ToastUtil.TextToastShort(SensorSetting.this, "请正确设置早晚门限！");
				} 
				//校验2
				else if ((SensorSettingFragment.edNightThre.getText().toString().length() > 4 ||
							SensorSettingFragment.edDayThre.getText().toString().length() >4) &&
							SensorSetting.sSensorType != 7) 
				{
					ToastUtil.TextToastShort(SensorSetting.this, "早晚门限不能超过四位");
				} 
				//校验3
				else if ((SensorSettingFragment.edNightThre.getText().toString().length() > 5 ||
						SensorSettingFragment.edDayThre.getText().toString().length() >5) &&
						SensorSetting.sSensorType == 7) 
				{
					ToastUtil.TextToastShort(SensorSetting.this, "早晚门限不能超过五位");
				}
				//保存到门限值数组
				else 
				{
					sChosedSensor[SensorSetting.sSensorType-1] = 1;
					sSetDayThre[SensorSetting.sSensorType-1] = Integer.parseInt(SensorSettingFragment.edNightThre.getText().toString());
					sSetNightThre[SensorSetting.sSensorType-1] = Integer.parseInt(SensorSettingFragment.edDayThre.getText().toString()); 
				}
			}
			
		});
		
		sensor_setting.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SocketOutputTask.sendMsgQueue.add("BUND");
				SocketOutputTask.getHandler().sendEmptyMessage(Const.BUND);	
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (SocketOutputTask.sendMsgQueue.contains("BUND")) {
					SocketOutputTask.getHandler().sendEmptyMessage(Const.BUND);	
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (SocketOutputTask.sendMsgQueue.contains("BUND")) {
					SocketOutputTask.getHandler().sendEmptyMessage(Const.BUND);	
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (SocketOutputTask.sendMsgQueue.contains("BUND")) {
					SocketOutputTask.getHandler().sendEmptyMessage(Const.BUND);	
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (SocketOutputTask.sendMsgQueue.contains("BUND")) {
					SocketOutputTask.getHandler().sendEmptyMessage(Const.BUND);	
				}
				Intent intent = new Intent(SensorSetting.this, JackFragmentMaster.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);				
					
			}
				
		});

		rg = (RadioGroup)findViewById(R.id.sensor_radio_group);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				checkedChange(checkedId);
			}
		});
		setDefaultFragment();
	}
	
	
	public void setDefaultFragment() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();		
		SensorSettingFragment sensorSettingFragment = new SensorSettingFragment();
		fragmentTransaction.add(R.id.sensor_setting_fragment, sensorSettingFragment);		
		fragmentTransaction.commit();
	}

	private void checkedChange(int id) {

		SensorSettingFragment sensorSet = (SensorSettingFragment)getFragmentManager().findFragmentById(R.id.sensor_setting_fragment);
		
		switch (id) {
		case R.id.rb_soil_temp:
			SensorSetting.sSensorType = 1;   
			sensorSet.onSensorSettingClickedListener("soil_temp");
			break;
		case R.id.rb_soil_hum:
			SensorSetting.sSensorType = 2;   
			sensorSet.onSensorSettingClickedListener("soil_hum");
			break;
		case R.id.rb_soil_ph:
			SensorSetting.sSensorType = 3;   
			sensorSet.onSensorSettingClickedListener("soil_ph");
			break;
		case R.id.rb_air_temp:
			SensorSetting.sSensorType = 4;   
			sensorSet.onSensorSettingClickedListener("air_temp");
			break;
		case R.id.rb_air_hum:
			SensorSetting.sSensorType = 5;   
			sensorSet.onSensorSettingClickedListener("air_hum");
			break;
		case R.id.rb_co2:
			SensorSetting.sSensorType = 6;   
			sensorSet.onSensorSettingClickedListener("co2");
			break;
		case R.id.rb_illum:
			SensorSetting.sSensorType = 7;   
			sensorSet.onSensorSettingClickedListener("illum");
			break;
			default:
				break;
		}
	}
	
	public interface OnSensorSettingClickedListener {
		public void onSensorSettingClickedListener(String msg);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestoy()");
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.title_btn:
			onBackPressed();
			return;
		default:
			return;
		}
	}

	

}
