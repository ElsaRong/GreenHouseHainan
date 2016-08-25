package com.greenhouse.ui;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.greenhouse.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SimpleExpandableListAdapter;

/** 
* 进入控制器后的菜单项。菜单的视图和点击事件都放在了Fragment的Java类中
* 
* 视图将<JackListFragment>的XML文件加载用户界面，点击事件将通过<JackListFragment>提供的接口继承实现
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/24 下午7:55:41 
* @version      1.0  
*/
public class JackFragmentList extends Fragment{
	
	OnJackFragmentListItemClickListener jackFragmentListItemClickListener;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Context c = getActivity().getApplicationContext();
		View vw = inflater.inflate(R.layout.jack_listfragment, container, false);		
		ExpandableListView jackFragmentListView = (ExpandableListView)vw.findViewById(R.id.jack_fragment_listview);
		
		// ExpandableListView数据结构
		List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
		Map<String, String> group1 = new HashMap<String, String>();
	    group1.put("group", "运行显示");
	    Map<String, String> group2 = new HashMap<String, String>();
	    group2.put("group", "运行测试");
	    Map<String, String> group3 = new HashMap<String, String>();
	    group3.put("group", "运行设置");
	    Map<String, String> group4 = new HashMap<String, String>();
	    group4.put("group", "统计查询");
	    groups.add(group1);
	    groups.add(group2);
	    groups.add(group3);
	    groups.add(group4);
	    
	    List<Map<String, String>> child1 = new ArrayList<Map<String, String>>();
	    Map<String, String> childdata1_1 = new HashMap<String, String>();
	    childdata1_1.put("child", "全部设备");
	    Map<String, String> childdata1_2 = new HashMap<String, String>();
	    childdata1_2.put("child", "环境参数");
	    child1.add(childdata1_1);
	    child1.add(childdata1_2);
	    
	    List<Map<String, String>> child2 = new ArrayList<Map<String, String>>();
	    Map<String, String> childdata2_1 = new HashMap<String, String>();
	    childdata2_1.put("child", "开关测试");
	    child2.add(childdata2_1);
	    
	    List<Map<String, String>> child3 = new ArrayList<Map<String, String>>();
	    Map<String, String> childdata3_1 = new HashMap<String, String>();
	    childdata3_1.put("child", "时间设置");
	    Map<String, String> childdata3_2 = new HashMap<String, String>();
	    childdata3_2.put("child", "阈值设置");
	    Map<String, String> childdata3_3 = new HashMap<String, String>();
	    childdata3_3.put("child", "模式设置");
	    child3.add(childdata3_1);
	    child3.add(childdata3_2);
	    child3.add(childdata3_3);
	    
	    List<Map<String, String>> child4 = new ArrayList<Map<String, String>>();
	    Map<String, String> childdata4_1 = new HashMap<String, String>();
	    childdata4_1.put("child", "土壤温度");
	    Map<String, String> childdata4_2 = new HashMap<String, String>();
	    childdata4_2.put("child", "土壤湿度");
	    Map<String, String> childdata4_3 = new HashMap<String, String>();
	    childdata4_3.put("child", "土壤酸碱度");
	    Map<String, String> childdata4_4 = new HashMap<String, String>();
	    childdata4_4.put("child", "空气温度");
	    Map<String, String> childdata4_5 = new HashMap<String, String>();
	    childdata4_5.put("child", "空气湿度");
	    Map<String, String> childdata4_6 = new HashMap<String, String>();
	    childdata4_6.put("child", "CO2浓度");
	    Map<String, String> childdata4_7 = new HashMap<String, String>();
	    childdata4_7.put("child", "光照强度");
	    child4.add(childdata4_1);
	    child4.add(childdata4_2);
	    child4.add(childdata4_3);
	    child4.add(childdata4_4);
	    child4.add(childdata4_5);
	    child4.add(childdata4_6);
	    child4.add(childdata4_7);
		
	    
	    
	    List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
        childs.add(child1);
        childs.add(child2);
        childs.add(child3);
        childs.add(child4);
        
        // 通过适配器，将ExpandableListView的数据结构和视图绑定
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                c, groups, R.layout.jack_listfragment_group, new String[] {"group"}, new int[] { R.id.jack_listfragment_group }, 
                childs, R.layout.jack_listfragment_child, new String[] { "child" }, new int[] { R.id.jack_listfragment_child });
        jackFragmentListView.setAdapter(adapter);
        
        // 默认group都是展开状�??,因为第三个group没有child�?以展�?报错
        int groupNum = jackFragmentListView.getCount();
        for(int i = 0; i < groupNum; i++) {
        	jackFragmentListView.expandGroup(i);
        }
        
        // 为ExpandableListView设置点击监听事件
        jackFragmentListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				jackFragmentListItemClickListener.onJackFragmentListItemClick(groupPosition+""+childPosition);		
				return true;
			}        	
		});
        
        // Group的点击事件应该是展开收缩
        jackFragmentListView.setOnGroupClickListener(new OnGroupClickListener() {			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// TODO Auto-generated method stub				
				return false;				
			}
		});
        
		return vw;
	}
	
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			jackFragmentListItemClickListener = (OnJackFragmentListItemClickListener)activity;
		} catch (ClassCastException cce) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString() + "must implement OnItemClickListener");
		}		
	}
	
	
	public interface OnJackFragmentListItemClickListener {
		public void onJackFragmentListItemClick(String msg);
	}
	
	

}
