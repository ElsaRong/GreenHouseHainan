package com.greenhouse.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import com.greenhouse.R;
import com.greenhouse.animation.HeavenAnimateView;
import com.greenhouse.database.SensorService;
import com.greenhouse.database.SourceDataManager;
import com.greenhouse.model.Jack;
import com.greenhouse.networkservice.NetworkManager;
import com.greenhouse.networkservice.SocketClientInit;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.networkservice.ThreadPoolManager;
import com.greenhouse.specialversion.ModifySpecificColumn;
import com.greenhouse.ui.JackFragmentList.OnJackFragmentListItemClickListener;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.OnFragmentRefreshInterface;
import com.greenhouse.viewconfig.LauncherViewConfig;

/** 
* @author    	Elsa 
* @Email     	elsarong715@gmail.com
* @date      	2015/11/24 AM11:08:11 
* @version      1.0  
* @description  
*/
public class JackFragmentMaster extends Activity 
	implements OnJackFragmentListItemClickListener, OnFragmentRefreshInterface{
	
	private static final String TAG = "JackFragmentMaster";
	
	private ThreadPoolManager threadPoolManager =  ThreadPoolManager.getInstance();
	private SensorService sensorService;

	private TextView localTextView;
	private ProgressBar title_waiting;
	private TextView text_waiting;
	
	public static List<Integer> listHistory = new ArrayList<Integer>(); //统计查询界面从数据库查询的历史记录List
	public static String FragmentFlag = "00";       //当前fragment标识,如果进入下一fragment前当前fragment开关测试(“10”),则发送退出报文
	public static boolean isQuery = false;
	private OnFragmentRefreshInterface mCallback = this;
	
	public static boolean isConnecting = false;
	public static int reconnNum = 0;
	public static boolean MCU_IS_STOP = false;
	
	
	private Handler handler = new Handler(); //handler.removeCallbacks
	
	//1s刷新一次时间显示和socket状态
	private Runnable refreshTitleBar = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			refreshHeavenView();
			refreshSocketConnect();
			handler.postDelayed(refreshTitleBar, 1000);
		}
	};
	
	//30秒刷新一次开关界面
	private Runnable refreshFragmentSwtich = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.onSwitchTestRefresh();
			}
			handler.postDelayed(refreshFragmentSwtich, 10000);
		}
	};
	
	private Runnable refreshFragmentShowinfo = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.onShowinfoRefresh();
			}
			handler.postDelayed(refreshFragmentShowinfo, 15000);
		}
	};
	
	
	private Runnable refreshFragmentEnvironment = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.onEnvironmentRefresh();
			}
			handler.postDelayed(refreshFragmentEnvironment, 1000);
		}
	};
	
	private void refreshSocketConnect() {
		if (MCU_IS_STOP) {
			MCU_IS_STOP = false;
			Intent intent = new Intent();
			intent.setClass(JackFragmentMaster.this, Launcher.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (Launcher.client == null) {
			title_waiting.setVisibility(View.VISIBLE);	
			text_waiting.setVisibility(View.VISIBLE);
			threadPoolManager.destroyClientTasks();
			if (!isConnecting) {
				reconnNum++;
				//超过30s重连失败就弹回主UI
				if (reconnNum < 30) {
					Log.e(TAG, "reconnNum=" + reconnNum);
					new AsyncSocketReReqTask().execute();			
				} else {
					reconnNum = 0;
					Intent intent = new Intent();
					intent.setClass(JackFragmentMaster.this, Launcher.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}
		} else {
			title_waiting.setVisibility(View.GONE);
			text_waiting.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 异步请求控制器或服务器，建立Socket连接
	 */
	class AsyncSocketReReqTask extends AsyncTask<Void, Integer, Integer> {	

		protected void onPreExecute() {
			isConnecting = true;
//			ToastUtil.TextToastShort(JackFragmentMaster.this, "异步重连Start");
		};
		
		//后台线程，建立连接
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub	
			Integer ret = Integer.valueOf(-1);
			
			if(NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_MOBILE) {
				Log.i(TAG, "NETWORK_MOBILE");
				// 20161125 special
//				return ret;
				ret = SocketClientInit.connAliyun();
			} else if(NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_WIFI) {
				Log.i(TAG, "NETWORK_WIFI");
				ret = SocketClientInit.connController();
			} else {
//				ToastUtil.TextToastShort(JackFragmentMaster.this, "请打开平板网络");
				return ret;
			}			
			
			if (ret != -1) {
				threadPoolManager.startClientTasks();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (Launcher.recvTIME) {
					ret = 0;
				}
			}
			return ret;		
		}
		
		protected void onPostExecute(Integer ret) {
			isConnecting = false;
			if (ret.intValue() == -1) {		
			} else {
				reconnNum = 0;
			}
		}
		
	}
	
	
	private void refreshHeavenView() {
		HeavenAnimateView localHeavenAnimateView = (HeavenAnimateView) findViewById(R.id.heaven);
		if (localHeavenAnimateView != null) {
			localHeavenAnimateView.update();
			localHeavenAnimateView.postInvalidate();
		}
	}

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		setContentView(R.layout.jack_layout);

		LauncherViewConfig.init(JackFragmentMaster.this);
	
		//修改一号柜命名
		if (Launcher.selectMac.equals("ACCF2357F44E")) {
//			txtController.setText("二号棚");
			ModifySpecificColumn.modifyControllerJackNameTest2(Launcher.selectMac);
		} else if (Launcher.selectMac.equals("ACCF236FA948")) {
//			txtController.setText("一号棚");
			ModifySpecificColumn.modifyControllerJackNameTest1(Launcher.selectMac);
		} else {
		}
		
//		ModifySpecificColumn.modifyControllerJackNameTest1(Launcher.selectMac);
		
		
		// title_bar theme
		localTextView = (TextView) findViewById(R.id.title);
		localTextView.setText("控制柜" );
		
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting);
		text_waiting = (TextView)findViewById(R.id.text_waiting);
		
		ImageView localImageView = (ImageView) findViewById(R.id.title_btn);
		localImageView.setImageResource(R.drawable.go_back);
		localImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(JackFragmentMaster.this).setTitle("提示")
				.setMessage(
						"离开此界面，和控制器的连接也会断开，确认离开?")
				.setNegativeButton("否",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int which) {
								// TODO Auto-generated method stub
							}
						})
				.setPositiveButton("是",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int whichButton) {
								
								handler.removeCallbacks(refreshTitleBar);
								handler.removeCallbacks(refreshFragmentShowinfo);
								handler.removeCallbacks(refreshFragmentSwtich);
								
								threadPoolManager.destroyClientTasks();
								
								Intent intent = new Intent(JackFragmentMaster.this, Launcher.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
							}
						}).show();
			}
		});		
		setDefaultFragment();
		handler.post(refreshTitleBar);
		handler.post(refreshFragmentSwtich);
		handler.post(refreshFragmentShowinfo);
		handler.post(refreshFragmentEnvironment);
	}
	
	// 20160818 暂时注释不知何用
//	public void clearSensorsValueAndOnline() {
//		SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
//	}
	
	
	public void setDefaultFragment() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		FragmentFlag = "00";
		JackFragmentList jackFragmentList = new JackFragmentList();
		fragmentTransaction.add(R.id.jack_fragment_list_container, jackFragmentList);
		fragmentTransaction.commit();
		refreshShowinfo();
//		//后面activity崩溃跳回该界面，会出现index＝null错误，因此在这里防呆Launcher.selectmac!=null
//		List<Jack> jackList = new ArrayList<Jack>();
//		jackList = SourceDataManager.initJackInfoList(JackFragmentMaster.this);
//		JackFragmentShowinfo jackFragmentRecyclerView = new JackFragmentShowinfo(JackFragmentMaster.this, jackList);
//		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentRecyclerView);		
//		fragmentTransaction.commit();
	}

	public void IsSwitchTest() {
		final String msg = "HFUT" + Launcher.selectMac + "OUTT00000000000000000000WANG";
		SocketOutputTask.sendMsgQueue.addLast(msg);
		SocketOutputTask.sendMsgQueue.addLast(msg);
		SocketOutputTask.sendMsgQueue.addLast(msg);
		SocketOutputTask.sendMsgQueue.addLast(msg);
		SocketOutputTask.sendMsgQueue.addLast(msg);
	}

	
	
	private void refreshEnvironment() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		localTextView.setText("环境参数" );
		sensorService = new SensorService(JackFragmentMaster.this);
		JackFragmentEnvironment.sensors = sensorService.getAllSensor();
		JackFragmentEnvironment jackFragmentEnvironment = new JackFragmentEnvironment();
		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentEnvironment);			
		fragmentTransaction.commit();
	}
	
	private void refreshShowinfo() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		localTextView.setText("全部设备" );
		List<Jack> jackList = new ArrayList<Jack>();
		jackList = SourceDataManager.initJackInfoList();
		JackFragmentShowinfo jackFragmentShowinfo = new JackFragmentShowinfo(JackFragmentMaster.this);
		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentShowinfo);
		fragmentTransaction.commit();
	}
	
	private void refreshSwitchTest() {
		localTextView.setText("开关测试" );
		FragmentManager fragmentManager = getFragmentManager();
		final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		JackFragmentSwitchTest jackFragmentSwitchTest = new JackFragmentSwitchTest();
		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentSwitchTest);			
		fragmentTransaction.commit();
	}
	
	private void initSwitchTest() {
		FragmentManager fragmentManager = getFragmentManager();
		final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		localTextView.setText("开关测试" );
		new AlertDialog.Builder(JackFragmentMaster.this).setTitle("提示")
		.setMessage("进入开关测试，控制柜上所有已绑定任务将暂时停止，确认进入？")
		.setNegativeButton("否",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,	int which) {
					}
				})
		.setPositiveButton("是",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,	int whichButton) {
						
						final String msg = "HFUT" + Launcher.selectMac + "INTE00000000000000000000WANG";	
						
						SocketOutputTask.sendMsgQueue.addLast(msg);
						SocketOutputTask.sendMsgQueue.addLast(msg);
						SocketOutputTask.sendMsgQueue.addLast(msg);
						SocketOutputTask.sendMsgQueue.addLast(msg);
						SocketOutputTask.sendMsgQueue.addLast(msg);
						SocketOutputTask.sendMsgQueue.addLast(msg);
						SocketOutputTask.sendMsgQueue.addLast(msg);
						SocketOutputTask.sendMsgQueue.addLast(msg);
						
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						JackFragmentSwitchTest jackFragmentSwitchTest = new JackFragmentSwitchTest();
						
						fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentSwitchTest);			
						fragmentTransaction.commit();
					}
		}).show();
	}
	
	private void refreshTimeSet() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		localTextView.setText("时间设置" );
		JackFragmentTimeSet jackFragmentTimeSet = new JackFragmentTimeSet(handler);
		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentTimeSet);			
		fragmentTransaction.commit();
	}
	
	private void refreshThresholdSet() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		localTextView.setText("阈值设置" );
		JackFragmentThresholdSet jackFragmentThresholdSet = new JackFragmentThresholdSet();
		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentThresholdSet);			
		fragmentTransaction.commit();
	}
	
	private void refreshModeset() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		localTextView.setText("模式设置" );
		List<Jack> jackModeSetList = SourceDataManager.initJackSwitchInfoList();
		JackFragmentModeSet jackFragmentModeSet = new JackFragmentModeSet(JackFragmentMaster.this, jackModeSetList);
		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentModeSet);			
		fragmentTransaction.commit();
	}
	
	private void refreshStatistics() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		localTextView.setText("统计查询" );
		JackFragmentStatistics jackFragmentStatistics = new JackFragmentStatistics();
		fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentStatistics);			
		fragmentTransaction.commit();
	}
	
	@Override
	public void onJackFragmentListItemClick(String msg) {
		// TODO Auto-generated method stub		
		// 环境参数
		if(msg.equals("01")) {
			IsSwitchTest();
			FragmentFlag="01";
			refreshEnvironment();
		} 
		// 全部设备
		else if (msg.equals("00")) {
			IsSwitchTest();
			FragmentFlag="00";
			refreshShowinfo();
		}
		// 开关测试
		else if(msg.equals("10")) {
			IsSwitchTest();
			FragmentFlag="10";
			initSwitchTest();	
		}
		// 时间设置
		else if(msg.equals("20")) {
			IsSwitchTest();
			FragmentFlag="20";
			refreshTimeSet();
		}
		// 阈
		else if(msg.equals("21")) {
			IsSwitchTest();
			FragmentFlag="21";
			
			refreshThresholdSet();
		}
		// 模式设置
		else if (msg.equals("22") ) {
			IsSwitchTest();
			FragmentFlag="22";
			refreshModeset();			
		}
		//每次选中查询类型后，（1）清空查询记录的全局静态数组变量（2）跳转
		else if(msg.equals("30")){
			IsSwitchTest();
			FragmentFlag="30";
			refreshStatistics();
		} else if (msg.equals("31")) {
			IsSwitchTest();
			FragmentFlag="31";
			refreshStatistics();
		} else if(msg.equals("32")) {
			IsSwitchTest();
			FragmentFlag="32";
			refreshStatistics();
		} else if(msg.equals("33")) {
			IsSwitchTest();
			FragmentFlag="33";
			refreshStatistics();
		} else if (msg.equals("34")) {
			IsSwitchTest();
			FragmentFlag="34";
			refreshStatistics();
		} else if(msg.equals("35")) {
			IsSwitchTest();
			FragmentFlag="35";
			refreshStatistics();
		} else if(msg.equals("36")) {
			IsSwitchTest();
			FragmentFlag="36";
			refreshStatistics();
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(JackFragmentMaster.this).setTitle("提示")
			.setMessage(
					"离开此界面，和控制器的连接也会断开，确认离开？")
			.setNegativeButton("否",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int which) {
							// TODO Auto-generated method stub
						}
					}).setPositiveButton("是",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int whichButton) {
							
							Launcher.BACK_TO_LAUNCHER = true;
							
							handler.removeCallbacks(refreshTitleBar);
							handler.removeCallbacks(refreshFragmentShowinfo);
							handler.removeCallbacks(refreshFragmentSwtich);
							
							threadPoolManager.destroyClientTasks();
							
							Intent intent = new Intent(JackFragmentMaster.this, Launcher.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							
							finish();//窗体泄漏
						}
					}).show();

			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub	
		Log.d(TAG, "onDestroy");
//		unregisterReceiver(myReceiver);	
		handler.removeCallbacks(refreshTitleBar);
		handler.removeCallbacks(refreshFragmentShowinfo);
		handler.removeCallbacks(refreshFragmentSwtich);
		super.onDestroy();	
	}

	@Override
	public void onSwitchTestRefresh() {
		// TODO Auto-generated method stub
//		Log.d(TAG, "onRefresh switchtest");
		if (JackFragmentSwitchTest.adapter != null) {
			JackFragmentSwitchTest.jackSwitchInfoList = SourceDataManager.initJackSwitchInfoList();
			JackFragmentSwitchTest.adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onShowinfoRefresh() {
		// TODO Auto-generated method stub
//		Log.d(TAG, "onRefresh showinfo");
		if (JackFragmentShowinfo.adapter != null) {
			JackFragmentShowinfo.jackInfoList = SourceDataManager.initJackInfoList();
			JackFragmentShowinfo.adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onEnvironmentRefresh() {
		// TODO Auto-generated method stub
//		Log.d(TAG, "onRefresh environment");
		if (JackFragmentEnvironment.adapter != null) {
			JackFragmentEnvironment.sensors = SourceDataManager.initEnvironmentList();
			JackFragmentEnvironment.adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onSoiltempClicked() {
		// TODO Auto-generated method stub
		refreshStatistics();
	}

	@Override
	public void onSoilhumClicked() {
		// TODO Auto-generated method stub
		refreshStatistics();
	}

	@Override
	public void onSoilphClicked() {
		// TODO Auto-generated method stub
		refreshStatistics();
	}


	@Override
	public void onAirtempClicked() {
		// TODO Auto-generated method stub
		refreshStatistics();
	}


	@Override
	public void onAirhumClicked() {
		// TODO Auto-generated method stub
		refreshStatistics();
	}


	public void onCo2Clicked() {
		// TODO Auto-generated method stub
		refreshStatistics();
	}

	@Override
	public void onIllumClicked() {
		// TODO Auto-generated method stub
		refreshStatistics();
	}
	
	
	
}
