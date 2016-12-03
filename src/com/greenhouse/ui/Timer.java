package com.greenhouse.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.greenhouse.animation.HeavenAnimateView;
import com.greenhouse.database.JackService;
import com.greenhouse.model.Jack;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.util.DataFormatConversion;
import com.greenhouse.util.TimerConfigure;
import com.greenhouse.util.ToastUtil;
import com.greenhouse.widget.CustomDatePicker;
import com.greenhouse.widget.CustomTimePicker;


public class Timer extends Activity {
	
	private static final String TAG = "Timer";
	
	public static boolean poweronFlag = false;
	public static boolean poweroffFlag = false;
	public static boolean circleFlag = false;
	public static boolean startFlag = false;

	private TextView powerOnTime;
	private TextView powerOffTime;
	private TextView circleTime;
	private TextView startTime;
	
	private ProgressBar title_waiting;
	private TextView text_waiting;

	private List<Map<String, String>> mMultiJack = new ArrayList<Map<String, String>>();
	private Iterator<Map<String, String>> iterator_timer;
	public static int[] chosedJackGroup = {0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0};
	public static String chosedJackGroupHex;

	public static long StopDate = 0;
	public static String stopdate = "0";
	
	private JackService jackService;
	
private Handler handler = new Handler(); //handler.removeCallbacks
	
	private Runnable refreshTimer = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			refreshHeavenView();
			handler.postDelayed(refreshTimer, 1000);
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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.timer);
		
		poweronFlag = false;
		poweroffFlag = false;
		mMultiJack = AlarmclockListView.chooseJacks;
		
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
		
		title_waiting = (ProgressBar) findViewById(R.id.title_waiting);
		text_waiting = (TextView)findViewById(R.id.text_waiting);
		
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
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						
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
//							Log.i("Stop time = timeDate",	StopDate + "");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						poweronFlag = poweroffFlag = circleFlag= false;
						
						// 1-加入消息队列
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						SocketOutputTask.sendMsgQueue.addLast(createTASKmsg());
						
						for (int i = 0; i < 48; i++) {
							Timer.chosedJackGroup[i] = 0;
							Timer.chosedJackGroupHex = null;
						}
						
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
		
		handler.post(refreshTimer);
	}

	public byte[] createTASKmsg() {
		final String year, month, day, hour, minute, on_hour, on_minute, off_hour, off_minute, circle;
		year = "0" +Integer.toHexString(CustomDatePicker.Year);
		month = modifyFormat(Integer.toHexString(CustomDatePicker.Month));
		day = modifyFormat(Integer.toHexString(CustomDatePicker.Day));
		hour = modifyFormat(Integer.toHexString(CustomDatePicker.Hour));
		minute = modifyFormat(Integer.toHexString(CustomDatePicker.Minute));
		on_hour = modifyFormat(Integer.toHexString(CustomTimePicker.on_hour));
		on_minute = modifyFormat(Integer.toHexString(CustomTimePicker.on_minute));
		off_hour = modifyFormat(Integer.toHexString(CustomTimePicker.off_hour));
		off_minute = modifyFormat(Integer.toHexString(CustomTimePicker.off_minute));
		circle = "00" + modifyFormat(Integer.toHexString(CustomTimePicker.CIRCLE));
		
		final String msg = DataFormatConversion.IrreguStringToHexValue("HFUT" + Launcher.selectMac + "TASK") 
				+ year + month + day + hour + minute + on_hour + on_minute + off_hour + off_minute + circle
				+ Timer.chosedJackGroupHex + "0000"
				+ DataFormatConversion.IrreguStringToHexValue("WANG");
		
//		Log.e(TAG, "转换成字节前的TASK报文: "+msg);
		
		byte[] b = DataFormatConversion.HexStringToByte(msg);
		return b;
	}

private static String modifyFormat(String s) {
	if (s.length() == 1) {
		s = "0" + s;
		return s;
	} else {
		return s;
	}
}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(refreshTimer);
		super.onDestroy();
	}


	public void intent2Launcher() {
		Intent intent = new Intent();
		intent.setClass(Timer.this, Launcher.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}


}
