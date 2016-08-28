package com.greenhouse.mvadpater;

import java.util.ArrayList;
import java.util.List;
import com.greenhouse.R;
import com.greenhouse.database.SourceDataManager;
import com.greenhouse.model.Jack;
import com.greenhouse.model.SensorItemInfo;
import com.greenhouse.specialversion.UIDisplay;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/** 
* class <code>NetBroadcastReceiver</code> 将在Application中需要调用应用
* 和系统资源的部分分离出来，写成接口
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/26 PM9:56:23 
* @version      1.0  
*/

public class JackInfoAdapter extends RecyclerView.Adapter<JackInfoAdapter.ViewHolder>  {
	
	private LayoutInflater mInflater;
	private Context context;	
	private ViewHolder viewHolder;
	
	private static JackSensModeAdapter sensoradapter; //联动模式显示适配器
	private static JackTimeModeAdapter timeadapter;     //定时模式显示适配器
	
	private List<Jack> jackInfoList = new ArrayList<Jack>();
	
	public JackInfoAdapter(Context context, List<Jack> jackInfoList) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.jackInfoList = jackInfoList;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View v) {
			super(v);
		}		
		LinearLayout showinfo;
		ImageView jack;
		TextView jackname;
		TextView jackstate;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position ) {
		View vw = mInflater.inflate(R.layout.jack_fragment_item, viewGroup, false);	
		
		viewHolder = new ViewHolder(vw);		
		viewHolder.showinfo = (LinearLayout)vw.findViewById(R.id.jack_fragment_item_showinfo);		
		viewHolder.jack = (ImageView)vw.findViewById(R.id.jack_fragment_item_jack);
		viewHolder.jackname = (TextView)vw.findViewById(R.id.jack_fragment_item_jackname);	
		viewHolder.jackstate = (TextView)vw.findViewById(R.id.jack_fragment_item_jackstate);	
		return viewHolder;		
	}
	
	/**
	 * @Title:       DynamicAddTimeMode
	 * @description: TODO 动态加载定时模式显示
	 * @param        @return
	 * @return       View
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 23, 2016, 11:18:40 AM
	 */
	private View DynamicAddTimeMode() {		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LayoutInflater inflater = LayoutInflater.from(context);
		View vw2 = inflater.inflate(R.layout.jack_fragment_showinfo, null);	
		vw2.setLayoutParams(lp);	
		TextView taskmode = (TextView)vw2.findViewById(R.id.task_mode);	
		TextView sensors = (TextView)vw2.findViewById(R.id.sensors);	
		taskmode.setText("定时模式");      //显示模式
		sensors.setText("");              //显示绑定传感器[null]
		
		
		List<Jack> jackTimeInfoList = new ArrayList<Jack>();
		jackTimeInfoList = SourceDataManager.initJackInfoList(context);
		timeadapter = new JackTimeModeAdapter(inflater, jackTimeInfoList);
		
		
		ListView listview = (ListView)vw2.findViewById(R.id.showinfo);
		listview.setAdapter(timeadapter); //绑定适配器
		return vw2;
	}
	
	/**
	 * @Title:       DynamicAddSensorMode
	 * @description: TODO 动态加载联动模式显示,初始化用于<传感器任务Adapter>的数据
	 * @param        @param position
	 * @param        @return
	 * @return       View
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 23, 2016, 11:18:59 AM
	 */
	private View DynamicAddSensorMode(int position) {		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LayoutInflater inflater = LayoutInflater.from(context);
		View vw2 = inflater.inflate(R.layout.jack_fragment_showinfo, null);	
		vw2.setLayoutParams(lp);	
		TextView taskmode = (TextView)vw2.findViewById(R.id.task_mode);	
		TextView sensors = (TextView)vw2.findViewById(R.id.sensors);
		String sensorsid = UIDisplay.ShowBundSensor(jackInfoList.get(position).getSensors()); //显示绑定的传感器id
		taskmode.setText("联动模式");        //显示模式
		sensors.setText(sensorsid);         //显示绑定传感器
		
		//每次动态加载一个插座的ShowInfo时，new一个新的适配器对象
		List<SensorItemInfo> sensorInfoList = initSensorInfoList(position);
		sensoradapter = new JackSensModeAdapter(inflater, sensorInfoList);
		
		ListView listview = (ListView)vw2.findViewById(R.id.showinfo);
		listview.setAdapter(sensoradapter); //绑定适配器
		return vw2;
	}
	
	/**
	 * @Title:       DynamicAddNoMode
	 * @description: TODO 动态加载无模式
	 * @param        @return
	 * @return       View
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 23, 2016, 11:21:16 AM
	 */
	private View DynamicAddNoMode() {		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LayoutInflater inflater = LayoutInflater.from(context);
		View vw2 = inflater.inflate(R.layout.jack_fragment_showinfo, null);	
		vw2.setLayoutParams(lp);	
		TextView taskmode = (TextView)vw2.findViewById(R.id.task_mode);	
		TextView sensors = (TextView)vw2.findViewById(R.id.sensors);	
		taskmode.setText("无任务");
		sensors.setText("");
		return vw2;
	}
	
	private List<SensorItemInfo> initSensorInfoList(final int position) {
		List<SensorItemInfo> list = new ArrayList<SensorItemInfo>();
		if (jackInfoList.get(position).getbundtype1() == 1) {
			SensorItemInfo sensInfo = new SensorItemInfo();
			sensInfo.setSensorType(1);
			sensInfo.setAverageValue(jackInfoList.get(position).getCurrentValue1());
			sensInfo.setDayThre(jackInfoList.get(position).getDay_threshold1());
			sensInfo.setNightthre(jackInfoList.get(position).getNight_threshold1());
			list.add(sensInfo);
		}
		if (jackInfoList.get(position).getbundtype2() == 1) {
			SensorItemInfo sensInfo = new SensorItemInfo();
			sensInfo.setSensorType(2);
			sensInfo.setAverageValue(jackInfoList.get(position).getCurrentValue2());
			sensInfo.setDayThre(jackInfoList.get(position).getDay_threshold2());
			sensInfo.setNightthre(jackInfoList.get(position).getNight_threshold2());
			list.add(sensInfo);
		}
		if (jackInfoList.get(position).getbundtype3() == 1) {
			SensorItemInfo sensInfo = new SensorItemInfo();
			sensInfo.setSensorType(3);
			sensInfo.setAverageValue(jackInfoList.get(position).getCurrentValue3());
			sensInfo.setDayThre(jackInfoList.get(position).getDay_threshold3());
			sensInfo.setNightthre(jackInfoList.get(position).getNight_threshold3());
			list.add(sensInfo);
		}
		if (jackInfoList.get(position).getbundtype4() == 1) {
			SensorItemInfo sensInfo = new SensorItemInfo();
			sensInfo.setSensorType(4);
			sensInfo.setAverageValue(jackInfoList.get(position).getCurrentValue4());
			sensInfo.setDayThre(jackInfoList.get(position).getDay_threshold4());
			sensInfo.setNightthre(jackInfoList.get(position).getNight_threshold4());
			list.add(sensInfo);
		}
		if (jackInfoList.get(position).getbundtype5() == 1) {
			SensorItemInfo sensInfo = new SensorItemInfo();
			sensInfo.setSensorType(5);
			sensInfo.setAverageValue(jackInfoList.get(position).getCurrentValue5());
			sensInfo.setDayThre(jackInfoList.get(position).getDay_threshold5());
			sensInfo.setNightthre(jackInfoList.get(position).getNight_threshold5());
			list.add(sensInfo);
		}
		if (jackInfoList.get(position).getbundtype6() == 1) {
			SensorItemInfo sensInfo = new SensorItemInfo();
			sensInfo.setSensorType(6);
			sensInfo.setAverageValue(jackInfoList.get(position).getCurrentValue6());
			sensInfo.setDayThre(jackInfoList.get(position).getDay_threshold6());
			sensInfo.setNightthre(jackInfoList.get(position).getNight_threshold6());
			list.add(sensInfo);
		}
		if (jackInfoList.get(position).getbundtype7() == 1) {
			SensorItemInfo sensInfo = new SensorItemInfo();
			sensInfo.setSensorType(7);
			sensInfo.setAverageValue(jackInfoList.get(position).getCurrentValue7());
			sensInfo.setDayThre(jackInfoList.get(position).getDay_threshold7());
			sensInfo.setNightthre(jackInfoList.get(position).getNight_threshold7());
			list.add(sensInfo);
		}
		return list;
	}

	
	public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
		//add 8/23 强制关闭复用
		viewHolder.setIsRecyclable(false);

		//case 0: 无任务；case 1: 传感器任务；case 2:定时任务
		switch (jackInfoList.get(i).getBund()) {
		case 0:	
			viewHolder.showinfo.removeAllViews();
			viewHolder.showinfo.addView(DynamicAddNoMode());
			break;			
		case 1:	
			viewHolder.showinfo.removeAllViews();
			viewHolder.showinfo.addView(DynamicAddSensorMode(i));
			
			break;			
		case 2:
			viewHolder.showinfo.removeAllViews();
			viewHolder.showinfo.addView(DynamicAddTimeMode());
			break;
		}
		
		viewHolder.jack.setImageResource(R.drawable.default_jack);
		viewHolder.jackname.setText(jackInfoList.get(i).getName());				
		
		if (jackInfoList.get(i).getSwitchstate() == 1) {
			viewHolder.jackstate.setText("运行");
			viewHolder.jackstate.setBackgroundColor(Color.rgb(255, 153, 18));
		} else {			
			viewHolder.jackstate.setText("停止");
			viewHolder.jackstate.setBackgroundColor(Color.DKGRAY);
		}
	}

	
	@Override
	public int getItemCount() {
		return jackInfoList.size();		
	}

	
}

