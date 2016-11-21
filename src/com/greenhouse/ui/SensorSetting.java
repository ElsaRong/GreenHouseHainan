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
import android.widget.TextView;

import com.greenhouse.R;
import com.greenhouse.animation.HeavenAnimateView;
import com.greenhouse.networkservice.NetBroadcastReceiver;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.util.Const;
import com.greenhouse.util.DataFormatConversion;
import com.greenhouse.util.ToastUtil;

public class SensorSetting extends Activity implements View.OnClickListener{

	public static final String TAG = "SensorSetting.class";
	
	OnSensorSettingClickedListener myListener;
	private RadioGroup rg;
	
	public static int[] sSetDayThre   = new int[]{0,0,0,0,0,0,0}; //用户设置的白天门限值
	public static int[] sSetNightThre = new int[]{0,0,0,0,0,0,0}; //用户设置的夜间门限值
	public static int[] sChosedSensor = new int[]{0,0,0,0,0,0,0}; //用户选择设置的传感器类型
	public static int sDeviceType      =  0; //用户选中的插座上绑定的设备类型
	public static int sSensorType      =  1; //用户选中的传感器类型
	
	private Button sensor_setting;
	private Button save_setting;
	private ProgressBar title_waiting;
	private TextView text_waiting;
	
	private Handler handler = new Handler();
	
	private Runnable refreshSensorSetting = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			refreshHeavenView();
			handler.postDelayed(refreshSensorSetting, 1000);
		}
	};
	
	private void refreshHeavenView() {
		HeavenAnimateView localHeavenAnimateView = (HeavenAnimateView) findViewById(R.id.heaven);
		if (localHeavenAnimateView != null) {
			localHeavenAnimateView.update();
			localHeavenAnimateView.postInvalidate();
		}
		if (Launcher.client == null) {
			title_waiting.setVisibility(View.VISIBLE);	
			text_waiting.setVisibility(View.VISIBLE);
		} else {
			title_waiting.setVisibility(View.GONE);
			text_waiting.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_setting_layout);
		
		sensor_setting = (Button)findViewById(R.id.sensor_setting);
		save_setting =  (Button)findViewById(R.id.save_setting);
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting);
		text_waiting = (TextView)findViewById(R.id.text_waiting);
		
		sSetDayThre   = new int[]{0,0,0,0,0,0,0};
		sSetNightThre = new int[]{0,0,0,0,0,0,0};
		sChosedSensor = new int[]{0,0,0,0,0,0,0};
		
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
		
		
		
		
		save_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//校验1
				if (SensorSettingFragment.edNightThre.getText().toString().equals(0) ||
						SensorSettingFragment.edDayThre.getText().toString().equals(0) ||
						SensorSettingFragment.edNightThre.getText().toString().equals("") ||
						SensorSettingFragment.edDayThre.getText().toString().equals("")) 
				{
					sChosedSensor[SensorSetting.sSensorType-1] = 0;
					sSetDayThre[SensorSetting.sSensorType-1] = 0;
					sSetNightThre[SensorSetting.sSensorType-1] = 0;
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
				//保存到门限值数组,
				else 
				{
					sChosedSensor[SensorSetting.sSensorType-1] = 1;
					
					//ph值*10再处理
					if (SensorSetting.sSensorType == 3) {
						Double doubleDayThre = 10*Double.valueOf(SensorSettingFragment.edDayThre.getText().toString());
						Double doubleNightThre = 10*Double.valueOf(SensorSettingFragment.edNightThre.getText().toString());
						Integer intDayThre = doubleDayThre.intValue();
						Integer intNightthre = doubleNightThre.intValue();
						sSetDayThre[SensorSetting.sSensorType-1] = intDayThre;
						sSetNightThre[SensorSetting.sSensorType-1] = intNightthre;
					} else {
						sSetDayThre[SensorSetting.sSensorType-1] = Integer.parseInt(SensorSettingFragment.edDayThre.getText().toString());
						sSetNightThre[SensorSetting.sSensorType-1] = Integer.parseInt(SensorSettingFragment.edNightThre.getText().toString()); 
					}
				}
			}
			
		});
		
		sensor_setting.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SocketOutputTask.sendMsgQueue.addLast(createBUNDmsg());
				SocketOutputTask.sendMsgQueue.addLast(createBUNDmsg());
				SocketOutputTask.sendMsgQueue.addLast(createBUNDmsg());
				SocketOutputTask.sendMsgQueue.addLast(createBUNDmsg());
				SocketOutputTask.sendMsgQueue.addLast(createBUNDmsg());
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
		handler.post(refreshSensorSetting);
	}
	
	
	public byte[] createBUNDmsg() {
		String msg = "";                    //拼接报文
		String hexJackId = "";              //插座编号转十六进制字符
		String hexSensorAndDeviceType = ""; //选定的传感器类型和设备类型编号转十六进制字符
		String hexBundSensorId = "";        //选择绑定的传感器编号转十六进制字符
		StringBuffer hexAllThreBuffer = new StringBuffer("0000000000000000000000000000000000");//拼接7种传感器早晚门限
		String hexAllThre = "";
		
		hexJackId = Integer.toHexString(JackFragmentModeSet.sJackId);
		hexBundSensorId = Integer.toHexString(Integer.valueOf(SensorRecyclerView.sBinSelectSensor,2));
		hexSensorAndDeviceType = DataFormatConversion.MultiSensAndDevTypeToHexStr(SensorSetting.sChosedSensor, SensorSetting.sDeviceType);
		
		//补位: 插座&传感器&设备类型
		hexJackId = DataFormatConversion.FormatStringByAddZero(hexJackId, 2);                           //插座编号：2位十六进制字符
		hexSensorAndDeviceType = DataFormatConversion.FormatStringByAddZero(hexSensorAndDeviceType, 2); //传感器和设备类型：2位十六进制字符
		//补位: 早晚门限
		for (int i=0; i<7; i++) {
			String hexDayThre = Integer.toHexString(SensorSetting.sSetDayThre[i]);
			String hexNightThre = Integer.toHexString(SensorSetting.sSetNightThre[i]);
//			Log.e(TAG, "第"+i+"种门限"+hexDayThre + ","+hexNightThre);
			//第六种传感器要求长度6
			if (i==5) {
				hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 3);
				hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 3);
			} 
			//第七种传感器要求长度6
			else if (i==6) {
				hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 4);
				hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 4);
			} 
			//其它类型传感器要求长度2
			else {
				hexDayThre = DataFormatConversion.FormatStringByAddZero(hexDayThre, 2);
				hexNightThre = DataFormatConversion.FormatStringByAddZero(hexNightThre, 2);
			}
			//拼接7种传感器早晚门限，累计34位十六进制字符
			hexAllThreBuffer = DataFormatConversion.FormatMultiThreByReplace(hexAllThreBuffer, hexDayThre+hexNightThre, i+1);
		}
		hexAllThre = hexAllThreBuffer.toString();
//		Log.e(TAG, "7种早晚门限: "+hexAllThre);
		
		msg = DataFormatConversion.IrreguStringToHexValue("HFUT" + Launcher.selectMac + "BUND")
			    + hexJackId + hexSensorAndDeviceType  //插座［2］－传感器设备类型［2］
				+ hexAllThre + hexBundSensorId        //早晚1［4］－早晚2［4］－早晚3［4］－早晚5［4］－早晚5［4］－早晚6［6］-早晚7［8］－选择定传感器［2］
				+ DataFormatConversion.IrreguStringToHexValue("WANG");
		
		byte[] b = DataFormatConversion.HexStringToByte(msg);	
		return b;
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
		super.onDestroy();
		handler.removeCallbacks(refreshSensorSetting);
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
