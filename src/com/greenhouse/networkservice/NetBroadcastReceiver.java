package com.greenhouse.networkservice;

import com.greenhouse.util.Const;
import com.greenhouse.util.GreenHouseApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/** 
* class <code>NetBroadcastReceiver</code> 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015年11月9日 下午9:44:05 
* @version      1.0  
*/
public class NetBroadcastReceiver extends BroadcastReceiver{
	
	private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	
	private static String BACK_TO_LAUNCHER_ACTION = "com.greenhosue.backtolauncheraction";
	
	private static final String TAG = "NetBroadcastReceiver.java";
	
	private Handler handler;
	
	public NetBroadcastReceiver(Handler handler) {//所有被绑定广播的Activity的Handler，用于向但前aciivty发msg跳转
		this.handler = handler;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(NET_CHANGE_ACTION)) {	
			if (NetworkManager.getNetworkState(GreenHouseApplication.getContext()) == NetworkManager.NETWORK_NONE) {
				Log.d(TAG, "无网络");
				handler.sendEmptyMessage(Const.BACK_TO_LAUNCHER);
			} 
		} else if (intent.getAction().equals(BACK_TO_LAUNCHER_ACTION)) {	
			handler.sendEmptyMessage(Const.BACK_TO_LAUNCHER);
		}
			
	}
	
	

}
