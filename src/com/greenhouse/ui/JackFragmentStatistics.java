package com.greenhouse.ui;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import com.greenhouse.R;
import com.greenhouse.database.JackService;
import com.greenhouse.database.SensorService;
import com.greenhouse.database.StatisticService;
import com.greenhouse.model.Jack;
import com.greenhouse.model.SocketClient;
import com.greenhouse.ui.JackFragmentList.OnJackFragmentListItemClickListener;
import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.ToastUtil;
import com.greenhouse.widget.JackStatisticCustomDatePicker;
import com.greenhouse.widget.JackStatisticCustomNumberPicker;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/** 
* @author ：Elsa 
* @Email �?? elsarong715@gmail.com
* @date �??2015�??11�??26�?? 下午4:19:29 
* @version 1.0  
* @description �?? 
*/
public class JackFragmentStatistics extends Fragment {
	
	private static final String TAG = "JackFragmentStatistics";
	
	private TextView startTime;
	private TextView timeSpan;
	private Button btn_query;
	private String QUERY_TYPE = null;

	private XYMultipleSeriesDataset mDataset;
	private XYMultipleSeriesRenderer mRenderer;
	private XYSeries series;
	private GraphicalView mChartView;
	private LinearLayout statisticContainer;
	private View vw;
	
	public static boolean querySpanIsSet = false;
	public static boolean queryTimeIsSet = false;
	
	private StatisticService statisticService = new StatisticService(GreenHouseApplication.getContext());
	
	
	private Handler handler;
	
	public static SocketClient queryClient = new SocketClient();
	
	
	/**
	 * 20160124 每刷新一次statistic界面，查询时间和跨度界面都会重新初始化，但是时间和跨度的值没有变
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vw = inflater.inflate(R.layout.jack_fragment_statistics, container, false);	
		statisticContainer = (LinearLayout)vw.findViewById(R.id.statistic_container);		
		startTime = (TextView)vw.findViewById(R.id.start_time);
		timeSpan = (TextView) vw.findViewById(R.id.end_time);
		btn_query = (Button)vw.findViewById(R.id.btn_query);
		
		btn_query.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (querySpanIsSet && queryTimeIsSet) {
					/* 不采用socket方式查询
					handler.sendEmptyMessage(Const.SOCKET_CONNECTING);
					new AsyncQueryTask(handler, series, mDataset).execute();
					*/
					querySpanIsSet = false;
					queryTimeIsSet = false;
					startTime.setText("请设置");
					timeSpan.setText("请设置");
					//为统计查询结果的全局静态变量赋值
					Integer year = Integer.parseInt(JackStatisticCustomDatePicker.sYear);   //向数据库传递的查询年份
					Integer month = Integer.parseInt(JackStatisticCustomDatePicker.sMonth); //向数据库传递的查询月份
					Integer day = Integer.parseInt(JackStatisticCustomDatePicker.sDay);
					Integer sensoryType = 1+Integer.parseInt(QUERY_TYPE.substring(1, 2));   //向数据库传递的查询传感器类型1-7
					JackFragmentMaster.listHistory = statisticService.getHistoryValue(month, day, sensoryType);
					
					//向Master发送UI刷新的msg
					if (QUERY_TYPE.equals("30")) {
						handler.sendEmptyMessage(Const.UI_REFRESH_FRAG_30); 
					} else if (QUERY_TYPE.equals("31")) {
						handler.sendEmptyMessage(Const.UI_REFRESH_FRAG_31); 
					} else if (QUERY_TYPE.equals("32")) {
						handler.sendEmptyMessage(Const.UI_REFRESH_FRAG_32); 
					} else if (QUERY_TYPE.equals("33")) {
						handler.sendEmptyMessage(Const.UI_REFRESH_FRAG_33); 
					} else if (QUERY_TYPE.equals("34")) {
						handler.sendEmptyMessage(Const.UI_REFRESH_FRAG_34); 
					} else if (QUERY_TYPE.equals("35")) {
						handler.sendEmptyMessage(Const.UI_REFRESH_FRAG_35); 
					} else if (QUERY_TYPE.equals("36")) {
						handler.sendEmptyMessage(Const.UI_REFRESH_FRAG_36); 
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
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
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
//		mRenderer.setXLabels(1000);
//		mRenderer.setXAxisMin(0);
		mRenderer.setXAxisMax(1000);
//		mRenderer.setYLabels(25);
		mRenderer.setPanLimits(new double[] { 0, 300, 0, 100 }); //设置拖动时X轴Y轴允许的�?大�?�最小�??.  
		mRenderer.setAxesColor(Color.rgb(242, 103, 16));
		mRenderer.setLabelsColor(Color.rgb(25, 110, 172));
		mRenderer.setLabelsTextSize(20);
		mRenderer.setPointSize(1);
		mRenderer.setChartTitleTextSize(28);
		mRenderer.setShowLegend(true);
	}
	
	public void InitDataset() {
		if (QUERY_TYPE.equals("30")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("土壤温度" + " [查询起始时间:" + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度:"  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[1]土壤温度－历史曲线");
		} else if (QUERY_TYPE.equals("31")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("土壤温度" + " [查询起始时间:" + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度:"  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[2]土壤湿度－历史曲线");
		} else if (QUERY_TYPE.equals("32")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("土壤酸酸碱度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[3]土壤酸碱度－历史曲线");
		} else if (QUERY_TYPE.equals("33")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(50);
			series = new XYSeries("空气温度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[4]空气温度－历史曲线");
		} else if (QUERY_TYPE.equals("34")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(100);
			series = new XYSeries("空气湿度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[5]空气湿度－历史曲线");
		} else if (QUERY_TYPE.equals("35")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(1000);
			series = new XYSeries("CO2浓度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[6]CO2浓度－历史曲线");
		} else if (QUERY_TYPE.equals("36")) {
			mRenderer.setYAxisMin(0);
			mRenderer.setYAxisMax(50000);
			series = new XYSeries("光照强度" + " [查询起始时间: " + JackStatisticCustomDatePicker.sYear + "-" + JackStatisticCustomDatePicker.sMonth + 
					"-" + JackStatisticCustomDatePicker.sDay + "  时间跨度: "  + JackStatisticCustomNumberPicker.timeSpan  + "个月]");
			series.add(0,0);
			mRenderer.setChartTitle("[7]光照强度－历史曲线");
		}
		
		if (JackFragmentMaster.listHistory != null) {
			for (int i = 1; i < JackFragmentMaster.listHistory.size(); i++) {
				Integer intValue = Integer.valueOf(JackFragmentMaster.listHistory.get(i));
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
