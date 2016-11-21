package com.greenhouse.mvadpater;

import com.greenhouse.R;
import com.greenhouse.ui.JackFragmentShowinfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			May 21, 20161:13:03 PM 
* @version		1.0  
* @description			 
*/
public class JackTimeModeAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private TextView starttime;
	private TextView ontime;
	private TextView offtime;
	private TextView cycleindex;
	private int jackId = 0;
	
	public JackTimeModeAdapter(LayoutInflater inflater, int jackId) {
		// TODO Auto-generated constructor stub
		this.mInflater = inflater;
		this.jackId = jackId;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = mInflater.inflate(R.layout.time_task_info_item, null);
		starttime = (TextView) convertView.findViewById(R.id.starttime);
		ontime = (TextView) convertView.findViewById(R.id.ontime);
		offtime = (TextView) convertView.findViewById(R.id.offtime);
		cycleindex = (TextView) convertView.findViewById(R.id.cycleindex);
		
		starttime.setText("开始时间：" + JackFragmentShowinfo.jackInfoList.get(jackId).getStart());
		ontime.setText("打开时间：" + JackFragmentShowinfo.jackInfoList.get(jackId).getPoweron());
		offtime.setText("关闭时间：" + JackFragmentShowinfo.jackInfoList.get(jackId).getPoweroff());
		cycleindex.setText("循环次数：" + JackFragmentShowinfo.jackInfoList.get(jackId).getCycle() + "次");
		return convertView;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
