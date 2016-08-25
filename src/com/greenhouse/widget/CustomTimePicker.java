package com.greenhouse.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.greenhouse.R;
import com.greenhouse.ui.Timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class CustomTimePicker extends Activity implements OnDateChangedListener, OnTimeChangedListener {

//	private static String test = "1";
	
	private String TAG = "CustomTimePicker.java";
	public static boolean poweronIsSet = false;
	public static boolean poweroffIsSet = false;
	private static boolean hourIsChanged = false;
	private static boolean minuteIsChanged = false;
	private static int default_hour = 0;
	private static int default_minute = 0;

	public static int YY;

	public static boolean IsCircle = false;

	private NumberPicker numberPicker;
	private NumberPicker numberPicker1;
	private NumberPicker numberPicker2;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
//	private String transmit;
	public static String circle_time = "1";
//	private String powerontime_hour = "0";
	public static int x1_0 = 0;
	public static int x1 = 0;
//	private String powerontime_minute = "0";
	public static int x2 = 0;
//	private String powerofftime_hour = "0";
	public static int x3 = 0;
	public static int x3_0;
//	private String powerofftime_minute = "0";
	public static int x4 = 0;
	public static int x5 = 1;
	public static String poweronhour = "0";
	public static String poweronminute = "0";
	public static String poweroffhour = "0";
	public static String poweroffminute = "0";
	private String start_time = "0";
	private Activity activity;
//	public static String on_hour, on_minute, off_hour, off_minute, CIRCLE;
	public static int on_hour, on_minute, off_hour, off_minute, CIRCLE;

	// ʱ�䵯��ѡ��򡾹��캯����
	public CustomTimePicker(Activity activity) {
		
		// 20150907 �򿪹رյڶ�������ʱʱ��û������
		poweronhour = "0";
		poweronminute = "0";
		poweroffhour = "0";
		poweroffminute = "0";		
		this.activity = activity;
	}

	// ��ʼ��start time����ѡ����������ʱ��
	public void init(DatePicker datePicker, TimePicker timePicker) {
		Calendar calendar = Calendar.getInstance();
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH), this);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
	}

	// time setting��������1
	public AlertDialog NumberPickDialog1(final TextView powerOnTime) {
		// TODO Auto-generated method stub
		RelativeLayout CircleLayout = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.time_picker, null);
		numberPicker1 = (NumberPicker) CircleLayout.findViewById(R.id.pick1);
		numberPicker2 = (NumberPicker) CircleLayout.findViewById(R.id.pick2);

		if (poweroffIsSet) {
			numberPicker1.setMinValue(0);
			numberPicker1.setMaxValue(99);
			numberPicker1.setValue(default_hour);
			numberPicker2.setMinValue(0);
			numberPicker2.setMaxValue(59);
			numberPicker2.setValue(default_minute);
			poweroffIsSet = false;
		} else {
			numberPicker1.setMinValue(0);
			numberPicker1.setMaxValue(99);
			numberPicker1.setValue(0);
			numberPicker2.setMinValue(0);
			numberPicker2.setMaxValue(59);
			numberPicker2.setValue(0);
		}

		numberPicker1.setOnValueChangedListener(mTest1);
		numberPicker2.setOnValueChangedListener(mTest2);

		ad = new AlertDialog.Builder(activity)
				.setTitle("请设置起始时间:")
				.setView(CircleLayout)
				.setCancelable(false)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								default_hour = 0;
								default_minute = 0;
								// ���power onû���趨����ʾ��Ĭ��0��24-poweroff
								if (hourIsChanged && minuteIsChanged) {
									powerOnTime.setText(poweronhour + "H "
											+ poweronminute + "M");
									hourIsChanged = false;
									minuteIsChanged = false;
								} else if (minuteIsChanged) {
									poweronhour = default_hour + "";
									powerOnTime.setText(default_hour + "H "
											+ poweronminute + "M");
									minuteIsChanged = false;
								} else if (hourIsChanged) {
									poweronminute = default_minute + "";
									powerOnTime.setText(poweronhour + "H "
											+ default_minute + "M");
									hourIsChanged = false;
								} else {
									poweronhour = default_hour + "";
									poweronminute = default_minute + "";
									powerOnTime.setText(default_hour + "H "
											+ default_minute + "M");
									Log.i(TAG, "power on set 4" + default_hour
											+ default_minute);
								}

								x1_0 = Integer.parseInt(poweronhour); // poweronСʱ

								x1 = 60 * x1_0; // poweron Сʱת����
								
								on_hour = x1_0;
								
//								on_hour = Integer.toString(x1_0); // Сʱת���Ӻ�תstring����
//								if (x1_0 < 10) {
//									on_hour = "0" + on_hour;
//								}
								
								
								x2 = Integer.parseInt(poweronminute); // poweron����
								
								on_minute = x2;
								
								
//								on_minute = Integer.toString(x2); // poweron����תstring����
//								if (x2 < 10) {
//									on_minute = "0" + on_minute;
//								}

								if (x2 != 0) {
									default_hour = 23 - x1_0;
									default_minute = 60 - x2;
								} else {
									default_hour = 24 - x1_0;
									default_minute = x2;
								}

								poweronIsSet = true;
								Timer.poweronFlag = true;

							}
						})
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								default_hour = 0;
								default_minute = 0;
								powerOnTime.setText("请设置");
							}
						}).show();
		return ad;
	}

	// time setting��������2
	public AlertDialog NumberPickDialog2(final TextView powerOffTime) {
		// TODO Auto-generated method stub

		RelativeLayout CircleLayout = (RelativeLayout) activity
				.getLayoutInflater().inflate(R.layout.time_picker, null);
		numberPicker1 = (NumberPicker) CircleLayout.findViewById(R.id.pick1);
		numberPicker2 = (NumberPicker) CircleLayout.findViewById(R.id.pick2);

		if (poweronIsSet) {
			numberPicker1.setMinValue(0);
			numberPicker1.setMaxValue(99);
			numberPicker1.setValue(default_hour);
			numberPicker2.setMinValue(0);
			numberPicker2.setMaxValue(59);
			numberPicker2.setValue(default_minute);

		} else {
			numberPicker1.setMinValue(0);
			numberPicker1.setMaxValue(99);
			numberPicker1.setValue(0);
			numberPicker2.setMinValue(0);
			numberPicker2.setMaxValue(59);
			numberPicker2.setValue(0);
		}

		numberPicker1.setOnValueChangedListener(mTest1);
		numberPicker2.setOnValueChangedListener(mTest2);

		ad = new AlertDialog.Builder(activity)
				.setTitle("请设置打开时间 :")
				.setView(CircleLayout)
				.setCancelable(false)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								if (hourIsChanged && minuteIsChanged) {
									Log.i(TAG, "power on set 1");
									powerOffTime.setText(poweroffhour + "H "	+ poweroffminute + "M");
									hourIsChanged = false;
									minuteIsChanged = false;
								} else if (minuteIsChanged) {
									Log.i(TAG, "power on set 2");
									poweroffhour = default_hour + "";
									powerOffTime.setText(default_hour + "H "+ poweroffminute + "M");
									minuteIsChanged = false;
								} else if (hourIsChanged) {
									Log.i(TAG, "power on set 3");
									poweroffminute = default_minute + "";
									powerOffTime.setText(poweroffhour + "H "	+ default_minute + "M");
									hourIsChanged = false;
								} else {
									poweroffhour = default_hour + "";
									poweroffminute = default_minute + "";
									powerOffTime.setText(default_hour + "H "	+ default_minute + "M");
								}

								x3_0 = Integer.parseInt(poweroffhour);
								x3 = 60 * x3_0;
								
								off_hour = x3_0;
								
//								off_hour = Integer.toString(x3_0);
//								if (x3_0 < 10) {
//									off_hour = "0" + off_hour;
//								}

								x4 = Integer.parseInt(poweroffminute);
								
								off_minute = x4;
								
//								off_minute = Integer.toString(x4);
//								if (x4 < 10) {
//									off_minute = "0" + off_minute;
//								}

								if (x4 != 0) {
									default_hour = 23 - x3_0;
									default_minute = 60 - x4;
								} else {
									default_hour = 24 - x3_0;
									default_minute = x4;
								}
								default_hour = 0;
								default_minute = 0;
								poweroffIsSet = true;
								Timer.poweroffFlag = true;

							}
						})
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								default_hour = 0;
								default_minute = 0;
								powerOffTime.setText("请设置");
							}
						}).show();

		return ad;
	}

	// circle time setting��������
	public AlertDialog NumberPickDialog3(final TextView circleTime) {
		// TODO Auto-generated method stub
		LinearLayout CircleLayout = (LinearLayout) activity.getLayoutInflater()	.inflate(R.layout.number_picker, null);
		numberPicker = (NumberPicker) CircleLayout.findViewById(R.id.pick);
		// 20150822ÿ�ζ�Ҫ��Ĭ��cycle index��ʼ��Ϊ0
		circle_time = "1";

		numberPicker.setMinValue(1);
		numberPicker.setMaxValue(99);
		numberPicker.setValue(1);
		numberPicker.setOnValueChangedListener(mTest);

		ad = new AlertDialog.Builder(activity)
				.setTitle("请设置循环次数:")
				.setView(CircleLayout)
				.setCancelable(false)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (IsCircle) {
									circleTime.setText(circle_time + " 次");
									IsCircle = false;
									
								} else {
									circleTime.setText("1 " + " 次");
									
								}

								x5 = Integer.parseInt(circle_time);
								CIRCLE = x5;
//								CIRCLE = Integer.toString(x5);
//								if (x5 < 10) {
//									CIRCLE = "0" + CIRCLE;
//								}
								
//								// 20150908 BUG ���������stoptime
//								int Y;
//								Y = TimerConfigure.calculate(x1, x2, x3, x4, x5);
//								YY = Y;
								
								Timer.circleFlag = true;

							}
						})
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								circleTime.setText("请设置");
								circle_time = "1";
								Timer.circleFlag = false;
							}
						}).show();
		return ad;
	}

	// ����timepicker��ʱ�䣬����onTimeChanged����ʱֵ�����ݵ�power_on_time
	// �����޸�ʱ������timepicker��������ע�͵�Ϊ�շ���
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

	}

	// ����hour�Ĵ���������onTimeChanged()
	private NumberPicker.OnValueChangeListener mTest1 = new OnValueChangeListener() {
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub

			poweronhour = newVal + "";
			poweroffhour = newVal + "";
//			test = "0" + test;
			hourIsChanged = true;
		}
	};
	// ����minute�Ĵ���������onTimeChanged()
	private NumberPicker.OnValueChangeListener mTest2 = new OnValueChangeListener() {
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			poweronminute = newVal + "";
			poweroffminute = newVal + "";
			minuteIsChanged = true;			
		}
	};

	// ����circletime�Ĵ���������onTimeChanged()
	private NumberPicker.OnValueChangeListener mTest = new OnValueChangeListener() {
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			IsCircle = true;
			circle_time = newVal + "";
			Timer.circleFlag = true;
		}
	};

	// ����datepicker��ʱ�䣬����onDateChanged����ʱֵ�����ݵ�start_time
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
				timePicker.getCurrentMinute());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		start_time = sdf.format(calendar.getTime());
		ad.setTitle(start_time);
	}

}
