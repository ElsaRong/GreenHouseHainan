package com.greenhouse.ui;

import java.util.ArrayList;
import java.util.List;
import com.greenhouse.R;
import com.greenhouse.database.JackService;
import com.greenhouse.database.SensorService;
import com.greenhouse.model.Jack;
import com.greenhouse.mvadpater.JackInfoAdapter;
import com.greenhouse.util.Const;
import com.greenhouse.util.ToastUtil;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/24 PM 5:11:32 
* @version      1.0  
*/
public class JackFragmentShowinfo extends Fragment {
	
	public static RecyclerView recyclerView;
//	private static Context context;

	public static JackInfoAdapter adapter;
//	private JackService jackService; 
	
	private List<Jack> jackInfoList = new ArrayList<Jack>();
	private Context context;
	
//	public static List<Jack> jacks;
	
	public JackFragmentShowinfo(Context context, List<Jack> jackInfoList) {
		this.context = context;
		this.jackInfoList = jackInfoList;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View vw = inflater.inflate(R.layout.jack_fragment_recyclerview, container, false); //获取fragment布局
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);        
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);                //设置布局方位
		recyclerView = (RecyclerView)vw.findViewById(R.id.id_recyclerview_horizontal);     //获取RecyclerView布局
		recyclerView.setLayoutManager(linearLayoutManager);								   //绑定view到布局
		
		
		adapter = new JackInfoAdapter(context, jackInfoList);							   //绑定数据到view
		recyclerView.setAdapter(adapter);		
		
		return vw;
	}
	
	public static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);  
			switch (msg.what) {
			case Const.UI_REFRESH:
				adapter.notifyDataSetChanged();
			}
		}
	};
	
	public void onDestroy() {
		super.onDestroy();
	}
	
	
	





	
	
}
