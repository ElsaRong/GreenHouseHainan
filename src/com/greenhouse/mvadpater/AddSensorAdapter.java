package com.greenhouse.mvadpater;

import java.util.List;
import java.util.Map;
import com.greenhouse.R;
import com.greenhouse.model.Sensor;
import com.greenhouse.networkservice.SocketOutputTask;
import com.greenhouse.ui.Launcher;
import com.greenhouse.ui.SensorRecyclerView;
import com.greenhouse.util.Const;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/** 
* @author Elsa 
* @Email elsarong715@gmail.com
* @date 2015/12/11 PM 2:11:02 
* @version 1.0  
* @description 
*/
public class AddSensorAdapter extends RecyclerView.Adapter<AddSensorAdapter.ViewHolder> {

	private long sensorNum;
	
	private List<Sensor> onlineSensors;
	
	private static final String TAG = "SensorRecyclerViewAdapter";
	
	public AddSensorAdapter(Context context, List<Sensor> onlineSensors, Map<String, String> map) {
		this.onlineSensors = onlineSensors;
		this.sensorNum = onlineSensors.size();
		for (int i = 0; i < sensorNum; i++) {
			SensorRecyclerView.selectSensorsMap.put(i+"",0+"");
		}
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
		}
		ImageView sensor;
		TextView sensorid;
		ImageView probe;
	}

	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
		// TODO Auto-generated method stub
		View view = View.inflate(viewGroup.getContext(), R.layout.sensor_item, null);
		ViewHolder viewHolder = new ViewHolder(view);
		viewHolder.sensor = (ImageView)view.findViewById(R.id.sensor);
		viewHolder.sensorid = (TextView)view.findViewById(R.id.sensorid);
		viewHolder.probe = (ImageView)view.findViewById(R.id.probe);
		return viewHolder;		
	}

	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		// TODO Auto-generated method stub
		
		// ���online��true������ѡ�񣬷��򲻵�����ѡ�񣬶���ͼ��Ҳ�ǻ�ɫ�����
		// ������ߣ����Ա�����ƽ�����飻��������ߣ����ܼ���ƽ������	
		if (onlineSensors.get(position).getOnline() == 1) {
			
			viewHolder.sensor.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String IsChecked = SensorRecyclerView.selectSensorsMap.get(position+"");
					if (IsChecked.equals("1")) {
						SensorRecyclerView.selectSensorsMap.put(position+"", 0+"");
						viewHolder.sensor.setImageResource(R.drawable.sensor_unselected);
					} else if (IsChecked.equals("0")){
						SensorRecyclerView.selectSensorsMap.put(position+"", 1+"");		
						viewHolder.sensor.setImageResource(R.drawable.sensor);
					}
				}
			});
			
		} else {
			viewHolder.sensor.setImageResource(R.drawable.sensormcu_offline);
		}
		
		if (SensorRecyclerView.selectSensorsMap.get(position+"").equals("1")) {
			viewHolder.sensor.setImageResource(R.drawable.sensor);
		} else {
			viewHolder.sensor.setImageResource(R.drawable.sensor_unselected);
		}
		
		viewHolder.sensorid.setText("传感器－" + onlineSensors.get(position).getId());
		
		viewHolder.probe.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(position <= 8) {
					SensorRecyclerView.sProbeSensor = "0" + (position + 1); 
				} else {
					SensorRecyclerView.sProbeSensor = (position + 1) + "";
				}
				SocketOutputTask.sendMsgQueue.addLast(createPROBmsg());
			}
		});		
	}
	
	private String createPROBmsg() {
		final String msg = "HFUT" + Launcher.selectMac + "PROB00" + SensorRecyclerView.sProbeSensor + "0000000000000000WANG";
		return msg;
	}
	
	

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return (int)sensorNum;
		
	}

	
	
}
