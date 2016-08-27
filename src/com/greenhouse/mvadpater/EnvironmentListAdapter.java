package com.greenhouse.mvadpater;

import java.util.ArrayList;
import java.util.List;

import com.greenhouse.R;
import com.greenhouse.model.Sensor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			May 22, 201612:39:20 PM 
* @version		1.0  
* @description			 
*/
public class EnvironmentListAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	
	private List<Sensor> sensors = new ArrayList<Sensor>();
	
	public EnvironmentListAdapter(Context context, List<Sensor> sensors) {
		mInflater = LayoutInflater.from(context);
		this.sensors = sensors;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sensors.size();
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
			convertView = mInflater.inflate(R.layout.environment_item, null);
			holder.sensorid = (TextView) convertView.findViewById(R.id.sensorid);
			holder.online = (TextView) convertView.findViewById(R.id.online);
			holder.airtemp = (TextView) convertView.findViewById(R.id.airtemp);
			holder.airhum = (TextView) convertView.findViewById(R.id.airhum);
			holder.co2 = (TextView) convertView.findViewById(R.id.co2);
			holder.soiltemp = (TextView) convertView.findViewById(R.id.soiltemp);
			holder.soilhum = (TextView) convertView.findViewById(R.id.soilhum);
			holder.ph = (TextView) convertView.findViewById(R.id.ph);
			holder.illum = (TextView) convertView.findViewById(R.id.illum);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.sensorid.setText("传感器" + sensors.get(position).getId() + "号");
		if (sensors.get(position).getOnline() == 1) {
			holder.online.setText("是");
		} else {
			holder.online.setText("否");
		}
		holder.airtemp.setText(sensors.get(position).getAirtemp() + " ℃");
		holder.airhum.setText(sensors.get(position).getAirhum() + "%");
		holder.co2.setText(sensors.get(position).getCo2() + " ppm");
		holder.soiltemp.setText(sensors.get(position).getSoiltemp() + " ℃");
		holder.soilhum.setText(sensors.get(position).getSoilhum() + "%");
		holder.ph.setText(sensors.get(position).getSoilph()/10 + "." + sensors.get(position).getSoilph()%10  + "");
		holder.illum.setText(10*sensors.get(position).getIllumination() + "lux");
		return convertView;
	}

	public final class ViewHolder {
		public TextView sensorid;
		public TextView online;
		public TextView airtemp;
		public TextView airhum;
		public TextView co2;
		public TextView soiltemp;
		public TextView soilhum;
		public TextView ph;
		public TextView illum;
	}
	
	
}


