package com.greenhouse.mvadpater;

import com.greenhouse.R;
import com.greenhouse.specialversion.UIDisplay;
import com.greenhouse.ui.JackFragmentShowinfo;
import com.greenhouse.util.Const;

import android.R.id;
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
* @description			 
*/
public class ShowSensorInfoAdapter extends BaseAdapter{
	
	
	private LayoutInflater mInflater;
	
	
	public ShowSensorInfoAdapter(LayoutInflater inflater) {
		// TODO Auto-generated constructor stub
		this.mInflater = inflater;
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
		//add 8/23 强制关闭复用
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.sensor_task_info_item, null);
			holder.sensortype = (TextView) convertView.findViewById(R.id.sensortype);
			holder.average_value = (TextView) convertView.findViewById(R.id.average_value);
			holder.day_thre = (TextView) convertView.findViewById(R.id.day_threshold);
			holder.night_thre = (TextView) convertView.findViewById(R.id.night_threshold);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		String sensortype = "";
//		//add by Elsa, 2016/8/27, 校验position和liteBundTask的长度,由于item复用,导致绑定传感器个数>复用的item count
//		if ((position+1) < JackFragmentShowinfo.liteBundTask.length)
			
		if (JackFragmentShowinfo.liteBundTask[position][0] != 0) {
			switch (JackFragmentShowinfo.liteBundTask[position][0]) {
			case 1:
				sensortype = Const.SOIL_TEMP;
				holder.sensortype.setText("类型：" + sensortype);
				holder.average_value.setText("平均值：" + JackFragmentShowinfo.liteBundTask[position][1] + " " +Const.SOIL_TEMP_UNIT);
				holder.day_thre.setText("白天门限：" + JackFragmentShowinfo.liteBundTask[position][2] + " " +Const.SOIL_TEMP_UNIT);
				holder.night_thre.setText("夜间门限：" + JackFragmentShowinfo.liteBundTask[position][3] + " " +Const.SOIL_TEMP_UNIT);
				break;
			case 2:
				sensortype = Const.SOIL_HUM;
				holder.sensortype.setText("类型：" + sensortype);
				holder.average_value.setText("平均值：" + JackFragmentShowinfo.liteBundTask[position][1] + " " +Const.SOIL_HUM_UNIT);
				holder.day_thre.setText("白天门限：" + JackFragmentShowinfo.liteBundTask[position][2]+ " " +Const.SOIL_HUM_UNIT);
				holder.night_thre.setText("夜间门限：" + JackFragmentShowinfo.liteBundTask[position][3]+ " " +Const.SOIL_HUM_UNIT);
				break;
			case 3:
				sensortype = Const.PH;
				holder.sensortype.setText("类型：" + sensortype);
				holder.average_value.setText("平均值：" + JackFragmentShowinfo.liteBundTask[position][1]/10 + "." 
						+ JackFragmentShowinfo.liteBundTask[position][1]%10 + " " + Const.PH_UNIT);
				holder.day_thre.setText("白天门限：" + JackFragmentShowinfo.liteBundTask[position][2]/10 + "."
						+ JackFragmentShowinfo.liteBundTask[position][2]%10 + " " +Const.PH_UNIT);
				holder.night_thre.setText("夜间门限：" + JackFragmentShowinfo.liteBundTask[position][3]/10 + "."
						+ JackFragmentShowinfo.liteBundTask[position][3]%10 + " " +Const.PH_UNIT);
				break;
			case 4:
				sensortype = Const.AIR_TEMP;
				holder.sensortype.setText("类型：" + sensortype);
				holder.average_value.setText("平均值：" + JackFragmentShowinfo.liteBundTask[position][1]+ " " +Const.AIR_TEMP_UNIT);
				holder.day_thre.setText("白天门限：" + JackFragmentShowinfo.liteBundTask[position][2]+ " " +Const.AIR_TEMP_UNIT);
				holder.night_thre.setText("夜间门限：" + JackFragmentShowinfo.liteBundTask[position][3]+ " " +Const.AIR_TEMP_UNIT);
				break;
			case 5:
				sensortype = Const.AIR_HUM;
				holder.sensortype.setText("类型：" + sensortype);
				holder.average_value.setText("平均值：" + JackFragmentShowinfo.liteBundTask[position][1]+ " " +Const.AIR_HUM_UNIT);
				holder.day_thre.setText("白天门限：" + JackFragmentShowinfo.liteBundTask[position][2]+ " " +Const.AIR_HUM_UNIT);
				holder.night_thre.setText("夜间门限：" + JackFragmentShowinfo.liteBundTask[position][3]+ " " +Const.AIR_HUM_UNIT);
				break;
			case 6:
				sensortype = Const.CO2;
				holder.sensortype.setText("类型：" + sensortype);
				holder.average_value.setText("平均值：" + JackFragmentShowinfo.liteBundTask[position][1]+ " " +Const.CO2_UNIT);
				holder.day_thre.setText("白天门限：" + JackFragmentShowinfo.liteBundTask[position][2]+ " " +Const.CO2_UNIT);
				holder.night_thre.setText("夜间门限：" + JackFragmentShowinfo.liteBundTask[position][3]+ " " +Const.CO2_UNIT);
				break;
			case 7:
				sensortype = Const.ILLUMINATION;
				holder.sensortype.setText("类型：" + sensortype);
				holder.average_value.setText("平均值：" + 10*JackFragmentShowinfo.liteBundTask[position][1]+ " " +Const.ILLUMINATION_UNIT);
				holder.day_thre.setText("白天门限：" + JackFragmentShowinfo.liteBundTask[position][2]+ " " +Const.ILLUMINATION_UNIT);
				holder.night_thre.setText("夜间门限：" + JackFragmentShowinfo.liteBundTask[position][3]+ " " +Const.ILLUMINATION_UNIT);
		     	break;
			default:
				break;
			}
		}
		return convertView;
	}

	public final class ViewHolder {
		public TextView sensortype;
		public TextView average_value;
		public TextView day_thre;
		public TextView night_thre;
	}

	/**
	 * bundTask[i][0]的顺序既是七种类型传感器的顺序，如果＝1or2or3，表示绑定了1，2，3类型的传感器，需要3个listview的item
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < JackFragmentShowinfo.liteBundTask.length; i++) {
			if (1 <= JackFragmentShowinfo.liteBundTask[i][0] && JackFragmentShowinfo.liteBundTask[i][0] <= 7 ) {  
				count++;
			}
		}
		return count;
	}

}
