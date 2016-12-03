package com.greenhouse.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.security.auth.PrivateCredentialPermission;

import com.example.smartlinklib.ModuleInfo;
import com.example.smartlinklib.SmartLinkManipulator;
import com.example.smartlinklib.SmartLinkManipulator.ConnectCallBack;
import com.greenhouse.R;
import com.greenhouse.database.ControllerService;
import com.greenhouse.database.JackService;
import com.greenhouse.database.SensorService;
import com.greenhouse.database.StatisticService;
import com.greenhouse.networkservice.NetworkManager;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.ToastUtil;


public class Smartlink extends Activity {
	
	
	private TextView ssid;
	
	private Button btnStart;
	
	private EditText pswd;

	private SmartLinkManipulator smManipulator;
	
	private LinearLayout layoutSSID, layoutPSWD, layoutWaiting;

	private boolean isConncting = false;

	private static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smartlink);

		layoutSSID = (LinearLayout) findViewById(R.id.line_ssid);
		layoutPSWD = (LinearLayout) findViewById(R.id.line_pswd);
		layoutWaiting = (LinearLayout) findViewById(R.id.waiting_layout);
		ssid = (TextView) findViewById(R.id.ssid);
		ssid.setText(NetworkManager.getSSid(Smartlink.this));
		pswd = (EditText) findViewById(R.id.pswd);
		pswd.setText(GreenHouseApplication.getPassword()+"");

		handler = new Handler();

		ImageView localImageView = (ImageView) findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back);
		localImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		btnStart = (Button) findViewById(R.id.start);
		
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String inputPassword = pswd.getText().toString().trim();
				GreenHouseApplication.setPassword(inputPassword);
				if(inputPassword != null) {
					btnStart.setVisibility(View.INVISIBLE);
					layoutSSID.setVisibility(View.INVISIBLE);
					layoutPSWD.setVisibility(View.INVISIBLE);
					layoutWaiting.setVisibility(View.VISIBLE);
					if (!isConncting) {
						isConncting = true;
						smManipulator = SmartLinkManipulator.getInstence(Smartlink.this);
						smManipulator.setConnection(NetworkManager.getSSid(Smartlink.this), inputPassword);
						smManipulator.Startconnection(connectCallBack);
						return;
					}
					smManipulator.StopConnection();
					isConncting = false;
				} else {
					ToastUtil.TextToastShort(Smartlink.this, "请检查密码输入是否正确");	
				}
			}
		});
	}

	/**
	 * Smartlinkģ�鷵�صĿ�������Ϣ
	 */
	private ModuleInfo module;
	
	
	/**
	 * 
	 */
	ConnectCallBack connectCallBack = new ConnectCallBack() {
		@Override
		public void onConnectTimeOut() {
			// TODO Auto-generated method stub
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					btnStart.setVisibility(View.VISIBLE);
					layoutSSID.setVisibility(View.VISIBLE);
					layoutPSWD.setVisibility(View.VISIBLE);
					layoutWaiting.setVisibility(View.INVISIBLE);
					btnStart.setText("重新配置");
					isConncting = false;
				}
			});
		}

		@Override
		public void onConnect(final ModuleInfo mi) {
			// TODO Auto-generated method stub
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					module = mi;
					ToastUtil.TextToastLong(Smartlink.this,"Device Type: " + module.getMid() + 
							"\nMAC:" + module.getMac() + "\nIP: " + module.getModuleIP());
				}
			});
		}

		private ControllerService controllerService = new ControllerService(Smartlink.this); //建Controller表
		private JackService jackService = new JackService(Smartlink.this);					 //建Jack表
		private SensorService sensorService = new SensorService(Smartlink.this);			 //建Sensor表
		private StatisticService statisticService  = new StatisticService(Smartlink.this);
		
		@Override
		public void onConnectOk() {
			// TODO Auto-generated method stub
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (controllerService.getCount() == 0) {
						if ((module.getMac().length() != 0)){
							
//							LauncherViewConfig.COUNT++;//����ǿձ��Ż��LauncherViewConfig.COUNT���ӣ�Ϊʲô��
							controllerService.init(module.getMac(), module.getModuleIP());
							jackService.init(module.getMac());
							sensorService.init(module.getMac());
							statisticService.init(module.getMac());
						}
					} else {
						boolean IsControllerExist = false;
						if (module != null) {
							
							if (controllerService.isMacExist(module.getMac())) {
								IsControllerExist = true;
							}
							
							if (IsControllerExist) {
								controllerService.modifyIp(module.getModuleIP(), module.getMac());
							} else {
								controllerService.init(module.getMac(), module.getModuleIP());
								jackService.init(module.getMac());
								sensorService.init(module.getMac());
								statisticService.init(module.getMac());
							}
						} else {
							Log.d("Smartlink.java", "null pointer exception maybe module=null");
						}
					}
					
					smManipulator.StopConnection();

					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//Version1.0
//					Launcher.handler.sendEmptyMessageDelayed(Const.UI_REFRESH, 3000);
					
					//Version1.1
//					startActivity(new Intent(Smartlink.this, Launcher.class));
					
					//Version2.0
					Intent intent = new Intent();
					intent.setClass(Smartlink.this, Launcher.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				
				}
			});
		}
	};

}