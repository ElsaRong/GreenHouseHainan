package com.greenhouse.mvadpater;

import java.util.List;
import com.greenhouse.R;
import com.greenhouse.model.SensorItemInfo;
import com.greenhouse.specialversion.UIDisplay;
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
public class JackSensModeAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;          //界面
	private List<SensorItemInfo> sensInfoList; //数据源
	
	public TextView sensortype;
	public TextView average_value;
	public TextView day_thre;
	public TextView night_thre;
	
	public JackSensModeAdapter(LayoutInflater inflater, List<SensorItemInfo> sensInfoList) {
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
//		ViewHolder holder = null;
		SensorItemInfo sensorItemInfo = (SensorItemInfo)getItem(position);
//		if (convertView == null) {
//			holder = new ViewHolder();
//			convertView = mInflater.inflate(R.layout.sensor_task_info_item, null);
//			holder.sensortype = (TextView) convertView.findViewById(R.id.sensortype);
//			holder.average_value = (TextView) convertView.findViewById(R.id.average_value);
//			holder.day_thre = (TextView) convertView.findViewById(R.id.day_threshold);
//			holder.night_thre = (TextView) convertView.findViewById(R.id.night_threshold);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag(); //复用convertView，关联了和该convertView相关的holder
//		}
		convertView = mInflater.inflate(R.layout.sensor_task_info_item, null);
		sensortype = (TextView) convertView.findViewById(R.id.sensortype);
		average_value = (TextView) convertView.findViewById(R.id.average_value);
		day_thre = (TextView) convertView.findViewById(R.id.day_threshold);
		night_thre = (TextView) convertView.findViewById(R.id.night_threshold);
		
		int sensorType = sensorItemInfo.getSensorType();
		String unit = UIDisplay.showSensInforListUnit(sensorType); //根据类型获取单位
		sensortype.setText("类型：" + UIDisplay.showSensInfoListSensType(sensorItemInfo.getSensorType())); //int1-7转换传感器类型
		average_value.setText("平均值：" + UIDisplay.showSensInfoListValue(sensorType, sensorItemInfo.getAverageValue()) + unit);
		day_thre.setText("白天门限：" +  UIDisplay.showSensInfoListValue(sensorType, sensorItemInfo.getDayThre()) + unit);
		night_thre.setText("夜间门限：" +  UIDisplay.showSensInfoListValue(sensorType, sensorItemInfo.getNightThe()) + unit);
			
		return convertView;
	}

//	public final class ViewHolder {
//		public TextView sensortype;
//		public TextView average_value;
//		public TextView day_thre;
//		public TextView night_thre;
//	}

	@Override
	public int getCount() {
		return sensInfoList.size();
	}

}
