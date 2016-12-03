package com.greenhouse.ui;

import java.util.ArrayList;
import java.util.List;
import com.greenhouse.R;
import com.greenhouse.database.SourceDataManager;
import com.greenhouse.model.Jack;
import com.greenhouse.mvadpater.JackSwitchItemAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			May 21, 20162:00:36 PM 
* @version		1.0  
* @description			 
*/
public class JackFragmentSwitchTest extends Fragment{
	
	public static String selectSwitch; //用户点击的插座编号
	
	public static List<Jack> jackSwitchInfoList = new ArrayList<Jack>(); //数据源
	public static JackSwitchItemAdapter adapter;                         //adapter
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		jackSwitchInfoList = SourceDataManager.initJackSwitchInfoList(); //初始化源数据
		adapter = new JackSwitchItemAdapter(activity, jackSwitchInfoList);       //初始化adapter
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View vw = inflater.inflate(R.layout.jack_fragment_switch_test, container, false);
		GridView gridView = (GridView)vw.findViewById(R.id.gridView);
		gridView.setAdapter(adapter);
		return vw;
	}


}
