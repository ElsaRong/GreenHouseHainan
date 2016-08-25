package com.greenhouse.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import android.app.Activity;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.greenhouse.R;
import com.greenhouse.database.JackService;
import com.greenhouse.model.Jack;
import com.greenhouse.networkservice.NetBroadcastReceiver;
import com.greenhouse.networkservice.SocketInputTask;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.networkservice.ThreadPoolManager;
import com.greenhouse.util.Const;
import com.greenhouse.util.TimerConfigure;
import com.greenhouse.util.ToastUtil;
import com.greenhouse.widget.CustomDatePicker;
import com.greenhouse.widget.CustomTimePicker;


public class Timer extends Activity {
	
	public static Queue<String> sendMsgQueue = new LinkedList<>();

	public static boolean poweronFlag = false;
	public static boolean poweroffFlag = false;
	public static boolean circleFlag = false;
	public static boolean startFlag = false;

	private TextView powerOnTime;
	private TextView powerOffTime;
	private TextView circleTime;
	private TextView startTime;

	private List<Map<String, String>> mMultiJack;
	private Iterator<Map<String, String>> iterator_timer;
	public static int[] chosedJackGroup = {0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0};
	public static String chosedJackGroupHex;

	public static long StopDate = 0;
	public static String stopdate = "0";
	
	private JackService jackService;
	
	private static ProgressBar title_waiting;
	
	public BroadcastReceiver myReceiver;
	
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Const.BACK_TO_LAUNCHER:
				ToastUtil.TextToastLong(Timer.this, "没有网络");
				Launcher.client.setState(Const.SOCKET_DISCONNECTED);
				Launcher.server.setServerState(Const.SOCKET_DISCONNECTED);
				Launcher.client.destroy();
				Launcher.server.destroy();
				Intent intent = new Intent(Timer.this, Launcher.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;	
			default:
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.timer);
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting); 
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction("com.greenhosue.backtolauncheraction");
        myReceiver = new NetBroadcastReceiver(handler);
        this.registerReceiver(myReceiver, filter);
		
		poweronFlag = false;
		poweroffFlag = false;
		mMultiJack = new ArrayList<Map<String, String>>();
		mMultiJack = AlarmclockListView.mMultiCheck;
		
		jackService = new JackService(this);

		iterator_timer = mMultiJack.iterator();


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
		
		// title_bar theme
		TextView localTextView = (TextView) findViewById(R.id.title);
		localTextView.setText(R.string.timer);
		
		// start_btn
		Button timer_start = (Button) findViewById(R.id.timer_btn);
		timer_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub			
				if (poweronFlag && poweroffFlag && circleFlag ) {
					
					if( (CustomTimePicker.on_hour + "" + CustomTimePicker.on_minute).equals("00") ){
						
						Toast.makeText(Timer.this, "打开时间不能为：0H0M !",	Toast.LENGTH_LONG).show();
						
					}  else {						
				
					if (startFlag) {
						CustomTimePicker.YY  = TimerConfigure.calculate(CustomTimePicker.x1, 
								CustomTimePicker.x2, CustomTimePicker.x3, CustomTimePicker.x4, CustomTimePicker.x5);
						
						stopdate = TimerConfigure.addDateMinut(CustomDatePicker.ZZ, CustomTimePicker.YY);
						poweronFlag = poweroffFlag = circleFlag = false;
						
						// 1-加入消息队列
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						
						AlarmclockListView.sAlarmListHandler.sendEmptyMessageDelayed(Const.UI_REFRESH, 300);
						Intent intent = new Intent();
						intent.setClass(Timer.this, AlarmclockListView.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						
					} else {

						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
						CustomDatePicker.Init_Start_time = sdf.format(calendar.getTime());

						CustomTimePicker.x1_0 = Integer.parseInt(CustomTimePicker.poweronhour);
						CustomTimePicker.x1 = 60 * CustomTimePicker.x1_0;
						CustomTimePicker.x2 = Integer.parseInt(CustomTimePicker.poweronminute);
						CustomTimePicker.x3_0 = Integer.parseInt(CustomTimePicker.poweroffhour);
						CustomTimePicker.x3 = 60 * CustomTimePicker.x3_0;
						CustomTimePicker.x4 = Integer.parseInt(CustomTimePicker.poweroffminute);
						CustomTimePicker.x5 = Integer.parseInt(CustomTimePicker.circle_time);
						CustomTimePicker.YY = TimerConfigure.calculate(CustomTimePicker.x1, CustomTimePicker.x2,CustomTimePicker.x3, CustomTimePicker.x4,CustomTimePicker.x5);

						stopdate = TimerConfigure.addDateMinut(CustomDatePicker.Init_Start_time,CustomTimePicker.YY);

						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						
						try {
							StopDate = sdf2.parse(stopdate).getTime();
							Log.i("Stop time = timeDate",	StopDate + "");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						poweronFlag = poweroffFlag = circleFlag= false;
						
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SocketOutputTask.getHandler().sendEmptyMessage(Const.TASK);	
						
						
						for (int i = 0; i < 48; i++) {
							Timer.chosedJackGroup[i] = 0;
							Timer.chosedJackGroupHex = null;
						}
						
						AlarmclockListView.sAlarmListHandler.sendEmptyMessageDelayed(Const.UI_REFRESH, 300);
						
						Intent intent = new Intent();
						intent.setClass(Timer.this, AlarmclockListView.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						
					}
				}

				} else {
					ToastUtil.TextToastShort(Timer.this, "请检查打开／关闭／循环是否正确设置");				
				}
			}
		});

		powerOnTime = (TextView) findViewById(R.id.power_on_time);
		powerOffTime = (TextView) findViewById(R.id.power_off_time);
		circleTime = (TextView) findViewById(R.id.circle_time);
		startTime = (TextView) findViewById(R.id.start_time);

		if (mMultiJack.size() > 1) {
			startTime.setText("00 : 00");
			powerOnTime.setText(" 00 : 00 ");
			powerOffTime.setText(" 00 : 00 ");
			circleTime.setText("0");
		} else {
			while (iterator_timer.hasNext()) {
				String chosedJack = iterator_timer.next().toString();										
				String jackId = chosedJack.substring(chosedJack.indexOf("=") + 1, chosedJack.indexOf("}"));
				Jack jack = jackService.queryJackIdTask(Integer.parseInt(jackId));
				startTime.setText(jack.getStart());
				powerOnTime.setText(jack.getPoweron());
				powerOffTime.setText(jack.getPoweroff());
				circleTime.setText(jack.getCycle()+"");
			}
		}

		startTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDatePicker mDatePickDialog = new CustomDatePicker(	Timer.this);
				mDatePickDialog.DatePickDialog(startTime);
			}
		});

		powerOnTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					CustomTimePicker mTimePickDialog1 = new CustomTimePicker(Timer.this);
					mTimePickDialog1.NumberPickDialog1(powerOnTime);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		powerOffTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomTimePicker mTimePickDialog2 = new CustomTimePicker(Timer.this);
				if (CustomTimePicker.poweronIsSet) {
					mTimePickDialog2.NumberPickDialog2(powerOffTime);					
				} else {
					Toast.makeText(Timer.this, "请正确设置打开时间", Toast.LENGTH_LONG).show();
				}
			}
		});

		circleTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomTimePicker numberPicker = new CustomTimePicker(Timer.this);
				numberPicker.NumberPickDialog3(circleTime);
			}
		});
	}



	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}


	public void intent2Launcher() {
		Intent intent = new Intent();
		intent.setClass(Timer.this, Launcher.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}


}
