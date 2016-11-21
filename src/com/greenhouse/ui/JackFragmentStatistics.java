package com.greenhouse.ui;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import com.greenhouse.R;
import com.greenhouse.database.StatisticService;
import com.greenhouse.model.Jack;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.OnFragmentRefreshInterface;
import com.greenhouse.util.ToastUtil;
import com.greenhouse.widget.JackStatisticCustomDatePicker;
import com.greenhouse.widget.JackStatisticCustomNumberPicker;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JackFragmentStatistics extends Fragment {
	
	private static final String TAG = "JackFragmentStatistics";
	
	private TextView startTime;
	private TextView timeSpan;
	private Button btn_query;

	private XYMultipleSeriesDataset mDataset;
	private XYMultipleSeriesRenderer mRenderer;
	private XYSeries series;
	private GraphicalView mChartView;
	private LinearLayout statisticContainer;
	private View vw;
	private XYSeriesRenderer xyRenderer;
	
	public static boolean querySpanIsSet = false;
	public static boolean queryTimeIsSet = false;
	
	private OnFragmentRefreshInterface mCallback;
	
	private StatisticService statisticService = new StatisticService(GreenHouseApplication.getContext());
	
	/**
	 * 20160124 每刷新一次statistic界面，查询时间和跨度界面都会重新初始化，但是时间和跨度的值没有变
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vw = inflater.inflate(R.layout.jack_fragment_statistics, container, false);	
		if (!JackFragmentMaster.isQuery ) {
//			Log.e(TAG, "not query, clear");
			if (JackFragmentMaster.listHistory != null) {
				JackFragmentMaster.listHistory.clear();
			}
		} else {
//			Log.e(TAG, "query, result");
			JackFragmentMaster.isQuery = false;
		}
		mCallback = (OnFragmentRefreshInterface) getActivity();
		statisticContainer = (LinearLayout)vw.findViewById(R.id.statistic_container);		
		startTime = (TextView)vw.findViewById(R.id.start_time);
		timeSpan = (TextView) vw.findViewById(R.id.end_time);
		btn_query = (Button)vw.findViewById(R.id.btn_query);
		
		btn_query.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (querySpanIsSet && queryTimeIsSet) {
					querySpanIsSet = false;
					queryTimeIsSet = false;
					startTime.setText("请设置");
					timeSpan.setText("请设置");
					//为统计查询结果的全局静态变量赋值
					Integer year = Integer.parseInt(JackStatisticCustomDatePicker.sYear);   //向数据库传递的查询年份
					Integer month = Integer.parseInt(JackStatisticCustomDatePicker.sMonth); //向数据库传递的查询月份
//					Integer day = Integer.parseInt(JackStatisticCustomDatePicker.sDay);
					Integer time_span = Integer.valueOf(JackStatisticCustomNumberPicker.timeSpan);
					Integer sensoryType = 1+Integer.parseInt(JackFragmentMaster.FragmentFlag.substring(1, 2));   //向数据库传递的查询传感器类型1-7
					Log.e(TAG, "History value: month=" + month + ", sensorType=" + sensoryType + ", time_span=" + time_span);
					
					JackFragmentMaster.isQuery = true;
					JackFragmentMaster.listHistory = statisticService.getHistoryValue(month, sensoryType, time_span);
					
					if (JackFragmentMaster.FragmentFlag.equals("30")) {
						mCallback.onSoiltempClicked();
					} else if (JackFragmentMaster.FragmentFlag.equals("31")) {
						mCallback.onSoilhumClicked();
					} else if (JackFragmentMaster.FragmentFlag.equals("32")) {
						mCallback.onSoilphClicked();
					} else if (JackFragmentMaster.FragmentFlag.equals("33")) {
						mCallback.onAirtempClicked();
					} else if (JackFragmentMaster.FragmentFlag.equals("34")) {
						mCallback.onAirhumClicked();
					} else if (JackFragmentMaster.FragmentFlag.equals("35")) {
						mCallback.onCo2Clicked();
					} else if (JackFragmentMaster.FragmentFlag.equals("36")) {
						mCallback.onIllumClicked();
					} 
				} else {
					ToastUtil.TextToastShort(getActivity(), "请设置查询起始时间和时间跨度");
				}
			}			
		});
		
		startTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				JackStatisticCustomDatePicker mDatePickDialog = new JackStatisticCustomDatePicker(getActivity());
				mDatePickDialog.DatePickDialog(startTime);
			}
		});
		
		timeSpan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JackStatisticCustomNumberPicker mDatePickDialog = new JackStatisticCustomNumberPicker(getActivity());
				mDatePickDialog.NumberPickDialog(timeSpan);
			}
		});
		
		mRenderer = new XYMultipleSeriesRenderer();
		InitRenderer();
		mDataset = new XYMultipleSeriesDataset();
		InitDataset();
		return vw;
	}
	
	private void InitRenderer() {
		xyRenderer = new XYSeriesRenderer();
		xyRenderer.setColor(Color.BLACK);
		xyRenderer.setPointStyle(PointStyle.CIRCLE);
		xyRenderer.setFillPoints(true);
		xyRenderer.setLineWidth(1);
		mRenderer.addSeriesRenderer(xyRenderer);
		mRenderer.setShowGrid(true);
		mRenderer.setMarginsColor(Color.argb(0, 0xff, 0, 0));
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setBackgroundColor(Color.TRANSPARENT);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setXAxisMin(0);
		mRenderer.setXAxisMax(60*24);//每天24个点，每月24＊30个点
		mRenderer.setXLabels(24);
		mRenderer.setPanLimits(new double[] { 0, 300, 0, 100 }); //设置拖动时X轴Y轴允许的�?大�?�最小�??.  
		mRenderer.setAxesColor(Color.rgb(242, 103, 16));
		mRenderer.setLabelsColor(Color.rgb(25, 110, 172));
		mRenderer.setLabelsTextSize(20); //横纵坐标字体单位大小
		mRenderer.setPointSize(2);
		mRenderer.setChartTitleTextSize(28);
		mRenderer.setShowLegend(true);
	}
	
	public void InitDataset() {
		if (JackFragmentMaster.FragmentFlag.equals("30")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("土壤温度" + " [查询起始时间:" + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度:"  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[1]土壤温度－历史曲线");
		} else if (JackFragmentMaster.FragmentFlag.equals("31")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("土壤湿度" + " [查询起始时间:" + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度:"  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[2]土壤湿度－历史曲线");
		} else if (JackFragmentMaster.FragmentFlag.equals("32")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("土壤酸酸碱度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[3]土壤酸碱度－历史曲线");
		} else if (JackFragmentMaster.FragmentFlag.equals("33")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(50);
			series = new XYSeries("空气温度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[4]空气温度－历史曲线");
		} else if (JackFragmentMaster.FragmentFlag.equals("34")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("空气湿度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[5]空气湿度－历史曲线");
		} else if (JackFragmentMaster.FragmentFlag.equals("35")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(1000);
			series = new XYSeries("CO2浓度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[6]CO2浓度－历史曲线");
		} else if (JackFragmentMaster.FragmentFlag.equals("36")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(50000);
			series = new XYSeries("光照强度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[7]光照强度－历史曲线");
		}
		
		if (JackFragmentMaster.listHistory != null) {
			Log.e(TAG, "listHistory.size = " + JackFragmentMaster.listHistory.size());
			for (int i = 1; i < JackFragmentMaster.listHistory.size(); i++) {
				Integer intValue = Integer.valueOf(JackFragmentMaster.listHistory.get(i));
				Log.e(TAG, "series.add(" + i + "," + Integer.valueOf(intValue) + ")");
				series.add((double)i, (double)intValue);
			}
		}
		mDataset.addSeries(series);
		mChartView = ChartFactory.getLineChartView(getActivity(), mDataset, mRenderer);
		mChartView.setId(0);
		statisticContainer.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	}
	
	
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);	
	}

	
}
