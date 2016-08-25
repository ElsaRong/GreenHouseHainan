package com.greenhouse.ui;

import java.util.ArrayList;
import java.util.List;

import com.greenhouse.R;
import com.greenhouse.database.SensorService;
import com.greenhouse.model.Sensor;
import com.greenhouse.mvadpater.EnvironmentListAdapter;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015��11��24�� ����5:11:32 
* @version      1.0  
*/
public class JackFragmentEnvironment extends Fragment {
	
	private ListView listview;
	
	private static EnvironmentListAdapter adapter;
	
	public static List<Sensor> sensors = new ArrayList<Sensor>();
	public static Handler handler;
	
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		final Context context = getActivity();
		View vw = inflater.inflate(R.layout.jack_fragment_environment, container, false);	
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				switch (msg.what) {
				case Const.UI_REFRESH:
					adapter.notifyDataSetChanged();
					break;
				}
			}
		};
		
		adapter = new EnvironmentListAdapter(context, sensors);
		listview = (ListView)vw.findViewById(R.id.list);
		listview.setAdapter(adapter);
		
		return vw;
	}
	
	
	





	
	
}
