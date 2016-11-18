package com.greenhouse.ui;

import java.util.ArrayList;
import java.util.List;
import com.greenhouse.R;
import com.greenhouse.database.SourceDataManager;
import com.greenhouse.model.Jack;
import com.greenhouse.mvadpater.JackInfoAdapter;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/24 PM 5:11:32 
* @version      1.0  
*/
public class JackFragmentShowinfo extends Fragment {
	
	public static RecyclerView recyclerView;
	public static JackInfoAdapter adapter;
	public static List<Jack> jackInfoList = new ArrayList<Jack>();
	private Context context;
	
	public JackFragmentShowinfo(Context context) {
		this.context = context;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View vw = inflater.inflate(R.layout.jack_fragment_recyclerview, container, false); //获取fragment布局
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);        
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);                //设置布局方位
		recyclerView = (RecyclerView)vw.findViewById(R.id.id_recyclerview_horizontal);     //获取RecyclerView布局
		recyclerView.setLayoutManager(linearLayoutManager);	
		//绑定view到布局
		jackInfoList = SourceDataManager.initJackInfoList();
		adapter = new JackInfoAdapter(context);							   //绑定数据到view
		recyclerView.setAdapter(adapter);		
		return vw;
	}
	
	public void onDestroy() {
		super.onDestroy();
	}
	
	
	





	
	
}
