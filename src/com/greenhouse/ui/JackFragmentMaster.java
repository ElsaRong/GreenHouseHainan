package com.greenhouse.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import com.greenhouse.R;
import com.greenhouse.database.JackService;
import com.greenhouse.database.SensorService;
import com.greenhouse.networkservice.NetBroadcastReceiver;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.networkservice.ThreadPoolManager;
import com.greenhouse.specialversion.ModifySpecificColumn;
import com.greenhouse.ui.JackFragmentList.OnJackFragmentListItemClickListener;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;
import com.greenhouse.viewconfig.LauncherViewConfig;

/** 
* @author    	Elsa 
* @Email     	elsarong715@gmail.com
* @date      	2015/11/24 AM11:08:11 
* @version      1.0  
* @description  
*/
public class JackFragmentMaster extends Activity implements OnJackFragmentListItemClickListener{
	
	private static final String TAG = "JackFragmentMaster.java";
	
	private JackService jackService;
	private SensorService sensorService;

	public BroadcastReceiver myReceiver;
	
	private static ProgressBar title_waiting;
	
	private TextView localTextView;
	
	public static List<Integer> listHistory = null; //统计查询界面从数据库查询的历史记录List
	public static String FragmentFlag = "00";       //当前fragment标识,如果进入下一fragment前当前fragment开关测试(“10”),则发送退出报文
	
	/**
	 * 负责插座界面和统计查询界面的handler处理
	 */
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			//重新
			case Const.SOCKET_RECONNECTED:
				title_waiting.setVisibility(View.GONE);
				SocketInputTask.getHandler().sendEmptyMessage(Const.SOCKET_CONNECTED);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SocketOutputTask.getHandler().sendEmptyMessage(Const.TIME);
				handler.sendEmptyMessageDelayed(Const.BACK_TO_LAUNCHER, 2000);
				break;
			//开启Socket服务端监听线程
			case Const.TIME_SERVICE_FINISHED:
				ThreadPoolManager.getInstance().startSocketServerAccept();
				break;
			//返回主界面，弹出Toast，销毁client和server对象
			case Const.BACK_TO_LAUNCHER:
				ToastUtil.TextToastLong(JackFragmentMaster.this, "网络异常");
				Launcher.client.setState(Const.SOCKET_DISCONNECTED);
				Launcher.server.setServerState(Const.SOCKET_DISCONNECTED);
				Launcher.client.destroy();
				Launcher.server.destroy();
				Intent intent = new Intent(JackFragmentMaster.this, Launcher.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;	
			//每次收到刷新指定查询类型的UI后，（1）点击“查询”，已经未查询记录的全局静态数组变量赋值了（2）跳转
			case Const.UI_REFRESH_FRAG_30: //用来刷新统计查询界面
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				JackFragmentStatistics jackFragmentStatistics = new JackFragmentStatistics("30",handler);
				fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentStatistics);			
				fragmentTransaction.commit();
				break;
			case Const.UI_REFRESH_FRAG_31: //用来刷新统计查询界面
				FragmentManager fragmentManager1 = getFragmentManager();
				FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
				JackFragmentStatistics jackFragmentStatistics1 = new JackFragmentStatistics("31",handler);
				fragmentTransaction1.replace(R.id.jack_fragment_container, jackFragmentStatistics1);			
				fragmentTransaction1.commit();
				break;
			case Const.UI_REFRESH_FRAG_32: //用来刷新统计查询界面
				FragmentManager fragmentManager2 = getFragmentManager();
				FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
				JackFragmentStatistics jackFragmentStatistics2 = new JackFragmentStatistics("32",handler);
				fragmentTransaction2.replace(R.id.jack_fragment_container, jackFragmentStatistics2);			
				fragmentTransaction2.commit();
				break;
			case Const.UI_REFRESH_FRAG_33: //用来刷新统计查询界面
				FragmentManager fragmentManager3 = getFragmentManager();
				FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
				JackFragmentStatistics jackFragmentStatistics3 = new JackFragmentStatistics("33",handler);
				fragmentTransaction3.replace(R.id.jack_fragment_container, jackFragmentStatistics3);			
				fragmentTransaction3.commit();
				break;
			case Const.UI_REFRESH_FRAG_34: //用来刷新统计查询界面
				FragmentManager fragmentManager4 = getFragmentManager();
				FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
				JackFragmentStatistics jackFragmentStatistics4 = new JackFragmentStatistics("34",handler);
				fragmentTransaction4.replace(R.id.jack_fragment_container, jackFragmentStatistics4);			
				fragmentTransaction4.commit();
				break;
			case Const.UI_REFRESH_FRAG_35: //用来刷新统计查询界面
				FragmentManager fragmentManager5 = getFragmentManager();
				FragmentTransaction fragmentTransaction5 = fragmentManager5.beginTransaction();
				JackFragmentStatistics jackFragmentStatistics5 = new JackFragmentStatistics("35",handler);
				fragmentTransaction5.replace(R.id.jack_fragment_container, jackFragmentStatistics5);			
				fragmentTransaction5.commit();
				break;
			case Const.UI_REFRESH_FRAG_36: //用来刷新统计查询界面
				FragmentManager fragmentManager6 = getFragmentManager();
				FragmentTransaction fragmentTransaction6 = fragmentManager6.beginTransaction();
				JackFragmentStatistics jackFragmentStatistics6 = new JackFragmentStatistics("36",handler);
				fragmentTransaction6.replace(R.id.jack_fragment_container, jackFragmentStatistics6);			
				fragmentTransaction6.commit();
			case Const.UI_REFRESH_FRAG_TIMESET: //用来刷新统计查询界面
				FragmentManager fragmentManager7 = getFragmentManager();
				FragmentTransaction fragmentTransaction7 = fragmentManager7.beginTransaction();
				JackFragmentTimeSet jackFragmentTimeSet = new JackFragmentTimeSet(handler);
				fragmentTransaction7.replace(R.id.jack_fragment_container, jackFragmentTimeSet);			
				fragmentTransaction7.commit();
				break;
			case Const.UI_REFRESH_FRAG_THRESET: //用来刷新统计查询界面
				FragmentManager fragmentManager8 = getFragmentManager();
				FragmentTransaction fragmentTransaction8 = fragmentManager8.beginTransaction();
				JackFragmentThresholdSet jackFragmentThresholdSet = new JackFragmentThresholdSet(handler);
				fragmentTransaction8.replace(R.id.jack_fragment_container, jackFragmentThresholdSet);			
				fragmentTransaction8.commit();
				break;
			default:
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		setContentView(R.layout.jack_layout);

		LauncherViewConfig.init(JackFragmentMaster.this);
	
		
		/*各种定制版
		// Special Version 修改2号棚
		//ModifySpecificColumn.modifyControllerJackNameTest2("ACCF2357F44E");
//		Special Version 修改1号棚
//		ModifySpecificColumn.modifyControllerJackNameTest1("ACCF236FA948");
		// Special Version 修改测试控制�?
//		modifyjackname.modifyControllerJackDrawableTest1(Launcher.selectMac);
		//ModifySpecificColumn.modifyControllerJackNameTest1(Launcher.selectMac);
//		ModifySpecificColumn.modifyJackBund(Launcher.selectMac);
		 */
		
		// title_bar theme
		localTextView = (TextView) findViewById(R.id.title);
		if (Launcher.client.getTerminalType() == 1) {
			localTextView.setText("控制柜(本地)" );
		} else if (Launcher.client.getTerminalType() == 0) {
			localTextView.setText("控制柜(远程)" );
		} else {
			localTextView.setText("控制柜(测试)" );
		}
		
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting); 
		Log.d(TAG, "[onCreate]");
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction("com.greenhosue.backtolauncheraction");
        myReceiver = new NetBroadcastReceiver(handler);
        this.registerReceiver(myReceiver, filter);
		
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
								
								Launcher.BACK_TO_LAUNCHER = true;
								Launcher.client.setState(Const.SOCKET_DISCONNECTED);
								Launcher.server.setServerState(Const.SOCKET_DISCONNECTED);
								
								Launcher.client.destroy();
								Launcher.server.destroy();
								
								Intent intent = new Intent(JackFragmentMaster.this, Launcher.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
							}
						}).show();
			}
		});		
		setDefaultFragment();	
	}
	
	// 20160818 暂时注释不知何用
//	public void clearSensorsValueAndOnline() {
//		SensorService sensorService = new SensorService(GreenHouseApplication.getContext());
//	}
	
	
	public void setDefaultFragment() {
		FragmentFlag = "00";
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();		
		JackFragmentList jackFragmentList = new JackFragmentList();
		//后面activity崩溃跳回该界面，会出现index＝null错误，因此在这里防呆
		if(Launcher.selectMac != null && Launcher.selectMac != "") {
			jackService = new JackService(JackFragmentMaster.this);
			JackFragmentShowinfo.jacks = jackService.getAllJack(Launcher.selectMac);   //插座基本信息的首次初始化
			JackFragmentShowinfo jackFragmentRecyclerView = new JackFragmentShowinfo();
			fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentRecyclerView);		
		}
		fragmentTransaction.add(R.id.jack_fragment_list_container, jackFragmentList);
		fragmentTransaction.commit();
	}

	public void IsSwitchTest() {
		if(FragmentFlag.equals("10")) {
			SocketOutputTask.getHandler().sendEmptyMessage(Const.OUTT);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SocketOutputTask.getHandler().sendEmptyMessage(Const.OUTT);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SocketOutputTask.getHandler().sendEmptyMessage(Const.OUTT);
		}
	}

	@Override
	public void onJackFragmentListItemClick(String msg) {
		
		// TODO Auto-generated method stub		
		FragmentManager fragmentManager = getFragmentManager();
		final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		// 环境参数
		if(msg.equals("01")) {
			IsSwitchTest();
			FragmentFlag="01";
			localTextView.setText("环境参数" );
			sensorService = new SensorService(JackFragmentMaster.this);
			JackFragmentEnvironment.sensors = sensorService.getAllSensor();
			JackFragmentEnvironment jackFragmentEnvironment = new JackFragmentEnvironment();
			fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentEnvironment);			
			fragmentTransaction.commit();
		} 
		// 全部设备
		else if (msg.equals("00")) {
			IsSwitchTest();
			FragmentFlag="00";
			localTextView.setText("全部设备" );
			jackService = new JackService(JackFragmentMaster.this);
			JackFragmentShowinfo.jacks = jackService.getAllJack(Launcher.selectMac); // 初始化JackFragmentShowinfo.jacks，插座当前信息
			JackFragmentShowinfo jackFragmentShowinfo = new JackFragmentShowinfo();
			fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentShowinfo);
			fragmentTransaction.commit();
		}
		// 开关测试
		else if(msg.equals("10")) {
			IsSwitchTest();
			FragmentFlag="10";
			localTextView.setText("开关测试" );
			
			new AlertDialog.Builder(JackFragmentMaster.this).setTitle("提示")
			.setMessage("进入开关测试，控制柜上所有已绑定任务将暂时停止，确认进入？")
			.setNegativeButton("否",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int which) {
							// TODO Auto-generated method stub
							jackService = new JackService(JackFragmentMaster.this);
							JackFragmentShowinfo.jacks = jackService.getAllJack(Launcher.selectMac); // 初始化JackFragmentShowinfo.jacks，插座当前信息
							JackFragmentShowinfo jackFragmentShowinfo = new JackFragmentShowinfo();
							fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentShowinfo);
							fragmentTransaction.commit();
						}
					})
			.setPositiveButton("是",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int whichButton) {
							SocketOutputTask.getHandler().sendEmptyMessage(Const.INTE);
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							SocketOutputTask.getHandler().sendEmptyMessage(Const.INTE);
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							SocketOutputTask.getHandler().sendEmptyMessage(Const.INTE);
							
							jackService = new JackService(JackFragmentMaster.this);
							JackFragmentSwitchTest.jacks = jackService.getAllJackNameAndState(Launcher.selectMac);
							JackFragmentSwitchTest jackFragmentSwitchTest = new JackFragmentSwitchTest();
							fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentSwitchTest);			
							fragmentTransaction.commit();
						}
					}).show();
			
			
			
		}
		// 时间设置
		else if(msg.equals("20")) {
			IsSwitchTest();
			FragmentFlag="20";
			localTextView.setText("时间设置" );
			JackFragmentTimeSet jackFragmentTimeSet = new JackFragmentTimeSet(handler);
			fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentTimeSet);			
			fragmentTransaction.commit();
		}
		// 阈�?�设�?
		else if(msg.equals("21")) {
			IsSwitchTest();
			FragmentFlag="21";
			localTextView.setText("阈值设置" );
			JackFragmentThresholdSet jackFragmentThresholdSet = new JackFragmentThresholdSet(handler);
			fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentThresholdSet);			
			fragmentTransaction.commit();
		}
		// 模式设置
		else if (msg.equals("22") ) {
			IsSwitchTest();
			FragmentFlag="22";
			localTextView.setText("模式设置" );
			jackService = new JackService(JackFragmentMaster.this);
			JackFragmentModeSet.jacks = jackService.getAllJackNameAndState(Launcher.selectMac);
			JackFragmentModeSet jackFragmentModeSet = new JackFragmentModeSet();
			fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentModeSet);			
			fragmentTransaction.commit();
		}
		//每次选中查询类型后，（1）清空查询记录的全局静态数组变量（2）跳转
		else if(msg.equals("30") || msg.equals("31") || msg.equals("32") || msg.equals("33") || msg.equals("34") || msg.equals("35") || msg.equals("36")){
			IsSwitchTest();
			FragmentFlag="30";
			JackFragmentMaster.listHistory = null;
			JackFragmentStatistics jackFragmentStatistics = new JackFragmentStatistics(msg,handler);
			fragmentTransaction.replace(R.id.jack_fragment_container, jackFragmentStatistics);			
			fragmentTransaction.commit();
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
							Launcher.client.setState(Const.SOCKET_DISCONNECTED);
							Launcher.server.setServerState(Const.SOCKET_DISCONNECTED);
							
							Launcher.client.destroy();
							Launcher.server.destroy();
							
							Intent intent = new Intent(JackFragmentMaster.this, Launcher.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							
							unregisterReceiver(myReceiver);
							handler.removeCallbacksAndMessages(null);
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
		unregisterReceiver(myReceiver);
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();	
	}
	

	

}
