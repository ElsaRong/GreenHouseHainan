package com.greenhouse.database;

import java.util.ArrayList;
import com.greenhouse.model.Jack;
import android.test.AndroidTestCase;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016年2月22日下午1:59:47 
* @version		1.0  
* @description			 
*/
public class JackServiceTest extends AndroidTestCase{

	private static final String TAG = "JackServiceTest.java";
	
	public void testInit() throws Throwable {
		JackService jackService = new JackService(this.getContext());
		jackService.init("hfut");
	}
	
//	public void testGetAllJack() throws Throwable {
//		JackService jackService = new JackService(this.getContext());
//		ArrayList<Jack> jacks = jackService.getAllJack("hfut");
//		for(int i = 0; i < jacks.size(); i++){
//			Log.d(TAG, jacks.get(i).getMac()+"");
//			Log.d(TAG, jacks.get(i).getJackId()+"");
//			Log.d(TAG, jacks.get(i).getName()+"");
//			Log.d(TAG, jacks.get(i).getDrawable()+"");
//			Log.d(TAG, jacks.get(i).getSwitchstate()+"");
//			Log.d(TAG, jacks.get(i).getBund()+"");
//			Log.d(TAG, jacks.get(i).getSensors()+"");
//			Log.d(TAG, jacks.get(i).getDay_threshold1()+"");
//			Log.d(TAG, jacks.get(i).getNight_threshold1()+"");
//			Log.d(TAG, jacks.get(i).getDay_threshold2()+"");
//			Log.d(TAG, jacks.get(i).getNight_threshold2()+"");
//			Log.d(TAG, jacks.get(i).getDay_threshold3()+"");
//			Log.d(TAG, jacks.get(i).getNight_threshold3()+"");
//			Log.d(TAG, jacks.get(i).getDay_threshold4()+"");
//			Log.d(TAG, jacks.get(i).getNight_threshold4()+"");
//			Log.d(TAG, jacks.get(i).getDay_threshold5()+"");
//			Log.d(TAG, jacks.get(i).getNight_threshold5()+"");
//			Log.d(TAG, jacks.get(i).getDay_threshold6()+"");
//			Log.d(TAG, jacks.get(i).getNight_threshold6()+"");
//			Log.d(TAG, jacks.get(i).getDay_threshold7()+"");
//			Log.d(TAG, jacks.get(i).getNight_threshold7()+"");
//			Log.d(TAG, jacks.get(i).getCurrentvalue()+"");
//			Log.d(TAG, jacks.get(i).getStart()+"");
//			Log.d(TAG, jacks.get(i).getPoweron()+"");
//			Log.d(TAG, jacks.get(i).getPoweroff()+"");
//			Log.d(TAG, jacks.get(i).getCycle()+"");
//			Log.d(TAG, jacks.get(i).getStop()+"");
//		}
//	}

}
