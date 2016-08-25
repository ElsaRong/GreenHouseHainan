package com.greenhouse.ui;

import java.util.List;
import com.greenhouse.R;
import com.greenhouse.database.JackService;
import com.greenhouse.model.Jack;
import com.greenhouse.mvadpater.GridItemSwitchTestAdapter;
import com.greenhouse.util.Const;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	
	private Context context;
	
	public static List<Jack> jacks;    //显示在开关测试界面上的插座基本信息List
	public static String selectSwitch; //用户点击的插座编号
	public static Handler handler;     //用于开关测试界面刷新
	
	public static GridItemSwitchTestAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.arg1 == Const.UI_REFRESH) {
					adapter.notifyDataSetChanged();
				} else {
					adapter.notifyDataSetChanged();
				} 
			}
		 };
		
		context = getActivity();
		View vw = inflater.inflate(R.layout.jack_fragment_switch_test, container, false);
		GridView gridView = (GridView)vw.findViewById(R.id.gridView);
		
        adapter = new GridItemSwitchTestAdapter(context, jacks);
		gridView.setAdapter(adapter);
		
		return vw;
	}
	
	
	
	

	

}
