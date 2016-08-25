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
public class ShowTimeInfoAdapter extends BaseAdapter{
	
	
	private LayoutInflater mInflater;
	
	public ShowTimeInfoAdapter(LayoutInflater inflater) {
		// TODO Auto-generated constructor stub
		mInflater = inflater;
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.time_task_info_item, null);
			holder.starttime = (TextView) convertView.findViewById(R.id.starttime);
			holder.ontime = (TextView) convertView.findViewById(R.id.ontime);
			holder.offtime = (TextView) convertView.findViewById(R.id.offtime);
			holder.cycleindex = (TextView) convertView.findViewById(R.id.cycleindex);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.starttime.setText("开始时间：" + JackFragmentShowinfo.jacks.get(position).getStart());
		holder.ontime.setText("打开时间：" + JackFragmentShowinfo.jacks.get(position).getPoweron());
		holder.offtime.setText("关闭时间：" + JackFragmentShowinfo.jacks.get(position).getPoweroff());
		holder.cycleindex.setText("循环次数：" + JackFragmentShowinfo.jacks.get(position).getCycle() + "次");
		return convertView;
	}

	public final class ViewHolder {
		public TextView starttime;
		public TextView ontime;
		public TextView offtime;
		public TextView cycleindex;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
