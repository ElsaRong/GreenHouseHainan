package com.greenhouse.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.greenhouse.R;
import com.greenhouse.ui.JackFragmentStatistics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JackStatisticCustomDatePicker implements OnDateChangedListener{

	private DatePicker datePicker;
	private AlertDialog ad;
	private String start_time = "";
	private Activity activity;
	public static String sYear = "", sMonth = "" ,sDay = "";

	public JackStatisticCustomDatePicker(Activity activity) {
		this.activity = activity;
	}

	public void init(DatePicker datePicker) {
		Calendar calendar = Calendar.getInstance();	
		datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY),calendar.get(Calendar.DAY_OF_MONTH),this);
		datePicker.setCalendarViewShown(false);
		calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
	}

	public AlertDialog DatePickDialog(final TextView startDate2ndTime){
		JackFragmentStatistics.queryTimeIsSet = false;
		LinearLayout TimeLayout = (LinearLayout)View.inflate(activity, R.layout.jack_statistic_date_picker, null);
		datePicker = (DatePicker) TimeLayout.findViewById(R.id.datepicker);
		init(datePicker);
		ad = new AlertDialog.Builder(activity)
				.setTitle("设置查询起始时间")			
				.setView(TimeLayout)
				.setCancelable(false)
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						startDate2ndTime.setText(start_time);			
						JackFragmentStatistics.queryTimeIsSet = true;
					}
				})			
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startDate2ndTime.setText("请设置");
						JackFragmentStatistics.queryTimeIsSet = false;
					}
				}).show();				
		onDateChanged(null,0,0,0);					
		return ad;
	}
	
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		start_time = sdf.format(calendar.getTime());
		int a = datePicker.getYear();
		int b = datePicker.getMonth();
		int c = datePicker.getDayOfMonth();
		sYear = Integer.toString(a);
		sMonth = Integer.toString(b + 1);
		sDay = Integer.toString(c);
		if ((b+1) < 10) {
			sMonth = "0" + sMonth;
		}
		if (c < 10) {
			sDay = "0" + sDay;
		}		
		ad.setTitle(start_time);
	}
}
