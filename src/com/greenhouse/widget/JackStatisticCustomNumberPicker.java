package com.greenhouse.widget;

import com.greenhouse.R;
import com.greenhouse.ui.JackFragmentStatistics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class JackStatisticCustomNumberPicker {

	private AlertDialog ad;
	private NumberPicker numberPicker;
	private Activity activity;
	public static int timeSpan = 1;
	LinearLayout timeSpanLayout;
	
	public JackStatisticCustomNumberPicker(Activity activity) {
		this.activity = activity;
	}
	
	public AlertDialog NumberPickDialog(final TextView timeSpanView) {
		JackFragmentStatistics.querySpanIsSet = false;
		timeSpanLayout = (LinearLayout)View.inflate(activity, R.layout.number_picker, null);
		numberPicker = (NumberPicker) timeSpanLayout.findViewById(R.id.pick);
		numberPicker.setMinValue(1);
		numberPicker.setMaxValue(3);
		numberPicker.setValue(1);
		numberPicker.setOnValueChangedListener(onChange);
		ad = new AlertDialog.Builder(activity)
				.setTitle("设置查询时间跨度:")
				.setView(timeSpanLayout)
				.setCancelable(false)
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						timeSpanView.setText(timeSpan + "个月");
						JackFragmentStatistics.querySpanIsSet = true;
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						timeSpanView.setText("请设置");
						JackFragmentStatistics.querySpanIsSet = false;
					}
				}).show();
		return ad;
	}
	
	private NumberPicker.OnValueChangeListener onChange = new OnValueChangeListener() {
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			timeSpan = newVal;
		}
	};



}
