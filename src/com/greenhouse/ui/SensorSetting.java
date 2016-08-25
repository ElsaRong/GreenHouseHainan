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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import java.util.LinkedList;
import java.util.Queue;
import com.greenhouse.R;
import com.greenhouse.networkservice.NetBroadcastReceiver;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.networkservice.ThreadPoolManager;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;

public class SensorSetting extends Activity implements View.OnClickListener{

	public static final String TAG = "SensorSetting.class";
	
	OnSensorSettingClickedListener myListener;
	public BroadcastReceiver myReceiver;
	public static Handler handler;
	private RadioGroup rg;
	
	public static String sSetDayThre   = ""; //用户设置的白天门限值
	public static String sSetNightThre = ""; //用户设置的夜间门限值
	public static int sDeviceType      =  0; //用户选中的插座上绑定的设备类型
	public static int sSensorType      =  1; //用户选中的传感器类型
	
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
		
		Button sensor_setting = (Button)findViewById(R.id.sensor_setting);
		sensor_setting.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sSetDayThre = SensorSettingFragment.edNightThre.getText().toString();
				sSetNightThre = SensorSettingFragment.edDayThre.getText().toString(); 
				
				if (sSetNightThre.equals("") && sSetDayThre.equals("")) {
					ToastUtil.TextToastShort(SensorSetting.this, "请正确设置早晚门限！");
				} else if ( (sSetNightThre.length() > 4 || sSetDayThre.length() > 4) && SensorSetting.sSensorType != 7 ) {
					ToastUtil.TextToastShort(SensorSetting.this, "早晚门限不能超过四位");
				} else if  ( (sSetNightThre.length() > 5 || sSetDayThre.length() > 5) && SensorSetting.sSensorType == 7 ) {
					ToastUtil.TextToastShort(SensorSetting.this, "早晚门限不能超过五位");
				} else {
					
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
