package com.greenhouse.ui;

import java.util.List;

import com.greenhouse.R;
import com.greenhouse.model.Jack;
import com.greenhouse.mvadpater.GridSwitchModeSetAdapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/24 PM 5:11:32 
* @version      1.0  
*/
public class JackFragmentModeSet extends Fragment {
	
	public static GridSwitchModeSetAdapter adapter;

	public static List<Jack> jacks; //模式设置界面的插座基本信息
	public static int sJackId;       //模式设置界面用户点击设置的插座编号

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Context context = getActivity();
		View vw = inflater.inflate(R.layout.jack_fragment_modeset, container, false);	
		
		GridView gridView = (GridView)vw.findViewById(R.id.greenhouse_ter);
			
	    adapter = new GridSwitchModeSetAdapter(context, jacks);
	    gridView.setAdapter(adapter);
			
		return vw;
	}
	
	
	
	





	
	
}
