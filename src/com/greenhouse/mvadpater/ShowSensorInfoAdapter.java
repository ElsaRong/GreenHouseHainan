package com.greenhouse.mvadpater;

import java.util.List;
import com.greenhouse.R;
import com.greenhouse.model.SensorItemInfo;
import com.greenhouse.specialversion.UIDisplay;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			May 21, 20161:14:41 PM 
* @version		1.0  
* @description  联动模式下显示多传感器绑定任务的Adapter of ListView 	 
*/
public class ShowSensorInfoAdapter extends BaseAdapter{
	
	private static final String TAG = "ShowSensorInfoAdapter.java";
	
	private LayoutInflater mInflater;
	private List<SensorItemInfo> sensInfoList;
	
	public ShowSensorInfoAdapter(LayoutInflater inflater, List<SensorItemInfo> sensInfoList) {
		this.mInflater = inflater;
		this.sensInfoList = sensInfoList;
	}
	
	@Override
	public Object getItem(int position) {
		return null != sensInfoList?sensInfoList.get(position):null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "getView() = " + position);
		ViewHolder holder = null;
		SensorItemInfo sensorItemInfo = (SensorItemInfo)getItem(position);
		
		if (convertView == null) {
			holder = new ViewHolder();//holder类做为adapter的静态内部类，保存了convertView中findViewById的Java对象
			convertView = mInflater.inflate(R.layout.sensor_task_info_item, null);
			holder.sensortype = (TextView) convertView.findViewById(R.id.sensortype);
			holder.average_value = (TextView) convertView.findViewById(R.id.average_value);
			holder.day_thre = (TextView) convertView.findViewById(R.id.day_threshold);
			holder.night_thre = (TextView) convertView.findViewById(R.id.night_threshold);
			convertView.setTag(holder);//通过setTag()关联convertView和holder
		} else {
			holder = (ViewHolder) convertView.getTag();//convertView不为空，可以复用滑出屏幕的convertView
		}
		
		int sensorType = sensorItemInfo.getSensorType();
		String unit = UIDisplay.showSensInforListUnit(sensorType); //根据类型获取单位
		holder.sensortype.setText("类型：" + UIDisplay.showSensInfoListSensType(sensorItemInfo.getSensorType())); //int1-7转换传感器类型
		holder.average_value.setText("平均值：" + UIDisplay.showSensInfoListValue(sensorType, sensorItemInfo.getAverageValue()) + unit);
		holder.day_thre.setText("白天门限：" +  UIDisplay.showSensInfoListValue(sensorType, sensorItemInfo.getAverageValue()) + unit);
		holder.night_thre.setText("夜间门限：" +  UIDisplay.showSensInfoListValue(sensorType, sensorItemInfo.getAverageValue()) + unit);
			
		return convertView;
	}

	public final class ViewHolder {
		public TextView sensortype;
		public TextView average_value;
		public TextView day_thre;
		public TextView night_thre;
	}

	@Override
	public int getCount() {
		return sensInfoList.size();
	}

}
