package com.greenhouse.mvadpater;

import java.util.LinkedList;
import java.util.Queue;
import com.greenhouse.R;
import com.greenhouse.specialversion.UIDisplay;
import com.greenhouse.ui.JackFragmentShowinfo;

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

public class JackRecyclerViewShowinfoAdapter extends RecyclerView.Adapter<JackRecyclerViewShowinfoAdapter.ViewHolder>  {
	
	private LayoutInflater mInflater;
	private Context context;	
	private ViewHolder viewHolder;
	
	//add 8/22
//	private RecyclerView recyclerView;
	
	private static ShowSensorInfoAdapter sensoradapter; //联动模式显示适配器
	private static ShowTimeInfoAdapter timeadapter;     //定时模式显示适配器
	
	public JackRecyclerViewShowinfoAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View v) {
			super(v);
			// TODO Auto-generated constructor stub
		}		
		LinearLayout showinfo;
		ImageView jack;
		TextView jackname;
		TextView jackstate;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position ) {
		// TODO Auto-generated method stub					
		View vw = mInflater.inflate(R.layout.jack_fragment_item, viewGroup, false);	
		
		//add 8/22
//		if (recyclerView == null) {
//			recyclerView = (RecyclerView) viewGroup;
//		}
		
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
		timeadapter = new ShowTimeInfoAdapter(inflater);
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
		String sensorsid = UIDisplay.ShowBundSensor(JackFragmentShowinfo.jacks.get(position).getSensors()); //显示绑定的传感器id
		taskmode.setText("联动模式");        //显示模式
		sensors.setText(sensorsid);         //显示绑定传感器
		
		// add by Elsa, 2016/8/27 初始化数据在这里是否合适
		JackFragmentShowinfo.bundTask = initBundTasksInfo(position);
		JackFragmentShowinfo.liteBundTask = UIDisplay.ShowLiteBundTask(JackFragmentShowinfo.bundTask);//position:当前正在绘制的jack id
		// add end
		
		sensoradapter = new ShowSensorInfoAdapter(inflater);
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
	
	/**
	 * @Title:       initBundTasksInfo
	 * @description: TODO 每次动态加载联动模式时调用，为静态全局变量bundTask赋值
	 * @param        @param position
	 * @param        @return
	 * @return       int[][]
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 23, 2016, 11:21:34 AM
	 */
	private int[][] initBundTasksInfo(int position) {
		int[][] bundTasks = new int[7][4];
		if (JackFragmentShowinfo.jacks.get(position).getbundtype1() == 1) {
			bundTasks[0][0] = 1;                                                              //绑定的传感器类型－1:土壤温度
			bundTasks[0][1] = JackFragmentShowinfo.jacks.get(position).getCurrentValue1();    //当前插座上绑定的传感器的平均实时值
			bundTasks[0][2] = JackFragmentShowinfo.jacks.get(position).getDay_threshold1();   
			bundTasks[0][3] = JackFragmentShowinfo.jacks.get(position).getNight_threshold1(); 
		}
		if (JackFragmentShowinfo.jacks.get(position).getbundtype2() == 1) {
			bundTasks[1][0] = 2;
			bundTasks[1][1] = JackFragmentShowinfo.jacks.get(position).getCurrentValue2();
			bundTasks[1][2] = JackFragmentShowinfo.jacks.get(position).getDay_threshold2();
			bundTasks[1][3] = JackFragmentShowinfo.jacks.get(position).getNight_threshold2();
		}
		if (JackFragmentShowinfo.jacks.get(position).getbundtype3() == 1) {
			bundTasks[2][0] = 3;
			bundTasks[2][1] = JackFragmentShowinfo.jacks.get(position).getCurrentValue3();
			bundTasks[2][2] = JackFragmentShowinfo.jacks.get(position).getDay_threshold3();
			bundTasks[2][3] = JackFragmentShowinfo.jacks.get(position).getNight_threshold3();
		}
		if (JackFragmentShowinfo.jacks.get(position).getbundtype4() == 1) {
			bundTasks[3][0] = 4;
			bundTasks[3][1] = JackFragmentShowinfo.jacks.get(position).getCurrentValue4();
			bundTasks[3][2] = JackFragmentShowinfo.jacks.get(position).getDay_threshold4();
			bundTasks[3][3] = JackFragmentShowinfo.jacks.get(position).getNight_threshold4();
		}
		if (JackFragmentShowinfo.jacks.get(position).getbundtype5() == 1) {
			bundTasks[4][0] = 5;
			bundTasks[4][1] = JackFragmentShowinfo.jacks.get(position).getCurrentValue5();
			bundTasks[4][2] = JackFragmentShowinfo.jacks.get(position).getDay_threshold5();
			bundTasks[4][3] = JackFragmentShowinfo.jacks.get(position).getNight_threshold5();
		}
		if (JackFragmentShowinfo.jacks.get(position).getbundtype6() == 1) {
			bundTasks[5][0] = 6;
			bundTasks[5][1] = JackFragmentShowinfo.jacks.get(position).getCurrentValue6();
			bundTasks[5][2] = JackFragmentShowinfo.jacks.get(position).getDay_threshold6();
			bundTasks[5][3] = JackFragmentShowinfo.jacks.get(position).getNight_threshold6();
		}
		if (JackFragmentShowinfo.jacks.get(position).getbundtype7() == 1) {
			bundTasks[6][0] = 7;
			bundTasks[6][1] = JackFragmentShowinfo.jacks.get(position).getCurrentValue7();
			bundTasks[6][2] = JackFragmentShowinfo.jacks.get(position).getDay_threshold7();
			bundTasks[6][3] = JackFragmentShowinfo.jacks.get(position).getNight_threshold7();
		}
		return bundTasks;
	}
	

	/**
	 * 每次滑动引发界面重绘时调用，传入触发的item id，重绘
	 */
	public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
		// TODO Auto-generated method stub	
		
		//add 8/22
//		long url = getItemId(i);
//		viewHolder.showinfo.setTag(url);
		
//		viewHolder.showinfo.setVisibility(View.VISIBLE);
		
		//add 8/23 强制关闭复用
		viewHolder.setIsRecyclable(false);
		
		/**
		 * case 0: 无任务；case 1: 传感器任务；case 2:定时任务
		 */
		switch (JackFragmentShowinfo.jacks.get(i).getBund()) {
		
		case 0:	//被HAVE刷成0
			viewHolder.showinfo.removeAllViews();
			viewHolder.showinfo.addView(DynamicAddNoMode());
			break;			
		case 1:	//被BUND，BUDD，BUUU刷成1	
			viewHolder.showinfo.removeAllViews();
			viewHolder.showinfo.addView(DynamicAddSensorMode(i));
			
			break;			
		case 2:	//被TAKS刷成2	
			viewHolder.showinfo.removeAllViews();
			viewHolder.showinfo.addView(DynamicAddTimeMode());
			break;
		}
		
		viewHolder.jack.setImageResource(R.drawable.default_jack);
		viewHolder.jackname.setText(JackFragmentShowinfo.jacks.get(i).getName());				
		
		if (JackFragmentShowinfo.jacks.get(i).getSwitchstate() == 1) {
			viewHolder.jackstate.setText("运行");
			viewHolder.jackstate.setBackgroundColor(Color.rgb(255, 153, 18));
		} else {			
			viewHolder.jackstate.setText("停止");
			viewHolder.jackstate.setBackgroundColor(Color.DKGRAY);
		}
	}
	
	
	public String SetBinearySensorsToShow(String binarySensors) {
		String sensors = "";
		for (int i = 0; i < 10; i++) {
			if (binarySensors.substring(i, i+1).equals("1")) {
				sensors = sensors + (i+1) + ",";
			}
		}
		if (sensors.length() > 1) {
			sensors = sensors.substring(0, sensors.length()-1);
		} else {
			sensors = "无传感器";
		}
		return sensors;
	}
	
	@Override
	public int getItemCount() {
		return JackFragmentShowinfo.jacks.size();		
	}

	
}

